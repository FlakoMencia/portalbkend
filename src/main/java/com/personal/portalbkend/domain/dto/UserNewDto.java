package com.personal.portalbkend.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserNewDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2663291631566557254L;

    private String userName;
    private String userEmail;
    private String password;

}
