package com.cmput301w17t07.moody;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Created by mike on 2017-02-24.
 */

public class FollowingListTest extends ActivityInstrumentationTestCase2 {

    public FollowingListTest() {
        super(FollowingList.class);
    }


    @Test
    public void testAddFollowing() {
        FollowingList followingList = new FollowingList();
        User user = new User("user");
        User user1 = new User("user1");

        followingList.addPending(user);
        // check if user added to pending list
        assertTrue(followingList.hasPending(user));
        followingList.addFollowing(user);
        // check if user added to following list
        assertTrue(followingList.hasFollowing(user));
        // check if user removed from pending list
        assertFalse(followingList.hasPending(user));

        // attempting to add a user already in the following list
        try{
            followingList.addFollowing(user);
            assertTrue(false);
        } catch(IllegalArgumentException e){
            assertTrue(true);
        }
        // attempting to add a user not in the pending list
        try{
            followingList.addFollowing(user1);
            assertTrue(false);
        } catch(IllegalArgumentException e){
            assertTrue((true));
        }

    }

    
}
