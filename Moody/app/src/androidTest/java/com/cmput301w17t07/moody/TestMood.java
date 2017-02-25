package com.cmput301w17t07.moody;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import static com.cmput301w17t07.moody.Mood.Feeling.happy;
import static com.cmput301w17t07.moody.Mood.Feeling.sad;

/**
 * Created by Panchy on 2017/2/24.
 */

public class TestMood extends ActivityInstrumentationTestCase2{
    public TestMood() {
        super(Mood.class);

    }

    @Test
    public void testGetMoodMessage(){
        Mood mood=new Mood("happy");
        assertEquals(mood.getMoodMessage(),"happy");
    }

    @Test
    public void testSetMoodMessage(){
        Mood mood=new Mood("happy");
        mood.setMoodMessage("sad");
        assertEquals(mood.getMoodMessage(),"sad");
    }

    @Test
    public void testGetFeeling(){
        Mood mood=new Mood(happy);
        assertEquals(mood.getFeeling(),happy);
    }

    @Test
    public void testSetFeeling(){
        Mood mood=new Mood(happy);
        mood.setFeeling(sad);
        assertEquals(mood.getFeeling(),sad);
    }

}
