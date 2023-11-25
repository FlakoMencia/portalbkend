package com.personal.portalbkend.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


/**
 * Se crea esta clase como respuesta estandar del API ya que solicitaron responder UNICAMENTE CON JSON!!
 * de esta manera se evita el tipico ResponseEntity<clase y HttpStatus >
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 462167435507047287L;

    private boolean success;
    private String message;
    private Serializable data;

    public ApiResponse(boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
