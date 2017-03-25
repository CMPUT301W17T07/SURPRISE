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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 *  The SearchUserActivty handles the user interface logic for when a user is viewing
 *  the users they searched for. Functionality of this activity will be increased
 *  when following functionality is added in part 5.
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
        System.out.printf("this is xin in after " + searchUsername);
        getUser.execute(searchUsername);

        try {

            userArrayList = getUser.get();
            //System.out.println("this is userlist " + userArrayList.get(0).getUsername());

        } catch (Exception e) {
            Log.i("error", "failed to get the User out of the async matched");
        }
        userAdapter = new UserAdapter(this, userArrayList, username, searchUsername);
        oldUserList.setAdapter(userAdapter);
        //System.out.println("this is error"+e);



    }


 }
