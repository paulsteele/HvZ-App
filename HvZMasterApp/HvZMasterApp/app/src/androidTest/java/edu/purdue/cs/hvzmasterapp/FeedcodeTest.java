package edu.purdue.cs.hvzmasterapp;

import android.test.AndroidTestCase;

/**
 * Created by Wells on 3/19/2015.
 */
public class FeedcodeTest extends AndroidTestCase {
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetFeedcode() {
        Server s = Server.getInstance();
        String code = s.getNewFeedcode(false);
        assertNotNull(code);
    }
}
