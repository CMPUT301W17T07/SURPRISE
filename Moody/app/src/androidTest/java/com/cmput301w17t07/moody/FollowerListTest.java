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

/**
 * Created by mike on 2017-02-24.
 */

/**
 * Tests for the FollowerList class model
 */

public class FollowerListTest extends ActivityInstrumentationTestCase2{
    public FollowerListTest() {
        super(FollowerList.class);
    }

    @Test
    public void testAddFollower() {

        FollowerList followerList = new FollowerList("testingfollowermodel");
        User user = new User("user");
        User user1 = new User("user1");

        followerList.addPending("user");
        // check if user added to pending list
        assertTrue(followerList.hasPending("user"));
        followerList.addFollower("user");
        // check if user added to following list
        assertTrue(followerList.hasFollower("user"));
        // check if user removed from pending list
        assertFalse(followerList.hasPending("user"));

    }

    @Test
    public void testCountFollowers(){
        FollowerList followerList = new FollowerList("testingfollowermodel");
        User user = new User("user");

        assertEquals(followerList.countFollowers(), 0);
        followerList.addPending("user");
        followerList.addFollower("user");
        assertEquals(followerList.countFollowers(),1);
    }


    @Test
    public void testGetFollower(){
        FollowerList followerList = new FollowerList("testingfollowermodel");
        User user = new User("user");

        followerList.addPending("user");
        followerList.addFollower("user");
        assertEquals(followerList.getFollower(0),"user");
    }

    @Test
    public void testHasFollower(){
        // TDD: Test > Fail > Code > Pass (LOOP)
        FollowerList followerList = new FollowerList("testingfollowermodel");
        User user = new User("user");
        assertFalse(followerList.hasFollower("user"));
        followerList.addPending("user");
        followerList.addFollower("user");
        assertTrue(followerList.hasFollower("user"));
    }

    @Test
    public void testDeleteFollower(){
        FollowerList followerList = new FollowerList("testingfollowermodel");
        User user = new User("user");

        followerList.addPending("user");
        followerList.addFollower("user");
        assertEquals(followerList.countFollowers(),1);
        followerList.deleteFollower("user");
        assertEquals(followerList.countFollowers(),0);
    }

    @Test
    public void testCountPending(){
        FollowerList followerList = new FollowerList("testingfollowermodel");
        User user = new User("user");

        assertEquals(followerList.countPending(), 0);
        followerList.addPending("user");
        assertEquals(followerList.countPending(), 1);

    }

}
