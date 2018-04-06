package com.example.keyman.mvp.model;

/**
 * Created by keyman on 4/6/2018.
 */

public interface LoginModel {

    void onDestroy(boolean isChangingConfiguration);

    boolean isEmailValid(String email);

    boolean isEmptyEmail(String email);

    boolean isPasswordValid(String password);

    boolean registerAccount(String mEmail, String mPassword);
}
