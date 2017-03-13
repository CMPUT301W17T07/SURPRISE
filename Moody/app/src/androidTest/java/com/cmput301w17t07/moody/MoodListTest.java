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

/**
 * Created by Panchy on 2017/2/24.
 */


//public class MoodListTest extends ActivityInstrumentationTestCase2 {
//    public MoodListTest() {
//        super(MoodList.class);
//    }
//
//    @Test
//    public void testAddMood() {
//        MoodList moodList=new MoodList();
//        Mood mood=new Mood("happy","xin");
//        moodList.addMood(mood);
//        assertTrue(moodList.hasMood(mood));
//        // testing to see if adding the same mood object will result in an error
//        try{
//            moodList.addMood(mood);
//            assertTrue(false);
//        } catch (IllegalArgumentException e){
//            assertTrue(true);
//        }
//    }
//
//    @Test
//    public void testHasMood(){
//        // TDD: Test > Fail > Code > Pass (LOOP)
//        MoodList moodList = new MoodList();
//        Mood mood = new Mood("test mood","xin");
//        // first checking that list doesn't have tweet before its addition
//        assertFalse(moodList.hasMood(mood));
//        moodList.addMood(mood);
//        assertTrue(moodList.hasMood(mood));
//    }
//
//    @Test
//    public void testDeleteMood(){
//        MoodList moodList=new MoodList();
//        Mood mood=new Mood("happy","xin");
//        moodList.addMood(mood);
//
//        moodList.deleteMood(mood);
//        assertEquals(moodList.countMoodList(),0);
//
//    }
//
//    @Test
//    public void testCountMoodList(){
//        MoodList moodList=new MoodList();
//        Mood mood=new Mood("happy","xin");
//        assertEquals(moodList.countMoodList(),0);
//        moodList.addMood(mood);
//        assertEquals(moodList.countMoodList(),1);
//    }
//
//    @Test
//    public void testGetMood(){
//        MoodList moodList=new MoodList();
//        Mood mood=new Mood("happy","xin");
//        moodList.addMood(mood);
//
//        assertEquals(mood,moodList.getMood(0));
//    }
//}

