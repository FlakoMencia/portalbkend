package com.personal.portalbkend.service;

import com.personal.portalbkend.common.ApiResponse;
import com.personal.portalbkend.domain.dto.UserDto;
import com.personal.portalbkend.domain.dto.UserDtoToFind;
import com.personal.portalbkend.domain.dto.UserNewDto;

import java.util.List;
import java.util.Optional;

public interface SecUserService {


    ApiResponse createNewUser(UserNewDto userNewDto);

    ApiResponse activateUser(UserDto userName);
    ApiResponse activateOrDisableUser(String userName, Boolean isActivate);

    ApiResponse findAllForAuthenticatedUser();

    ApiResponse findByUserFilters(UserDtoToFind userDtoToFind);
}
