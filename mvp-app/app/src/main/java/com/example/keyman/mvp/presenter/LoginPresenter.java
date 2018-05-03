package com.example.keyman.mvp.presenter;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

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
    public void attemptLogin(AutoCompleteTextView mEmailView, EditText mPasswordView) {
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
