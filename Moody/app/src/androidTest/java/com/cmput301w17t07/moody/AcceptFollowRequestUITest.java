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

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by mike on 2017-04-02.
 */

public class AcceptFollowRequestUITest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public AcceptFollowRequestUITest() {
        super(com.cmput301w17t07.moody.ProfileActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());

    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testAcceptingRequest(){

        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);

        // Getting current user's username
        UserController userController = new UserController();
        String username = userController.readUsername(getActivity()).toString();

        if(UserController.checkUsername("testadd1")){
            //checking to see if test user needs to be created or not
            UserController.createUser("testadd1");
            // Creating the follow lists
            FollowController.createFollowLists("testadd1");
        }


        //sending requests
        FollowController.sendPendingRequest("testadd1", username, getActivity().getApplicationContext());

        //go to pending requests page
        solo.clickOnButton("PENDING REQUESTS");

        //check to see the current activity is correct
        solo.assertCurrentActivity("Wrong Activity", PendingRequestsActivity.class);


        solo.waitForText("testadd1");

        // accept first user
        solo.clickOnButton("ACCEPT");
        solo.waitForText("ACCEPTED");

        // Check the user's follower list to determine appropriate action was taken

        FollowerList followerList = FollowController.getFollowerList(username);

        assertTrue(followerList.hasFollower("testadd1"));

        assertFalse(followerList.hasPending("testadd1"));

        followerList.deleteFollower("testadd1");

        //UPDATING THE SERVER

        ElasticSearchFollowController.DeleteFollowerList deleteFollowerList =
                new ElasticSearchFollowController.DeleteFollowerList();
        ElasticSearchFollowController.AddFollowerList addFollowerList =
                new ElasticSearchFollowController.AddFollowerList();

        // deleting old followerlist
        deleteFollowerList.execute(username);
        // adding updated one
        addFollowerList.execute(followerList);

    }

    public void testDeclineRequest(){

        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);

        // Getting current user's username
        UserController userController = new UserController();
        String username = userController.readUsername(getActivity()).toString();


        if(UserController.checkUsername("testdecline")){
            //checking to see if test user needs to be created or not
            UserController.createUser("testdecline");
            FollowController.createFollowLists("testdecline");
        }
        // Creating the follow lists

        //sending requests
        FollowController.sendPendingRequest("testdecline", username, getActivity().getApplicationContext());

        //go to pending requests page
        solo.clickOnButton("PENDING REQUESTS");

        //check to see the current activity is correct
        solo.assertCurrentActivity("Wrong Activity", PendingRequestsActivity.class);


        solo.waitForText("testdecline");

        // decline user
        solo.clickOnButton("DECLINE");
        solo.waitForText("DECLINED");

        // Check the user's follower list to determine appropriate action was taken

        FollowerList followerList = FollowController.getFollowerList(username);

        assertFalse(followerList.hasFollower("testdecline"));

        assertFalse(followerList.hasPending("testdecline"));

    }

}
