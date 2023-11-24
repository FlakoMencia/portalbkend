package com.personal.portalbkend.security.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {


	@Serial
	private static final long serialVersionUID = 4082296481407291034L;

	private final String jwttoken;

}
