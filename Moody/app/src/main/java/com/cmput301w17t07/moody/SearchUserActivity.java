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
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

/**
 *  The SearchUserActivity handles the user interface logic for when a user is viewing
 *  the users they searched for.
 */

public class SearchUserActivity extends BarMenuActivity {

    private ArrayList<User> userArrayList=new ArrayList<>();
    private ListView oldUserList;
    private UserAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        setUpMenuBar(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // loading the username of the user
        UserController userController = new UserController();
        final String username = userController.readUsername(SearchUserActivity.this).toString();

        ElasticSearchUserController.GetUser getUser = new ElasticSearchUserController.GetUser();
        oldUserList = (ListView) findViewById(R.id.listSearch);
        Intent intent = getIntent();
        // username of searched user
        final String searchUsername = intent.getStringExtra("editUsername");
        getUser.execute(searchUsername);

        try {

            userArrayList = getUser.get();

        } catch (Exception e) {
            Log.i("error", "failed to get the User out of the async matched");
        }
        userAdapter = new UserAdapter(this, userArrayList, username, searchUsername);
        oldUserList.setAdapter(userAdapter);
    }


 }
