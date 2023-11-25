package com.personal.portalbkend.service;


import com.personal.portalbkend.common.ApiResponse;
import com.personal.portalbkend.common.Constants;
import com.personal.portalbkend.common.UtilHelper;
import com.personal.portalbkend.domain.SecPhoneBook;
import com.personal.portalbkend.domain.dto.PhoneData;
import com.personal.portalbkend.domain.dto.UserDtoToFind;
import com.personal.portalbkend.repository.SecPhoneBookRepository;
import com.personal.portalbkend.security.SecurityHelper;
import com.personal.portalbkend.domain.SecUser;
import com.personal.portalbkend.domain.dto.UserDto;
import com.personal.portalbkend.domain.dto.UserNewDto;
import com.personal.portalbkend.repository.SecUserRepository;
import com.personal.portalbkend.security.jwt.JwtTokenUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SecUserServiceImpl implements SecUserService {
    private final SecUserRepository secUserRepository;
    private final SecPhoneBookRepository secPhoneBookRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public SecUserServiceImpl(SecUserRepository secUserRepository, PasswordEncoder passwordEncoder, SecPhoneBookRepository secPhoneBookRepository, JwtTokenUtil jwtTokenUtil) {
        this.secUserRepository = secUserRepository;
        this.secPhoneBookRepository = secPhoneBookRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    /**
     * Creaccion con las reglas de negocio solicitadas en
     * Apartado 1. Endpoint para registrar usuario
     * @param userNewDto
     * @return
     */
    @Override
    @Transactional
    public ApiResponse createNewUser(UserNewDto userNewDto) {
        if(!UtilHelper.isValidEmail(userNewDto.getEmail())){
            return new ApiResponse(false, userNewDto.getEmail() + Constants.MSG_EMAIL_INVALID );
        }
        Optional<SecUser> secUsersByCdUser = secUserRepository.findSecUsersByStEmail(userNewDto.getEmail());
        if (secUsersByCdUser.isPresent()) {
            return new ApiResponse(false, Constants.MSG_USER_REGISTERED, userNewDto);
        }
        if(!UtilHelper.isValidPassword(userNewDto.getPassword())){
            return new ApiResponse(false, Constants.MSG_PASS_INVALID );
        }
        SecUser userNew = new SecUser();
        LocalDateTime now = LocalDateTime.now();
        userNew.setStEmail(userNewDto.getEmail());
        userNew.setStName(userNewDto.getName());
        userNew.setStPassword(passwordEncoder.encode(userNewDto.getPassword()));
        userNew.setDtCreate(now);
        userNew.setStCreUser("FrontEnd");
        userNew.setIsActive(true);
        userNew.setDtLastConnection(now);
        userNew.setJwToken(userNew.getIsActive() ? jwtTokenUtil.generateToken(userNewDto.getEmail()) : null);
        secUserRepository.save(userNew);
        if (userNewDto.getPhones() != null) {
            for (PhoneData phone : userNewDto.getPhones()) {
                if (phone.getNumber() != null && !phone.getNumber().isEmpty()) {
                    try {
                        SecPhoneBook phoneBook = new SecPhoneBook();
                        phoneBook.setSecUserByIdUser(userNew);
                        phoneBook.setNuNumber(Integer.parseInt(phone.getNumber()));
                        phoneBook.setCdCityCode((phone.getCitycode() != null && !phone.getCitycode().isEmpty())? Integer.parseInt(phone.getCitycode()): null);
                        phoneBook.setCdContryCode((phone.getContrycode() != null && !phone.getContrycode().isEmpty())? Integer.parseInt(phone.getContrycode()): null);
                        secPhoneBookRepository.save(phoneBook);
                    } catch (NumberFormatException e) {

                    }

                }
            }
        }
        return convertSecUserToDtoOnApiResponse(userNew);
    }

    /**
     * Servicio para activar
     * depredecate!
     * se sustituye por activateOrDisableUser
     * @param userDto
     * @return
     */
    @Override
    public ApiResponse activateUser(UserDto userDto) {
        ApiResponse apiResponse = new ApiResponse(false,
                "No es posible activar " + userDto.getUserEmail() + Constants.USER_NOT_FOUND);
        Optional<SecUser> userToActivate = secUserRepository.findSecUsersByStEmail(userDto.getUserEmail());
        if (userToActivate.isPresent()) {
            userToActivate.get().setIsActive(true);
            userToActivate.get().setStModUser(SecurityHelper.getLoggedInUserDetails().getStEmail());
            userToActivate.get().setDtModify(LocalDateTime.now());
            secUserRepository.save(userToActivate.get());
            ApiResponse apiResponseActivado  = convertSecUserToDtoOnApiResponse(userToActivate.get());
            apiResponseActivado.setMessage(Constants.MSG_ACTIVATED_SUCCESS);
            return apiResponseActivado;
        }
        return apiResponse;
    }

    /**
     * Servicio para desactivar o activar Usuario
     * @param stEmail
     * @param isActivate
     * @return
     */
    @Override
    public ApiResponse activateOrDisableUser(String stEmail, Boolean isActivate) {
        ApiResponse apiResponse = new ApiResponse(false,
                "No es posible realizar " + stEmail + Constants.USER_NOT_FOUND);
        Optional<SecUser> userToActivate = secUserRepository.findSecUsersByStEmail(stEmail);
        if (userToActivate.isPresent()) {
            userToActivate.get().setIsActive(isActivate);
            userToActivate.get().setStModUser(SecurityHelper.getLoggedInUserDetails().getStEmail());
            userToActivate.get().setDtModify(LocalDateTime.now());
            secUserRepository.save(userToActivate.get());
            ApiResponse apiResponseActivado  = convertSecUserToDtoOnApiResponse(userToActivate.get());
            apiResponseActivado.setMessage(isActivate ? Constants.MSG_ACTIVATED_SUCCESS : Constants.MSG_DISABLE_SUCCESS);
            return apiResponseActivado;
        }
        return apiResponse;
    }

    /**
     * Para listar todos los usuarios
     * @return
     */
    @Override
    public ApiResponse findAllForAuthenticatedUser() {
        ApiResponse apiResponse = new ApiResponse(false, Constants.USERS_NOT_FOUND);
        List<SecUser> secUsers = secUserRepository.findAll();
        List<UserDto> usersToFront = secUsers.stream().map(this::convertSecUserToDto).collect(Collectors.toList());
        if(usersToFront.size() > 0 ){
            apiResponse.setSuccess(true);
            apiResponse.setMessage(Constants.MSG_USERS_FOUND + usersToFront.size());
            apiResponse.setData((Serializable) usersToFront);
        }

        return apiResponse;
    }

    /**
     * Servicio que busca usuarios por los filtros solicitados
     * @param userDtoToFind
     * @return
     */
    @Override
    public ApiResponse findByUserFilters(UserDtoToFind userDtoToFind) {
        ApiResponse apiResponse = new ApiResponse(false, Constants.USERS_NOT_FOUND);
        List<SecUser> users = secUserRepository.findByUserFilters(userDtoToFind.getEmail().isEmpty() ? null : userDtoToFind.getEmail(),
                                                                    userDtoToFind.getName().isEmpty() ? null : userDtoToFind.getName(),
                                                                    userDtoToFind.getNumber().isEmpty() ? null : userDtoToFind.getNumber(),
                                                                    userDtoToFind.getIsActive());
        List<UserDto> usersFound = users.stream().map(this::convertSecUserToDto).collect(Collectors.toList());
        if(usersFound.size() > 0 ){
            apiResponse.setSuccess(true);
            apiResponse.setMessage(Constants.MSG_USERS_FOUND + usersFound.size());
            apiResponse.setData((Serializable) usersFound);
        }

        return apiResponse;
    }

    /**
     * Util para retornar el objeto UserDto requerido por el front en un ApiResponse
     * @param secUser
     * @return
     */
    private ApiResponse convertSecUserToDtoOnApiResponse(SecUser secUser) {
        ApiResponse apiResponse = new ApiResponse(false, Constants.MSG_ERROR_GRAL);
        UserDto userDto = convertSecUserToDto(secUser);
        apiResponse.setSuccess(true);
        apiResponse.setMessage(Constants.MSG_REGISTER_SUCCESS);
        apiResponse.setData(userDto);

        return apiResponse;
    }


    private UserDto convertSecUserToDto(SecUser secUser){
        UserDto userDto = new UserDto();
        DateTimeFormatter ft = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        userDto.setId(secUser.getIdUser());
        userDto.setUserEmail(secUser.getStEmail());
        userDto.setName(secUser.getStName());
        userDto.setCreated(ft.format(secUser.getDtCreate()));
        userDto.setModified(secUser.getDtModify() != null ? ft.format(secUser.getDtModify()) : "");
        userDto.setLastLogin(ft.format(secUser.getDtLastConnection()));
        userDto.setCreated(secUser.getStCreUser());
        userDto.setToken(secUser.getJwToken());
        userDto.setIsActive(secUser.getIsActive());
        return userDto;
    }
}
