package com.chareun410.miagenda.interactor;

import android.app.Activity;

public class LoginInteractor {
    private Activity actividad;
    private String VALID_USERNAME = "pepita";
    private String VALID_PASSWORD = "123";

    public LoginInteractor(Activity actividad) {
        this.actividad = actividad;
    }

    public boolean validateCredentials(String user, String password) {
        return user.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD);
    }

}
