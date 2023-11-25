package com.personal.portalbkend.controller;

import com.personal.portalbkend.common.ApiResponse;
import com.personal.portalbkend.domain.dto.UserDto;
import com.personal.portalbkend.domain.dto.UserDtoToFind;
import com.personal.portalbkend.domain.dto.UserNewDto;
import com.personal.portalbkend.security.dto.JwtRequest;
import com.personal.portalbkend.service.SecUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class SecUserController {

    private final SecUserService secUserService;

    public SecUserController(SecUserService secUserService) {
        this.secUserService = secUserService;
    }

    /**
     * Lista los usuarios para
     * peticiones con token valido (authenticado)
     *
     * @return ApiResponse
     * con el resultado, mensaje y datos correspodientes
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('admin:read, user:read')")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getUserList() {
        return secUserService.findAllForAuthenticatedUser();
    }

    /**
     * Util para registrar usuario como se solicito en PDF
     * Numero : 1. Endpoint para registrar usuario
     * @param userNewDto
     * @return ApiResponse
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse registerUser(@RequestBody @Validated UserNewDto userNewDto) {
        return secUserService.createNewUser(userNewDto);
    }

    /**
     * Util para buscar usuario
     * Numero 2. Endpoint para consultar usuarios
     * @param userDtoToFind
     * @return
     */
    @GetMapping(value = "/find")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody ApiResponse findUser(@RequestBody UserDtoToFind userDtoToFind) {
        return secUserService.findByUserFilters(userDtoToFind);
    }

    /**
     * Util para activar usuario
     * Solamente rol ADMIN
     * @param userNewDto
     * @return
     */
    @PatchMapping(value = "/activate", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin:update')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody ApiResponse activateUser(@RequestBody UserNewDto userNewDto) {
        return secUserService.activateOrDisableUser(userNewDto.getEmail(), true);
    }

    /**
     * Util para desactivar usuario
     * Solamente rol ADMIN
     * @param userNewDto
     * @return
     */
    @PatchMapping(value = "/disable", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin:update')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody ApiResponse disableUser(@RequestBody UserNewDto userNewDto) {
        return secUserService.activateOrDisableUser(userNewDto.getEmail(), false);
    }
}
