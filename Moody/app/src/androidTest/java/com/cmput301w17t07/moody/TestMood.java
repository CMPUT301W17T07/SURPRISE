
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

import android.graphics.Bitmap;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


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
        Mood mood=new Mood("happy","xin","mes",0,0,null,null,null,null);
        assertEquals(mood.getUsername(),"xin");
    }

    @Test
    public void testsetUsername() throws Exception {
        Mood mood=new Mood("happy","xin","mes",0,0,null,null,null,null);
        mood.setUsername("pang");
        assertEquals(mood.getUsername(),"pang");

    }

    @Test
    public void testGetMoodMessage(){
        Mood mood=new Mood("happy","xin","mes",0,0,null,null,null,null);
        assertEquals(mood.getFeeling(),"happy");
    }

    @Test
    public void testSetMoodMessage(){
        Mood mood=new Mood("happy","xin","mes",0,0,null,null,null,null);
        mood.setMoodMessage("sad");
        assertEquals(mood.getMoodMessage(),"sad");
    }

    @Test
    public void testGetFeeling(){
        Mood mood=new Mood("happy","xin","mes",0,0,null,null,null,null);
        assertEquals(mood.getFeeling(),"happy");
    }

    @Test
    public void testSetFeeling(){
        Mood mood=new Mood("happy","xin","mes",0,0,null,null,null,null);
        mood.setFeeling("sad");
        assertEquals(mood.getFeeling(),"sad");
    }

    @Test
    public void testEncodeImage() throws Exception {

        Bitmap mPicture = null;
        mPicture = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
        if (mPicture == null) {
            assertNull(mPicture);
        } else {
            Mood moodEncode = new Mood("anger", "testing", "", 0.0, 0.0, mPicture, "", new Date(), "");
            String string = moodEncode.encodeImage(mPicture);
            assertNotNull(string);
        }
    }



}

