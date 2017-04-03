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

import java.util.Date;

/**
 * Created by xin on 2017/4/2.
 */

public class TimeLineActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public TimeLineActivityTest() {
        super(com.cmput301w17t07.moody.TimelineActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void testAddMood() {

        solo.assertCurrentActivity("Wrong Activity", TimelineActivity.class);


        UserController userController = new UserController();
        String username = userController.readUsername(getActivity()).toString();

        if (UserController.checkUsername("timelineuser")) {
            //checking to see if test user needs to be created or not
            UserController.createUser("timelineuser");
            // Creating the follow lists
            FollowController.createFollowLists("timelineuser");
        }

        solo.clickOnMenuItem("Search");
        solo.enterText((EditText) solo.getView(R.id.searchEditname), "timelineuser");
        solo.clickOnButton(0);
        solo.clickOnButton("SEND REQUEST");
        solo.waitForText("REQUEST SENT");

        FollowController.sendPendingRequest(username, "timelineuser", getActivity().getApplicationContext());

        Mood newMood = new Mood("surprise", "timelineuser", "timeline",
                0.0, 0.0, null, "alone", new Date(), "");

        ElasticMoodController.AddMood addMood = new ElasticMoodController.AddMood();
        addMood.execute(newMood);

        FollowController.acceptFollowRequest("timelineuser", username, getActivity().getApplicationContext());


        //go to pending requests page
        solo.clickOnMenuItem("Home");
        solo.clickInList(0);
        //check to see the current activity is correct
        solo.waitForText("timelineuser");
        solo.waitForText("timeline");

        // Check the user's follower list to determine appropriate action was taken

        FollowingList followingList = FollowController.getFollowingList(username);

        assertTrue(followingList.hasFollowing("timelineuser"));


        followingList.deleteFollowing("timelineuser");


        //UPDATING THE SERVER

        ElasticSearchFollowController.DeleteFollowingList deleteFollowingList =
                new ElasticSearchFollowController.DeleteFollowingList();
        ElasticSearchFollowController.AddFollowingList addFollowingList =
                new ElasticSearchFollowController.AddFollowingList();

        // deleting old followerlist
        deleteFollowingList.execute(username);
        // adding updated one
        addFollowingList.execute(followingList);


        // Updating the test user's follower list

        FollowerList followerList = FollowController.getFollowerList("timelineuser");

        followerList.deleteFollower(username);

        ElasticSearchFollowController.DeleteFollowerList deleteFollowerList =
                new ElasticSearchFollowController.DeleteFollowerList();
        ElasticSearchFollowController.AddFollowerList addFollowerList =
                new ElasticSearchFollowController.AddFollowerList();

        deleteFollowerList.execute("timelineuser");
        addFollowerList.execute(followerList);

        ElasticMoodController.DeleteMood deleteMood = new ElasticMoodController.DeleteMood();
        deleteMood.execute(newMood.getId());


    }
}
