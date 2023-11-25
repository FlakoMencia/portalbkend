package com.personal.portalbkend.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PhoneData implements Serializable {

    @Serial
    private static final long serialVersionUID = 7056676655082368881L;

    private String number;
    private String citycode;
    private String contrycode;
}
