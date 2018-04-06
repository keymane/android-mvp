package com.example.keyman.mvp.presenter;

import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.keyman.mvp.view.LoginView;

import java.lang.ref.WeakReference;

/**
 * Created by keyman on 4/6/2018.
 */

public interface LoginPresenter {

    void onDestroy(boolean isChangingConfiguration);

    void setView(LoginView view);

    void attemptLogin(AutoCompleteTextView emailView, EditText passwordView);

}
