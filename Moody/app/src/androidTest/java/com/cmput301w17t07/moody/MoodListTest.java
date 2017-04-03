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

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Panchy on 2017/2/24.
 */


public class MoodListTest extends ActivityInstrumentationTestCase2 {
    public MoodListTest() {
        super(MoodList.class);
    }

    @Test
    public void testAddMood(){
        MoodList moodList = new MoodList();
        Mood mood = new Mood("anger", "testing", "", 0.0, 0.0, null, "", new Date(), "");

        moodList.addMood(mood);

        // determine that offline added count is 1
        assertTrue(moodList.addedOffline.size() == 1);
        // determine that moodlist size count is 1
        assertTrue(moodList.countMoodList() == 1);
    }

    @Test
    public void testdeleteMood(){
        MoodList moodList = new MoodList();
        Mood mood = new Mood("anger", "testing", "", 0.0, 0.0, null, "", new Date(), "");
        Mood mood1 = new Mood("anger", "testing", "", 0.0, 0.0, null, "", new Date(), "");

        mood.setId(UUID.randomUUID().toString());
        mood1.setId(UUID.randomUUID().toString());


        moodList.addMood(mood);
        moodList.addMood(mood1);

        // determine that offline added count is 2
        assertTrue(moodList.addedOffline.size() == 2);
        // determine that moodlist size count is 2
        assertTrue(moodList.countMoodList() == 2);

        moodList.deleteMood(mood);
        // check the moodlist size
        assertTrue(moodList.countMoodList() == 1);
        // check the deleted offline moodlist size
        assertTrue(moodList.deletedOffline.size() == 1);
        // check that it contains the proper mood
        assertTrue(moodList.deletedOffline.contains(mood));
        // make sure moodlist deleted the proper entry
        assertTrue(moodList.getMoods().contains(mood1));


    }

    @Test
    public void testGetMood(){
        MoodList moodList=new MoodList();
        Mood mood = new Mood("anger", "testing", "", 0.0, 0.0, null, "", new Date(), "");
        moodList.addMood(mood);

        assertEquals(mood,moodList.getMood(0));
    }

    @Test
    public void testCountMoodList(){
        MoodList moodList=new MoodList();
        Mood mood = new Mood("anger", "testing", "", 0.0, 0.0, null, "", new Date(), "");
        assertEquals(moodList.countMoodList(),0);
        moodList.addMood(mood);
        assertEquals(moodList.countMoodList(),1);
    }

    @Test
    public void testEditMood(){

        MoodList moodList = new MoodList();
        Mood mood = new Mood("anger", "testing", "", 0.0, 0.0, null, "", new Date(), "");

        moodList.addMood(mood);

        // determine that offline added count is 1
        assertTrue(moodList.addedOffline.size() == 1);
        // determine that moodlist size count is 1
        assertTrue(moodList.countMoodList() == 1);


        Mood editMood = new Mood("anger", "edit", "", 0.0, 0.0, null, "", new Date(), "");

        mood.setId(UUID.randomUUID().toString());
        editMood.setId(mood.getId());


        moodList.editMood(editMood, mood);


        // Ensure that the added offline list was incremented
        assertTrue(moodList.addedOffline.size() == 1);
        // ensure that moodlist contains the edited mood
        assertTrue(moodList.getMood(0).getMoodMessage().equals("edit"));

    }
}

