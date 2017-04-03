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

import android.support.v4.media.MediaMetadataCompat;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Created by mike on 2017-02-24.
 */

/**
 * Tests for the FollowingList class model.
 */

public class FollowingListTest extends ActivityInstrumentationTestCase2 {

    public FollowingListTest() {
        super(FollowingList.class);
    }


    @Test
    public void testAddFollowing() {
        FollowingList followingList = new FollowingList("testingfollowingmodel");
        User user = new User("user");
        User user1 = new User("user1");

        followingList.addFollowing("user");
        // check if user added to following list
        assertTrue(followingList.hasFollowing("user"));

        // attempting to add a user already in the following list
        try{
            followingList.addFollowing("user");
            assertTrue(false);
        } catch(IllegalArgumentException e){
            assertTrue(true);
        }


    }

    @Test
    public void testCountFollowing(){
        FollowingList followingList = new FollowingList("testingfollowingmodel");
        User user = new User("user");

        assertEquals(followingList.countFollowing(), 0);
        followingList.addFollowing("user");
        assertEquals(followingList.countFollowing(),1);
    }

    @Test
    public void testGetFollowing(){
        FollowingList followingList = new FollowingList("testingfollowingmodel");
        User user = new User("user");

        followingList.addFollowing("user");
        assertEquals(followingList.getFollowing(0),"user");
    }

    @Test
    public void testHasFollowing(){
        // TDD: Test > Fail > Code > Pass (LOOP)
        FollowingList followingList = new FollowingList("testingfollowingmodel");
        User user = new User("user");
        assertFalse(followingList.hasFollowing("user"));
        followingList.addFollowing("user");
        assertTrue(followingList.hasFollowing("user"));
    }

    @Test
    public void testDeleteFollowing(){
        FollowingList followingList = new FollowingList("testingfollowingmodel");
        User user = new User("user");

        followingList.addFollowing("user");
        assertEquals(followingList.countFollowing(),1);
        followingList.deleteFollowing("user");
        assertEquals(followingList.countFollowing(),0);
    }

}
