package com.personal.portalbkend.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserDtoToFind implements Serializable {


    @Serial
    private static final long serialVersionUID = 1390558107051238201L;

    private String name;
    private String email;
    private String number;
    private Boolean isActive;
}
