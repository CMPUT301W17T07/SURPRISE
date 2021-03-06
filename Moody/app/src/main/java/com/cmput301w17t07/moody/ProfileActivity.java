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
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    /**
     * Handles the setting up of the follower/follow information & the UI interface for the profile
     * page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpMenuBar(this);


        //----------------------------- INITIALIZE MANAGER -----------------------------------------

        MoodManager.initManager(this.getApplicationContext());

        //----------------------------- GETTING LOCAL USERNAME -------------------------------------
        UserController userController = new UserController();
        username = userController.readUsername(ProfileActivity.this).toString();

        //--------------------- SETTING UP PROFILE INFO AT TOP OF SCREEN----------------------------
        FollowController followController = new FollowController();


        final TextView userName = (TextView) findViewById(R.id.UserNameText);
        userName.setText(username);

        userName.setTextColor(getResources().getColor(R.color.redTheme));
        userName.setTypeface(null, Typeface.BOLD_ITALIC);

        TextView Following = (TextView) findViewById(R.id.Following);
        Following.setText("Following\n"+followController.getNumberOfFollowing(username, ProfileActivity.this));
        TextView Followers = (TextView) findViewById(R.id.Followers);
        Followers.setText("Followers\n"+followController.getNumberOfFollowers(username, ProfileActivity.this));

        //------------------------------ PENDING REQUEST STUFF -------------------------------------
        Button PendingRequests = (Button) findViewById(R.id.PendingRequests);
        PendingRequests.setText("PENDING REQUESTS ("+
                followController.getNumberOfRequests(username, ProfileActivity.this) +")");
        PendingRequests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(MoodController.checkNetwork(ProfileActivity.this)) {
                    Toast.makeText(ProfileActivity.this, "Pending Requests", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, PendingRequestsActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(ProfileActivity.this, "CONNECT TO THE GOD DAMN NETWORK, MAN", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        // todo displaying username on screen. REMOVE IN FINAL VERSION
        Toast.makeText(ProfileActivity.this, userController.readUsername(ProfileActivity.this).toString(), Toast.LENGTH_SHORT).show();


        // ############################ achievements ###################################

        ImageButton achievements = (ImageButton) findViewById(R.id.achievementButton);
        achievements.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Show me the money", Toast.LENGTH_SHORT).show();
                Intent showMeTheMoney = new Intent(ProfileActivity.this, AchievementActivity.class);
                startActivity(showMeTheMoney);
                finish();

            }
        });



    }

    /**
     * Handles the populating of the profile listview with the user's moods
     */
    @Override
    protected void onStart(){
        super.onStart();

        // set scroll flag to true because the user has not tried loading older moods yet and thus
        // we cannot tell if they have all been loaded yet
        scrollFlag = true;
        indexOfScroll = 0;
        final ListView moodTimelineListView = (ListView) findViewById(R.id.test_list);

        // Getting the user's moods
        try {
            moodArrayList = MoodController.getUserMoods(username,
                    String.valueOf(indexOfScroll),
                    ProfileActivity.this, true, String.valueOf(8));
            // Save the moodlist locally
            MoodController.saveMoodList();
        } catch (Exception E){
            System.out.println("this is an error in the Profile Activity "+E);
        }

        adapter = new MoodAdapter(this, R.layout.timeline_list, moodArrayList);

        moodTimelineListView.setAdapter(adapter);

        moodTimelineListView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){
                // when not scroll
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // when it scroll to bottom
                    // added a test to see if all moods have been loaded
                    if(scrollFlag) {
                        Toast.makeText(getApplicationContext(), "Starting loading new moody", Toast.LENGTH_SHORT).show();
                        indexOfScroll = indexOfScroll + 6;
                        // Getting the extra moods after the scroll
                        try {
                            templist = MoodController.getUserMoods(username,
                                    String.valueOf(indexOfScroll), ProfileActivity.this,
                                    true, String.valueOf(8));
                            MoodController.saveMoodList();
                        } catch(Exception e){
                            System.out.println("this is an error in the Profile Activity when loading extra moods"+e);
                        }
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


        moodTimelineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Mood viewMood = moodArrayList.get(position);
                Intent viewMoodIntent = new Intent(ProfileActivity.this, ViewMoodActivity.class);
                viewMoodIntent.putExtra("viewMood", viewMood);
                String trigger = "profile";
                viewMoodIntent.putExtra("trigger",trigger);
                startActivity(viewMoodIntent);
                finish();
            }


        });

    }

}


