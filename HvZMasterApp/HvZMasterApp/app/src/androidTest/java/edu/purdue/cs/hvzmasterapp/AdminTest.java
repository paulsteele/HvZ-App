package edu.purdue.cs.hvzmasterapp;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

/**
 * Created by Wells on 3/23/2015.
 */
public class AdminTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity main;

    public AdminTest() {
        super(MainActivity.class);
    }

    protected void setUp() {
        try {
            super.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        main = getActivity();
        //main.isLoggedIn = true;
       // main.self = new User("admin", "a", false, true);
    }

    public void testLogin() {
        TextView view = (TextView) main.findViewById(R.id.adminlabel);
        assertEquals("Admin: admin", view.toString());
    }
}
