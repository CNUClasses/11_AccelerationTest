package com.example.test;

import android.test.InstrumentationTestCase;

/**
 * Created by Perkins on 3/25/2015.
 */
public class ExampleTest extends InstrumentationTestCase{
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }
}
