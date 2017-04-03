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

import java.util.ArrayList;

/**
 * Created by mike on 2017-04-02.
 */

/**
 * Testing the sending and declining of follow requests
 */
public class SendFollowRequestUITest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public SendFollowRequestUITest() {
        super(com.cmput301w17t07.moody.SearchFilterOptionsActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());

    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testSendFollowRequest(){

        ElasticSearchFollowController.DeleteFollowerList deleteFollowerListAgain =
                new ElasticSearchFollowController.DeleteFollowerList();

        // deleting old followerlist
        deleteFollowerListAgain.execute("testrequest1");

        ElasticSearchFollowController.DeleteFollowingList deleteFollowingList =
                new ElasticSearchFollowController.DeleteFollowingList();
        // delete old following list
        deleteFollowingList.execute("testrequest1");

        // Creating a user to send a request to

        if(UserController.checkUsername("testrequest1")){
            //checking to see if test user needs to be created or not
            UserController.createUser("testrequest1");
        }




        // creating follower lists for the user
        if(FollowController.getFollowerList("testrequest1") == null) {
            FollowController.createFollowLists("testrequest1");
        }


        // checking if on the correct activity
        solo.assertCurrentActivity("Wrong Activity", SearchFilterOptionsActivity.class);

        // Getting current user's username
        UserController userController = new UserController();
        String username = userController.readUsername(getActivity()).toString();


        // typing in the username to send the request to
        solo.enterText((EditText) solo.getView(R.id.searchEditname), "testrequest1");
        // clicking on the button
        solo.clickOnButton(0);
        // seeing if the activity has properly switched
        solo.assertCurrentActivity("Wrong Activity", SearchUserActivity.class);
        // seeing if the user's name is displayed on the list
        solo.waitForText("testrequest1");
        // clicking on request button
        solo.clickOnButton("SEND REQUEST");
        // seeing if button responded
        solo.waitForText("REQUEST SENT");

        // Determining if user is in the testingrequest's pending list

        ArrayList<String> pendingRequests = FollowController.getPendingRequests("testrequest1");

        assertTrue(pendingRequests.contains(username));

        // declining the request

        FollowerList followerList = FollowController.getFollowerList("testrequest1");

        // removing the requester from the user's pending lust
        followerList.deletePending(username);

        // update the list

        ElasticSearchFollowController.DeleteFollowerList deleteFollowerList =
                new ElasticSearchFollowController.DeleteFollowerList();
        ElasticSearchFollowController.AddFollowerList addFollowerList =
                new ElasticSearchFollowController.AddFollowerList();

        // deleting old followerlist
        deleteFollowerList.execute("testrequest1");
        // adding updated one
        addFollowerList.execute(followerList);

        // checking the pending request and follower lists

        pendingRequests.clear();



        ArrayList<String> followers = FollowController.getFollowerList("testrequest1").getFollowerList();

        // confirm that the user is not in the follower list
        assertFalse(followers.contains(username));


        // Go back to search page
        solo.clickOnMenuItem("Search");


        //
        solo.assertCurrentActivity("Wrong Activity", SearchFilterOptionsActivity.class);

        solo.enterText((EditText) solo.getView(R.id.searchEditname), "testrequest1");
        // clicking on the button
        solo.clickOnButton(0);
        // seeing if the activity has properly switched
        solo.assertCurrentActivity("Wrong Activity", SearchUserActivity.class);
        // seeing if the user's name is displayed on the list
        solo.waitForText("testrequest1");

        // SEEING IF USER is no longer in pending section and can send request again
        solo.waitForText("SEND REQUEST");


    }

}
