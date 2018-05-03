package com.example.keyman.mvp.task;

import android.os.AsyncTask;

import com.example.keyman.mvp.R;
import com.example.keyman.mvp.contract.LoginContract;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 * <p>
 * Created by keyman on 4/30/2018.
 */
public class LoginTask extends AsyncTask<Void, Void, Boolean> {

    private LoginContract.View mLoginView;
    private LoginContract.Model mLoginModel;
    private final String mEmail;
    private final String mPassword;


    public LoginTask(LoginContract.View loginView, LoginContract.Model loginModel, String email, String password) {
        mLoginView = loginView;
        mLoginModel = loginModel;
        mEmail = email;
        mPassword = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mLoginView.showProgress(true);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }

        return mLoginModel.registerAccount(mEmail, mPassword);
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        mLoginView.showProgress(false);

        if (success) {
            mLoginView.finish();
        } else {
            mLoginView.setPasswordError(mLoginView.getActivityContext().getString(R.string.error_incorrect_password));

        }
    }

    @Override
    protected void onCancelled() {
        mLoginView.showProgress(false);
    }
}
