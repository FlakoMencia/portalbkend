package com.personal.portalbkend.common;

import org.springframework.beans.factory.annotation.Value;

import java.util.regex.Pattern;

public class UtilHelper {
    private UtilHelper() {
        throw new IllegalStateException("Utility class");
    }

    @Value("${user.valid.format.pass.regexp}")
    private String passRegex;

    @Value("${user.valid.format.email.regexp}")
    private String emailRegex;


    /**
     * Util para para validar un correo electrónico básico por medio de expresion regular
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(emailRegex, email);
    }

    /**
     * Util para validar formato de contrasena
     * que contenga al menos 5 caracteres,
     * una letra mayúscula, una letra minúscula y un dígito
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{5,}$";
        return Pattern.matches(passwordRegex, password);
    }

}
