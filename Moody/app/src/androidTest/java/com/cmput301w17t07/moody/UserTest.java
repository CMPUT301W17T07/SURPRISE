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

import android.media.Image;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

import org.junit.Test;


/**
 * Created by Panchy on 2017/2/24.
 */

public class UserTest extends ActivityInstrumentationTestCase2 {

    public UserTest() {
        super(User.class);

    }


    @Test
    public void testSetId(){
        User user=new User("panchy");
        user.setId("1");
        user.setId("2");
        assertEquals(user.getId(),"2");
    }

    @Test
    public void testGetId(){
        User user=new User("panchy");
        user.setId("1");
        assertEquals(user.getId(),"1");
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
    public void testsetProfilePicture(){

        User user=new User("panchy");
        Image image = null;

        user.setProfilePicture(image);
        assertEquals(user.getProfilePicture(),null);
    }

    public void testgetProfilePicture(){
        User user=new User("panchy");
        assertEquals(user.getProfilePicture(),null);
    }
}
