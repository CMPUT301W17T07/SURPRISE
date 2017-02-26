package com.cmput301w17t07.moody;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Created by mike on 2017-02-24.
 */

public class FollowerListTest extends ActivityInstrumentationTestCase2{
    public FollowerListTest() {
        super(FollowerList.class);
    }

    @Test
    public void testAddFollower() {

        FollowerList followerList = new FollowerList();
        User user = new User("user");
        User user1 = new User("user1");

        followerList.addPending(user);
        // check if user added to pending list
        assertTrue(followerList.hasPending(user));
        followerList.addFollower(user);
        // check if user added to following list
        assertTrue(followerList.hasFollower(user));
        // check if user removed from pending list
        assertFalse(followerList.hasPending(user));

        // attempting to add a user already in the following list
        try{
            followerList.addFollower(user);
            assertTrue(false);
        } catch(IllegalArgumentException e){
            assertTrue(true);
        }
        // attempting to add a user not in the pending list
        try{
            followerList.addFollower(user1);
            assertTrue(false);
        } catch(IllegalArgumentException e){
            assertTrue((true));
        }
    }

    @Test
    public void testCountFollowers(){
        FollowerList followerList = new FollowerList();
        User user = new User("user");

        assertEquals(followerList.countFollowers(), 0);
        followerList.addPending(user);
        followerList.addFollower(user);
        assertEquals(followerList.countFollowers(),1);
    }


    @Test
    public void testGetFollower(){
        FollowerList followerList = new FollowerList();
        User user = new User("user");

        followerList.addPending(user);
        followerList.addFollower(user);
        assertEquals(followerList.getFollower(0),user);
    }

    @Test
    public void testHasFollower(){
        // TDD: Test > Fail > Code > Pass (LOOP)
        FollowerList followerList = new FollowerList();
        User user = new User("user");
        assertFalse(followerList.hasFollower(user));
        followerList.addPending(user);
        followerList.addFollower(user);
        assertTrue(followerList.hasFollower(user));
    }

    @Test
    public void testDeleteFollower(){
        FollowerList followerList = new FollowerList();
        User user = new User("user");

        followerList.addPending(user);
        followerList.addFollower(user);
        assertEquals(followerList.countFollowers(),1);
        followerList.deleteFollower(user);
        assertEquals(followerList.countFollowers(),0);
    }

    @Test
    public void testCountPending(){
        FollowerList followerList = new FollowerList();
        User user = new User("user");

        assertEquals(followerList.countPending(), 0);
        followerList.addPending(user);
        assertEquals(followerList.countPending(), 1);

    }

}