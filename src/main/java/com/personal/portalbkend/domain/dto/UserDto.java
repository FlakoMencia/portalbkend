package com.personal.portalbkend.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class UserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -926682114024290700L;

    private Long userId;
    private String userName;
    private String userEmail;
    private String userStatus;

}
