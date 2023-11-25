package com.personal.portalbkend.security.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 2059675473470312285L;


	private String email;
	private String password;
	
	
}