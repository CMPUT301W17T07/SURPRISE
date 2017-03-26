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

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PendingRequestsActivity extends BarMenuActivity {

    private String username;
//    private FollowerList followerList = null;
    private ArrayList<String> userArrayList=new ArrayList<>();
    private ListView requestList;
    private PendingRequestsAdapter pendingRequestsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);
        setUpMenuBar(this);
        TextView PendingRequestsText = (TextView) findViewById(R.id.PendingRequestsText);
        PendingRequestsText.setText("Pending Requests");



    }

    @Override
    protected void onStart() {
        super.onStart();

        //todo technically we should implement infinite scroll here too, right...?


        // loading the username of the user
        UserController userController = new UserController();
        username = userController.readUsername(PendingRequestsActivity.this).toString();

        // UI listview in pending_requests_activity that will display the list
        requestList = (ListView) findViewById(R.id.pendingList);

        // getting the list of pending requests
        FollowController followController = new FollowController();
        userArrayList = followController.getPendingRequests(username);

        // setting the adapter
        pendingRequestsAdapter = new PendingRequestsAdapter(this, userArrayList, username);
        requestList.setAdapter(pendingRequestsAdapter);


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PendingRequestsActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
