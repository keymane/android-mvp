package com.example.keyman.mvp.presenter.impl;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.keyman.mvp.R;
import com.example.keyman.mvp.model.LoginModel;
import com.example.keyman.mvp.presenter.LoginPresenter;
import com.example.keyman.mvp.view.LoginView;

import java.lang.ref.WeakReference;

/**
 * Created by keyman on 4/6/2018.
 */

public class LoginPresenterImpl implements LoginPresenter {

    // View reference. We use as a WeakReference
    // because the Activity could be destroyed at any time
    // and we don't want to create a memory leak
    private WeakReference<LoginView> mLoginView;

    private LoginModel mLoginModel;

    private UserLoginTask mAuthTask = null;

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = new WeakReference<LoginView>(loginView);
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        // View show be null every time onDestroy is called
        mLoginView = null;
        // Inform Model about the event
        mLoginModel.onDestroy(isChangingConfiguration);
        // Activity destroyed
        if (!isChangingConfiguration) {
            // Nulls Model when the Activity destruction is permanent
            mLoginModel = null;
        }
    }


    public void setLoginModel(LoginModel loginModel) {
        this.mLoginModel = loginModel;
    }

    @Override
    public void setView(LoginView loginView) {
        mLoginView = new WeakReference<LoginView>(loginView);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin(AutoCompleteTextView mEmailView, EditText mPasswordView) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        getLoginView().setEmailError(null);
        getLoginView().setPasswordError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!mLoginModel.isPasswordValid(password)) {
            mPasswordView.setError(getLoginView().getActivityContext().getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (mLoginModel.isEmptyEmail(email)) {
            mEmailView.setError(getLoginView().getActivityContext().getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!mLoginModel.isEmailValid(email)) {
            mEmailView.setError(getLoginView().getActivityContext().getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            getLoginView().showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }


    private LoginView getLoginView() throws NullPointerException {
        if (mLoginView != null)
            return mLoginView.get();
        else
            throw new NullPointerException("Login View is unavailable");
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        private UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
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
            mAuthTask = null;
            getLoginView().showProgress(false);

            if (success) {
                getLoginView().finish();
            } else {
                getLoginView().setPasswordError(getLoginView().getActivityContext().getString(R.string.error_incorrect_password));

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            getLoginView().showProgress(false);
        }
    }
}
