/*
 * Copyright 2017 CMPUT301W17T07
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

