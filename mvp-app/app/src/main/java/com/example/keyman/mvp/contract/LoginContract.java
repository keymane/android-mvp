package com.example.keyman.mvp.contract;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

/**
 * Created by keyman on 4/30/2018.
 */

public interface LoginContract {
    interface Presenter {
        void attemptLogin(AutoCompleteTextView mEmailView, EditText mPasswordView);
    }

    interface View {
        Context getActivityContext();

        void setEmailError(String emailError);

        void setPasswordError(String passwordError);

        void showProgress(final boolean show);

        void finish();
    }

    interface Model {
        boolean registerAccount(String mEmail, String mPassword);
    }

}
