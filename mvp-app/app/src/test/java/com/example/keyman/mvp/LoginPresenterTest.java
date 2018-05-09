package com.example.keyman.mvp;

import com.example.keyman.mvp.contract.LoginContract;
import com.example.keyman.mvp.model.LoginModel;
import com.example.keyman.mvp.presenter.LoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

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

        when(mockModel.isPasswordValid(anyString())).thenReturn(false);

        mPresenter.attemptLogin("foo@bar.com", "bar");
        verify(mockModel).isPasswordValid("bar");
        verify(mockView).setPasswordError(R.string.error_invalid_password);
    }

    @Test
    public void testEmailIsEmpty() {

        when(mockModel.isEmptyEmail(anyString())).thenReturn(true);

        mPresenter.attemptLogin("", "baar");
        verify(mockModel).isEmptyEmail("");
        verify(mockView).setEmailError(R.string.error_field_required);
    }

    @Test
    public void testEmailIsValid() {

        when(mockModel.isEmailValid(anyString())).thenReturn(false);

        mPresenter.attemptLogin("foo", "bar");
        verify(mockModel).isEmailValid("foo");
        verify(mockView).setEmailError(R.string.error_invalid_email);
    }
}
