package com.example.keyman.mvp.presenter;

import android.view.View;

import com.example.keyman.mvp.R;
import com.example.keyman.mvp.contract.LoginContract;
import com.example.keyman.mvp.model.LoginModel;
import com.example.keyman.mvp.task.LoginTask;

import java.lang.ref.WeakReference;

/**
 * Created by keyman on 4/6/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {

    // View reference. We use as a WeakReference
    // because the Activity could be destroyed at any time
    // and we don't want to create a memory leak
    private WeakReference<LoginContract.View> mLoginView;

    private LoginModel mLoginModel;

    public LoginPresenter(LoginContract.View loginView) {
        mLoginView = new WeakReference<>(loginView);
        mLoginModel = new LoginModel(this);
    }

    public void setmLoginModel(LoginModel mLoginModel) {
        this.mLoginModel = mLoginModel;
    }

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

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    @Override
    public void attemptLogin(String email, String password) {
        // Reset errors.
        getLoginView().resetEmailError();
        getLoginView().resetPaswordError();

        // Store values at the time of the login attempt.
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!mLoginModel.isPasswordValid(password)) {
            getLoginView().setPasswordError(R.string.error_invalid_password);
            cancel = true;
        }

        // Check for a valid email address.
        if (mLoginModel.isEmptyEmail(email)) {
            getLoginView().setEmailError(R.string.error_field_required);
            cancel = true;
        } else if (!mLoginModel.isEmailValid(email)) {
            getLoginView().setEmailError(R.string.error_invalid_email);
            cancel = true;
        }

        if (!cancel) {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            new LoginTask(getLoginView(), mLoginModel, email, password).execute((Void) null);
        }
    }


    private LoginContract.View getLoginView() throws NullPointerException {
        if (mLoginView != null)
            return mLoginView.get();
        else
            throw new NullPointerException("Login View is unavailable");
    }

}
