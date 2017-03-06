package com.cmput301w17t07.moody;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Created by Panchy on 2017/2/24.
 */

public class MoodListTest extends ActivityInstrumentationTestCase2 {
    public MoodListTest() {
        super(MoodList.class);
    }
//
//    @Test
//    public void testAddMood() {
//        MoodList moodList=new MoodList();
//        Mood mood=new Mood("happy");
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
//        Mood mood = new Mood("test mood");
//        // first checking that list doesn't have tweet before its addition
//        assertFalse(moodList.hasMood(mood));
//        moodList.addMood(mood);
//        assertTrue(moodList.hasMood(mood));
//    }
//
//    @Test
//    public void testDeleteMood(){
//        MoodList moodList=new MoodList();
//        Mood mood=new Mood("happy");
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
//        Mood mood=new Mood("happy");
//        assertEquals(moodList.countMoodList(),0);
//        moodList.addMood(mood);
//        assertEquals(moodList.countMoodList(),1);
//    }
//
//    @Test
//    public void testGetMood(){
//        MoodList moodList=new MoodList();
//        Mood mood=new Mood("happy");
//        moodList.addMood(mood);
//
//        assertEquals(mood,moodList.getMood(0));
//    }
}
