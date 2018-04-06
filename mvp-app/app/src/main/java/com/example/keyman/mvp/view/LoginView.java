package com.example.keyman.mvp.view;

import android.content.Context;

import java.util.List;

/**
 * Created by keyman on 4/6/2018.
 */

public interface LoginView {

    Context getActivityContext();

    void setEmailError(String emailError);

    void setPasswordError(String passwordError);

    void showProgress(final boolean show);

    void finish();

}
