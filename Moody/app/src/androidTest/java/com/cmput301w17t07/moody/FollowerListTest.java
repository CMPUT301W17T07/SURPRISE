package com.cmput301w17t07.moody;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Created by mike on 2017-02-24.
 */

public class FollowerListTest extends ActivityInstrumentationTestCase2{

    @Test
    public void testAddMood() {
        MoodList moodList=new MoodList();
        Mood mood=new Mood("happy");
        moodList.addMood(mood);
        assertEquals(moodList.countMoodList(),1);

    }

}
