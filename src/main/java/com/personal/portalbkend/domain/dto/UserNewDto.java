package com.personal.portalbkend.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class UserNewDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2663291631566557254L;

    private String name;
    private String email;
    private String password;
    private List<PhoneData> phones;


}
