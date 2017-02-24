package com.cmput301w17t07.moody;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Created by Panchy on 2017/2/24.
 */

public class UserTest extends ActivityInstrumentationTestCase2 {

    public UserTest() {
        super(User.class);

    }

    @Test
    public void testSetUsername(){
        User user=new User("panchy");
        user.setUsername("tom");
        assertEquals(user.getUsername(),"tom");
    }

    @Test
    public void testGetUsername(){
        User user=new User("panchy");
        assertEquals(user.getUsername(),"panchy");
    }

    @Test
    public void testGetMoodList(){
        MoodList moodList=new MoodList();
        User user=new User("panchy",moodList);

        assertEquals(user.getMoodList(),moodList);

    }

    @Test
    public void testSetMoodList(){
        MoodList moodList=new MoodList();
        User user=new User("panchy",moodList);

        MoodList moodList1=new MoodList();
        user.setMoodList(moodList1);

        assertEquals(user.getMoodList(),moodList1);
    }
}
