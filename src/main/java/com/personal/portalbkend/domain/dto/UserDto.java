package com.personal.portalbkend.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class UserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -926682114024290700L;

    private Long id;
    private String name;
    private String userEmail;
    private String created;
    private String modified;
    private String lastLogin;
    private String token;
    private Boolean isActive;

}
