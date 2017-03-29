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
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *  The ProfileActivity handles the user interface logic for when a user is viewing their
 *  mood history.
 */


public class ProfileActivity extends BarMenuActivity {

    private ListView moodTimeline;
    private MoodAdapter adapter;
    private ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    String username;

    private ArrayList<Mood> templist = new ArrayList<Mood>();
    private boolean scrollFlag;
    int indexOfScroll=0;
    int lastItem;

//    final Context currentContext = context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpMenuBar(this);

        //----------------------------- INITIALIZE MANAGER -----------------------------------------

        MoodManager.initManager(ProfileActivity.this);

        //----------------------------- GETTING LOCAL USERNAME -------------------------------------
        UserController userController = new UserController();
        username = userController.readUsername(ProfileActivity.this).toString();

        //--------------------- SETTING UP PROFILE INFO AT TOP OF SCREEN----------------------------
//        FollowController followController = new FollowController();
//
//
//        TextView userName = (TextView) findViewById(R.id.UserNameText);
//        userName.setText(username);
//        TextView Following = (TextView) findViewById(R.id.Following);
//        Following.setText("Following\n"+followController.getNumberOfFollowing(username));
//        TextView Followers = (TextView) findViewById(R.id.Followers);
//        Followers.setText("Followers\n"+followController.getNumberOfFollowers(username));

        //------------------------------ PENDING REQUEST STUFF -------------------------------------
//        Button PendingRequests = (Button) findViewById(R.id.PendingRequests);
//        PendingRequests.setText("PENDING REQUESTS ("+
//                followController.getNumberOfRequests(username) +")");
//        PendingRequests.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Toast.makeText(ProfileActivity.this, "Pending Requests", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(ProfileActivity.this, PendingRequestsActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        // todo displaying username on screen. REMOVE IN FINAL VERSION
        Toast.makeText(ProfileActivity.this, userController.readUsername(ProfileActivity.this).toString(), Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onStart(){
        super.onStart();

        // set scroll flag to true because the user has not tried loading older moods yet and thus
        // we cannot tell if they have all been loaded yet
        scrollFlag = true;

        final ListView moodTimelineListView = (ListView) findViewById(R.id.test_list);

        // Getting the user's moods
        moodArrayList = MoodController.getUserMoods(username, String.valueOf(indexOfScroll), ProfileActivity.this);
        // Save the moodlist locally
        MoodController.saveMoodList();


        // creating the adapter that will display the moods
        adapter = new MoodAdapter(this, R.layout.timeline_list, moodArrayList);
//        // setting the adapter to display the moods
        moodTimelineListView.setAdapter(adapter);

        // listener that checks if user has reached the bottom of the screen and then attempts to load
        // older moods
        moodTimelineListView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    // added a test to see if all moods have been loaded
                    if(scrollFlag) {
                        Toast.makeText(getApplicationContext(), "Starting loading new moody", Toast.LENGTH_SHORT).show();
                        indexOfScroll = indexOfScroll + 6;
                        // Getting the extra moods after the scroll
                        templist = MoodController.getUserMoods(username,
                                String.valueOf(indexOfScroll), ProfileActivity.this);
                        MoodController.saveMoodList();
                        // determining if there any old moods to find
                        if (templist.size() == 0) {
                            scrollFlag = false;
                        }
                        moodArrayList.addAll(templist);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                lastItem = firstVisibleItem + visibleItemCount - 1 ;
            }
        });

//         logic for when a user clicks on a mood item
        moodTimelineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Mood viewMood = moodArrayList.get(position);
                Intent viewMoodIntent = new Intent(ProfileActivity.this, ViewMoodActivity.class);
                viewMoodIntent.putExtra("viewMood", viewMood);

                startActivity(viewMoodIntent);
                finish();
            }


        });



    }


}


