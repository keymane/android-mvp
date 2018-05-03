package com.example.keyman.mvp.model;

import android.text.TextUtils;

import com.example.keyman.mvp.contract.LoginContract;

/**
 * Created by keyman on 4/6/2018.
 */

public class LoginModel implements LoginContract.Model {

    private LoginContract.Presenter mLoginPresenter;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    public LoginModel(LoginContract.Presenter loginPresenter) {
        this.mLoginPresenter = loginPresenter;
    }

    /**
     * Called by Presenter when View is destroyed
     *
     * @param isChangingConfiguration true configuration is changing
     */
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
