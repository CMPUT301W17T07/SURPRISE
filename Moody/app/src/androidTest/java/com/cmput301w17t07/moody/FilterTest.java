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
import android.widget.ImageView;

import com.robotium.solo.Solo;

import java.util.Date;

/**
 * Created by xin on 2017/4/2.
 */

public class FilterTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public FilterTest() {
        super(com.cmput301w17t07.moody.CreateMoodActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void testAddMood() {

        solo.assertCurrentActivity("Wrong Activity", CreateMoodActivity.class);
        solo.pressSpinnerItem(0, 0);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(0, "anger"));
        solo.pressSpinnerItem(0, 1);
        assertTrue("mood spinner test confusion", solo.isSpinnerTextSelected(0, "confusion"));
        solo.pressSpinnerItem(1, 1);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(1, "alone"));
        solo.pressSpinnerItem(1, 2);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(1, "with two people"));

        solo.enterText((EditText) solo.getView(R.id.Description), "filter");

        ImageView photo = (ImageView) solo.getView(R.id.editImageView);

        solo.clickOnButton("Send");
        solo.clickOnMenuItem("Search");
        solo.clickOnButton(1);
        solo.pressSpinnerItem(1, 1);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(1, "confusion"));

        solo.clickOnButton(0);
        solo.clickInList(0);
        solo.waitForText("filter");
        solo.waitForText("with two people");

        solo.clickOnMenuItem("Search");
        solo.clickOnButton(1);
        solo.pressSpinnerItem(2, 1);

        solo.clickOnButton(1);
        solo.clickInList(0);
        solo.waitForText("filter");
        solo.waitForText("with two people");

        solo.clickOnMenuItem("Search");
        solo.clickOnButton(1);
        solo.enterText((EditText) solo.getView(R.id.filterMessageText), "filter");
        solo.clickOnButton(2);
        solo.clickInList(0);
        solo.waitForText("filter");
        solo.waitForText("with two people");



    }
    public void testTimeLine() {
        solo.assertCurrentActivity("Wrong Activity", CreateMoodActivity.class);
        solo.clickOnMenuItem("Home");
        UserController userController = new UserController();
        String username = userController.readUsername(getActivity()).toString();

        if (UserController.checkUsername("testfilter")) {
            //checking to see if test user needs to be created or not
            UserController.createUser("testfilter");
            // Creating the follow lists
            FollowController.createFollowLists("testfilter");
        }
        //sending requests
        FollowController.sendPendingRequest(username, "testfilter", getActivity().getApplicationContext());

        Mood newMood = new Mood("anger", "testfilter", "timeline",
                0.0, 0.0, null, "alone", new Date(), "");

        ElasticMoodController.AddMood addMood = new ElasticMoodController.AddMood();
        addMood.execute(newMood);

        FollowController.acceptFollowRequest("testfilter", username, getActivity().getApplicationContext());


        //go to pending requests page
        solo.clickOnMenuItem("Search");
        solo.clickOnButton(1);
        solo.pressSpinnerItem(0, 1);
        solo.pressSpinnerItem(1, 0);
        solo.clickOnButton(1);
        solo.clickInList(0);

        //check to see the current activity is correct
        solo.waitForText("testfilter");
        solo.waitForText("timeline");

        // Check the user's follower list to determine appropriate action was taken

        FollowingList followingList = FollowController.getFollowingList(username);

        assertTrue(followingList.hasFollowing("testfilter"));


        followingList.deleteFollowing("testfilter");

        //UPDATING THE SERVER

        ElasticSearchFollowController.DeleteFollowingList deleteFollowingList =
                new ElasticSearchFollowController.DeleteFollowingList();
        ElasticSearchFollowController.AddFollowingList addFollowingList =
                new ElasticSearchFollowController.AddFollowingList();

        // deleting old followerlist
        deleteFollowingList.execute(username);
        // adding updated one
        addFollowingList.execute(followingList);
    }

}
