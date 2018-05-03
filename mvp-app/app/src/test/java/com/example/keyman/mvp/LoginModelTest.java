package com.example.keyman.mvp;

import android.content.Context;

import com.example.keyman.mvp.model.LoginModel;
import com.example.keyman.mvp.presenter.LoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.reset;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class LoginModelTest {

    private LoginModel mLoginModel;

    @Before
    public void setup() {
        Context context = RuntimeEnvironment.application;

        LoginPresenter mockPresenter = Mockito.mock(LoginPresenter.class);
        mLoginModel = new LoginModel(mockPresenter);

        reset(mockPresenter);
    }

    @Test
    public void testIsValidEmail() {
        assertTrue(mLoginModel.isEmailValid("foo@example.com"));
        assertTrue(mLoginModel.isEmailValid("bar@example.com"));
        assertFalse(mLoginModel.isEmailValid("barexample.com"));
        assertFalse(mLoginModel.isEmailValid("examplecom."));

    }

    @Test
    public void testIsEmptyEmail() {
        assertTrue(mLoginModel.isEmptyEmail(null));
        assertTrue(mLoginModel.isEmptyEmail(""));
        assertFalse(mLoginModel.isEmptyEmail("barexample.com"));
        assertFalse(mLoginModel.isEmptyEmail("bar@examplecom"));

    }

    @Test
    public void testIsValidPassword() {
        assertTrue(mLoginModel.isPasswordValid("hello"));
        assertTrue(mLoginModel.isPasswordValid("world"));
        assertFalse(mLoginModel.isPasswordValid(""));
        assertFalse(mLoginModel.isPasswordValid(null));
        assertFalse(mLoginModel.isPasswordValid("he1o"));

    }

    @Test
    public void testRegisterAccountPassword() {
        assertTrue(mLoginModel.registerAccount("foo@example.com", "hello"));
        assertTrue(mLoginModel.registerAccount("bar@example.com", "world"));
        assertFalse(mLoginModel.registerAccount("foo@example.com", "world"));
        assertFalse(mLoginModel.registerAccount("bar@example.com", "hello"));
    }
}
