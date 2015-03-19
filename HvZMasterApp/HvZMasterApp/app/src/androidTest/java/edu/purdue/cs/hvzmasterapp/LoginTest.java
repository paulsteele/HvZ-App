package edu.purdue.cs.hvzmasterapp;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Wells on 3/19/2015.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private LoginActivity login;

    public LoginTest() {
        super(LoginActivity.class);
    }

    public void testLogin() {
        login = getActivity();
        String id = "test";
        String pass = "test";
        String hash = login.hash(pass);
        assertEquals(0, Server.getInstance().login(id, hash));
    }
}
