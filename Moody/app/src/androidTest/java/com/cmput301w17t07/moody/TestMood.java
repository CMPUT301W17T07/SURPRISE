package com.cmput301w17t07.moody;

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.Date;


/**
 * Created by Panchy on 2017/2/24.
 */

/**
 * Tests for the Mood class model.
 */


public class TestMood extends ActivityInstrumentationTestCase2{

    private Location location;
    public TestMood() {
        super(Mood.class);

    }


    @Test
    public void testgetUsername() throws Exception {
        Mood mood=new Mood("happy","xin","mes",null,null,"soc");
        assertEquals(mood.getUsername(),"xin");
    }

    @Test
    public void testsetUsername() throws Exception {
        Mood mood=new Mood("happy","xin","mes",null,null,"soc");
        mood.setUsername("pang");
        assertEquals(mood.getUsername(),"pang");

    }

    @Test
    public void testGetMoodMessage(){
        Mood mood=new Mood("happy","xin","mes",null,null,"soc");
        assertEquals(mood.getFeeling(),"happy");
    }

    @Test
    public void testSetMoodMessage(){
        Mood mood=new Mood("happy","xin","mes",null,null,"soc");
        mood.setMoodMessage("sad");
        assertEquals(mood.getMoodMessage(),"sad");
    }

    @Test
    public void testGetFeeling(){
        Mood mood=new Mood("happy","xin","mes",null,null,"soc");
        assertEquals(mood.getFeeling(),"happy");
    }

    @Test
    public void testSetFeeling(){
        Mood mood=new Mood("happy","xin","mes",null,null,"soc");
        mood.setFeeling("sad");
        assertEquals(mood.getFeeling(),"sad");
    }

    @Test
    public void testgetMoodImageID() throws Exception {
        Mood mood=new Mood("happy","xin","mes",null,"1","soc");
        assertEquals(mood.getMoodImageID(),"1");

    }

    @Test
    public void testsetMoodImageID() throws Exception {
        Mood mood=new Mood("happy","xin","mes",null,"1","soc");
        mood.setMoodImageID("2");
        assertEquals(mood.getMoodImageID(),"2");
    }


}

