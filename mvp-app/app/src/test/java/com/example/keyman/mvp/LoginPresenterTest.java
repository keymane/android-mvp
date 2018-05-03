package com.example.keyman.mvp;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.keyman.mvp.contract.LoginContract;
import com.example.keyman.mvp.model.LoginModel;
import com.example.keyman.mvp.presenter.LoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class LoginPresenterTest {

    private LoginPresenter mPresenter;
    private LoginModel mockModel;
    private LoginContract.View mockView;

    @Before
    public void setup() {
        mockView = Mockito.mock(LoginContract.View.class);
        mockModel = Mockito.mock(LoginModel.class, RETURNS_DEEP_STUBS);
        mPresenter = new LoginPresenter(mockView);
        mPresenter.setmLoginModel(mockModel);

        reset(mockView);
    }

    @Test
    public void testPasswordTooShort() {
        AutoCompleteTextView mockAutoCompleteTextView = Mockito.mock(AutoCompleteTextView.class, RETURNS_DEEP_STUBS);
        EditText mockEditText = Mockito.mock(EditText.class, RETURNS_DEEP_STUBS);
        Context mockContext = Mockito.mock(Context.class, RETURNS_DEEP_STUBS);

        when(mockAutoCompleteTextView.getText().toString()).thenReturn("foo@bar.com");
        when(mockEditText.getText().toString()).thenReturn("bar");
        when(mockModel.isPasswordValid(anyString())).thenReturn(false);
        when(mockView.getActivityContext()).thenReturn(mockContext);
        when(mockContext.getString(anyInt())).thenReturn("This password is too short");

        mPresenter.attemptLogin(mockAutoCompleteTextView, mockEditText);
        verify(mockModel).isPasswordValid("bar");
        verify(mockEditText).setError("This password is too short");
    }

    @Test
    public void testEmailIsEmpty() {
        AutoCompleteTextView mockAutoCompleteTextView = Mockito.mock(AutoCompleteTextView.class, RETURNS_DEEP_STUBS);
        EditText mockEditText = Mockito.mock(EditText.class, RETURNS_DEEP_STUBS);
        Context mockContext = Mockito.mock(Context.class, RETURNS_DEEP_STUBS);

        when(mockAutoCompleteTextView.getText().toString()).thenReturn("");
        when(mockEditText.getText().toString()).thenReturn("baaar");
        when(mockModel.isEmptyEmail(anyString())).thenReturn(true);
        when(mockView.getActivityContext()).thenReturn(mockContext);
        when(mockContext.getString(anyInt())).thenReturn("This field is required");

        mPresenter.attemptLogin(mockAutoCompleteTextView, mockEditText);
        verify(mockModel).isEmptyEmail("");
        verify(mockAutoCompleteTextView).setError("This field is required");
    }

    @Test
    public void testEmailIsValid() {
        AutoCompleteTextView mockAutoCompleteTextView = Mockito.mock(AutoCompleteTextView.class, RETURNS_DEEP_STUBS);
        EditText mockEditText = Mockito.mock(EditText.class, RETURNS_DEEP_STUBS);
        Context mockContext = Mockito.mock(Context.class, RETURNS_DEEP_STUBS);

        when(mockAutoCompleteTextView.getText().toString()).thenReturn("foo");
        when(mockEditText.getText().toString()).thenReturn("baaar");
        when(mockModel.isEmailValid(anyString())).thenReturn(false);
        when(mockView.getActivityContext()).thenReturn(mockContext);
        when(mockContext.getString(anyInt())).thenReturn("This email address is invalid");

        mPresenter.attemptLogin(mockAutoCompleteTextView, mockEditText);
        verify(mockModel).isEmailValid("foo");
        verify(mockAutoCompleteTextView).setError("This email address is invalid");
    }
}
