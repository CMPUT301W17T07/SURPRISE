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

        //----------------------------- GETTING LOCAL USERNAME -------------------------------------
        UserController userController = new UserController();
        username = userController.readUsername(ProfileActivity.this).toString();

        //--------------------- SETTING UP PROFILE INFO AT TOP OF SCREEN----------------------------
        FollowController followController = new FollowController();


        TextView userName = (TextView) findViewById(R.id.UserNameText);
        userName.setText(username);
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
                Toast.makeText(ProfileActivity.this, "Pending Requests", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this, PendingRequestsActivity.class);
                startActivity(intent);
                finish();
            }
        });

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


        adapter = new MoodAdapter(this, R.layout.timeline_list, moodArrayList);
//        Toast.makeText(ProfileActivity.this, moodArrayList.get(1).getFeeling(), Toast.LENGTH_SHORT).show();

        moodTimelineListView.setAdapter(adapter);

        moodTimelineListView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    // added a test to see if all moods have been loaded
                    if(scrollFlag) {
                        Toast.makeText(getApplicationContext(), "Starting load new moody", Toast.LENGTH_SHORT).show();
                        indexOfScroll = indexOfScroll + 6;
                        ElasticMoodController.GetUserMoods getUserMoodsAgain = new ElasticMoodController.GetUserMoods();
                        getUserMoodsAgain.execute(username, String.valueOf(indexOfScroll));
                        try {
                            templist = getUserMoodsAgain.get();
                            if (templist.size() == 0) {
                                scrollFlag = false;
                            }

                        } catch (Exception e) {
                            Log.i("error", "failed to get the mood out of the async matched");
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
//                try{
//                     Mood viewMood = moodArrayList.get(position);
//                     String ID = viewMood.getId();
//                     Location location = null;
//                     Mood send = new Mood(viewMood.getFeeling(),
//                             viewMood.getUsername(),
//                             viewMood.getMoodMessage(),
//                             location,
//                             viewMood.getMoodImageID(),
//                             viewMood.getSocialSituation());
//                    send.setId(viewMood.getId());
//                    String hasLocation = "0";
//
//                    System.out.println("location = " + viewMood.toString());
//                    Intent viewMoodIntent = new Intent(ProfileActivity.this, ViewMoodActivity.class);
//                    viewMoodIntent.setAction("action");
//                    viewMoodIntent.putExtra("viewMood", send);
//                   // viewMoodIntent.putExtra("ID",ID);
//                    if(viewMood.getLocation()!=null){
//                        DecimalFormat decimalFormat=new DecimalFormat(".##");
//                        String latitude = decimalFormat.format(viewMood.getLocation().getLatitude());
//                        String longitude = decimalFormat.format(viewMood.getLocation().getLongitude());
//                        String passLocation = "Latitude:" + latitude +",Londitude:" + longitude;
//                        System.out.println("passlocation = "+passLocation);
//                        Location sendLocation = viewMood.getLocation();
//                        viewMoodIntent.putExtra("sendLatitude",sendLocation.getLatitude());
//                        viewMoodIntent.putExtra("sendLonditude",sendLocation.getLongitude());
//                        System.out.println("lat = " + sendLocation);
//                        hasLocation = "1";
//                        viewMoodIntent.putExtra("location",passLocation);}
//                    else{
//                        String passLocation = "";
//                        viewMoodIntent.putExtra("location",passLocation);}
//                    viewMoodIntent.putExtra("haslocation",hasLocation);
//                    String trigger = "profile";
//                    viewMoodIntent.putExtra("trigger",trigger);
//                    startActivity(viewMoodIntent);
//                    finish();
//                }catch(Exception e){}
                Mood viewMood = moodArrayList.get(position);
                Intent viewMoodIntent = new Intent(ProfileActivity.this, ViewMoodActivity.class);
                viewMoodIntent.putExtra("viewMood", viewMood);

                startActivity(viewMoodIntent);
                finish();
            }


        });



    }


}


