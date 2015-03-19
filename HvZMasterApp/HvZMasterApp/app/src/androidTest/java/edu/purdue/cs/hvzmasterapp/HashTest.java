package edu.purdue.cs.hvzmasterapp;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

/**
 * Created by Wells on 3/19/2015.
 */
public class HashTest extends ActivityInstrumentationTestCase2<RegisterActivity> {
    private RegisterActivity reg;

    public HashTest() {
        super(RegisterActivity.class);
    }

    public void testHash() {
        reg = getActivity();
        String pass = "test";
        String hash = reg.hash(pass);
        assertEquals("Check hash", "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", hash);
    }
}
