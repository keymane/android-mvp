package com.example.keyman.mvp.model.impl;

import android.text.TextUtils;

import com.example.keyman.mvp.model.LoginModel;
import com.example.keyman.mvp.presenter.LoginPresenter;

/**
 * Created by keyman on 4/6/2018.
 */

public class LoginModelImpl implements LoginModel {
    private LoginPresenter mLoginPresenter;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    public LoginModelImpl(LoginPresenter loginPresenter) {
        this.mLoginPresenter = loginPresenter;
    }

    /**
     * Called by Presenter when View is destroyed
     *
     * @param isChangingConfiguration true configuration is changing
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mLoginPresenter = null;
        }
    }


    public boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return !TextUtils.isEmpty(password) && password.length() > 4;
    }

    @Override
    public boolean isEmptyEmail(String email) {
        return TextUtils.isEmpty(email);
    }

    @Override
    public boolean registerAccount(String mEmail, String mPassword) {
        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(mEmail)) {
                // Account exists, return true if the password matches.
                return pieces[1].equals(mPassword);
            }
        }

        // TODO: register the new account here.
        return true;
    }
}
