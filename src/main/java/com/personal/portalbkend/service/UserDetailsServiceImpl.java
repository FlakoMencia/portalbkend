package com.personal.portalbkend.service;

import com.personal.portalbkend.common.ApiResponse;
import com.personal.portalbkend.common.Constants;
import com.personal.portalbkend.domain.SecUser;
import com.personal.portalbkend.domain.dto.UserDto;
import com.personal.portalbkend.repository.SecUserRepository;
import com.personal.portalbkend.security.SecurityUserDetails;
import com.personal.portalbkend.security.dto.JwtRequest;
import com.personal.portalbkend.security.enums.Role;
import com.personal.portalbkend.security.jwt.JwtTokenUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final SecUserRepository secUserRepository;

    private final JwtTokenUtil jwtTokenUtil;


    public UserDetailsServiceImpl(SecUserRepository secUserRepository, JwtTokenUtil jwtTokenUtil) {
        this.secUserRepository = secUserRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Util para registrar usuario y generar token
     * para persistirlo en tabla correspondiente
     * @param usermail
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String usermail) throws UsernameNotFoundException {
        Optional<SecUser> userOptional = secUserRepository.findSecUsersByStEmail(usermail);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(usermail + Constants.USER_NOT_FOUND);
        }
        LocalDateTime now = LocalDateTime.now();
        SecUser user = userOptional.orElse(new SecUser());
        user.setDtLastConnection(now);
        SecurityUserDetails securityUser = new SecurityUserDetails();
        securityUser.setStEmail(user.getStEmail());
        securityUser.setPassword(user.getStPassword());
        securityUser.setIsEnable(user.getIsActive());
        securityUser.setCreated(user.getDtCreate());
        securityUser.setModified(user.getDtModify());
        securityUser.setLastLogin(user.getDtLastConnection());
        securityUser.setRole(loadRoleBySecUserRole(user).orElse(Role.USER));
        user.setJwToken(user.getIsActive() ? jwtTokenUtil.generateToken(securityUser) : null );
        secUserRepository.save(user);

        return securityUser;
    }

    /**
     * Util para cargar el rol o los roles correspondiente
     * @param user
     * @return
     */
    private Optional<Role> loadRoleBySecUserRole(SecUser user) {
        Optional<Role> role = Optional.ofNullable(user.getSecUserRoles())
                .map(roles -> roles.stream()
                        .filter(userRole -> Objects.equals("ADMIN",
                                userRole.getSecRoleByFkRole().getCdRole()) &&
                                userRole.getSecRoleByFkRole().getIsActive())
                        .map(userRole -> Role.ADMIN)
                        .findFirst()
                ).orElse(null);
        return role;
    }

    /**
     * util para responder a la solicitud de registro de usuario
     * validando correo
     * @param authenticationRequest
     * @return
     */
    public ApiResponse resultOfLoadUserByEmail(JwtRequest authenticationRequest) {
        String usermail = authenticationRequest.getEmail();
        try {
            UserDetails userDetails = loadUserByUsername(usermail);
        }catch (UsernameNotFoundException e){
            return new ApiResponse(false, usermail + Constants.USER_NOT_FOUND);
        }
        ApiResponse apiResponse = new ApiResponse(false,  Constants.MSG_ERROR_GRAL);
        Optional<SecUser> userOptionalUpdated = secUserRepository.findSecUsersByStEmail(usermail);
        if(userOptionalUpdated.isPresent()){
            SecUser userUpdated = userOptionalUpdated.orElse(new SecUser());
            UserDto userDto = new UserDto();
            DateTimeFormatter ft = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            userDto.setId(userUpdated.getIdUser());
            userDto.setUserEmail(userUpdated.getStEmail());
            userDto.setName(userUpdated.getStName());
            userDto.setCreated(ft.format(userUpdated.getDtCreate()));
            userDto.setModified(userUpdated.getDtModify() != null ? ft.format(userUpdated.getDtModify()) : "");
            userDto.setLastLogin(ft.format(userUpdated.getDtLastConnection()));
            userDto.setCreated(userUpdated.getStCreUser());
            userDto.setToken(userUpdated.getJwToken());
            userDto.setIsActive(userUpdated.getIsActive());

            apiResponse.setSuccess(true);
            apiResponse.setMessage(Constants.MSG_AUTHENTICATE_SUCCESS);
            apiResponse.setData(userDto);
            if(!userDto.getIsActive()){
                return new ApiResponse (false, usermail + Constants.USER_DESACTIVATED , userDto);
            }
        }

        return apiResponse;

    }
}
