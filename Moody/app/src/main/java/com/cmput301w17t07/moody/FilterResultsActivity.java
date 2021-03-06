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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * FilterResultsActivity class. User interface logic for the view that allows the user
 * to view the filtered moods they requested.
 */

public class FilterResultsActivity extends BarMenuActivity {

    String username;
    private ListView moodTimeline;
    private MoodAdapter adapter;
    private ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private Integer selectedFilter;
    private String filterFeeling;
    private String filterMessage;

    private String selectedUser; // equals My Moods/Timeline Moods
    private Integer user; // user = 0 if My Moods else user = 1 if Timeline Moods
    ArrayList nameList=new ArrayList();
    private ArrayList<Mood> sortArrayList2 = new ArrayList<Mood>();

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_results);

        setUpMenuBar(this);
        UserController userController = new UserController();
        username = userController.readUsername(FilterResultsActivity.this).toString();

        intent = getIntent();
        selectedFilter = intent.getIntExtra("selectedFilter", 0);
        selectedUser = intent.getStringExtra("selectedUser");
        if (selectedUser.equals("My Moods")) {
            user = 0;
        }
        else {
            user = 1;
        }

    }



    @Override
    protected void onStart(){
        super.onStart();
        moodTimeline = (ListView) findViewById(R.id.filterResultsList);

        if (user == 0){
            // if equals 0 filter by feeling
            if(selectedFilter == 0) {
                filterFeeling = intent.getStringExtra("feelingFilter");
                ElasticMoodController.GetFeelingFilterMoods getFeelingFilterMoods =
                        new ElasticMoodController.GetFeelingFilterMoods();
                getFeelingFilterMoods.execute(username, filterFeeling);

                try {
                    moodArrayList = getFeelingFilterMoods.get();
                } catch (Exception e) {
                    Log.i("error", "failed to get the mood out of the async matched");
                }
            }
            // if equals 1 filter by date
            if(selectedFilter == 1) {
                ElasticMoodController.GetRecentWeekUserMoods getRecentWeekUserMoods =
                        new ElasticMoodController.GetRecentWeekUserMoods();
                getRecentWeekUserMoods.execute(username);
                try {
                    moodArrayList = getRecentWeekUserMoods.get();
                } catch (Exception e) {
                    Log.i("error", "failed to get the recent week moods out of the async matched");
                }
            }
            // if equals 2 filter by mood message
            if(selectedFilter == 2) {
                filterMessage = intent.getStringExtra("messageFilter");
                ElasticMoodController.GetMessageFilterMoods getMessageFilterMoods =
                        new ElasticMoodController.GetMessageFilterMoods();
                getMessageFilterMoods.execute(username, filterMessage.toLowerCase());

                try {
                    moodArrayList = getMessageFilterMoods.get();
                } catch (Exception e) {
                    Log.i("error", "failed to get the mood out of the async matched");
                }
            }
        }
        //------------------------------ TIMELINE FILTERING ----------------------------------------
        else {
            // do timeline stuff ...
            UserController userController = new UserController();
            username = userController.readUsername(FilterResultsActivity.this).toString();

            FollowController followController = new FollowController();
            FollowingList followingList = followController.getFollowingList(username);

            nameList.addAll(followingList.getFollowingList());
            if (selectedFilter == 0) {
                filterFeeling = intent.getStringExtra("feelingFilter");
                try {
                    for (int i = 0; i <nameList.size(); i++) {

                        ElasticMoodController.GetFeelingFilterMoods getFeelingFilterMoods =
                                new ElasticMoodController.GetFeelingFilterMoods();
                        getFeelingFilterMoods.execute(nameList.get(i).toString(), filterFeeling);

                        try {
                            moodArrayList.addAll(getFeelingFilterMoods.get());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    sortArrayList2 = MoodController.sortMoods(moodArrayList);
                    adapter = new MoodAdapter(this, R.layout.timeline_list, sortArrayList2);
                    moodTimeline.setAdapter(adapter);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            if (selectedFilter == 1) {
                try {
                    for (int i = 0; i < nameList.size(); i++) {

                        ElasticMoodController.GetRecentWeekUserMoods getRecentWeekUserMoods =
                                new ElasticMoodController.GetRecentWeekUserMoods();
                        getRecentWeekUserMoods.execute(nameList.get(i).toString());

                        try {
                            moodArrayList = getRecentWeekUserMoods.get();

                        } catch (Exception e) {
                            Log.i("error", "failed to get the recent week moods out of the async matched");
                        }
                    }
                    sortArrayList2 = MoodController.sortMoods(moodArrayList);
                    adapter = new MoodAdapter(this, R.layout.timeline_list, sortArrayList2);
                    moodTimeline.setAdapter(adapter);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (selectedFilter == 2) {
                filterMessage = intent.getStringExtra("messageFilter");
                try {
                    for (int i = 0; i < nameList.size(); i++) {
                        ElasticMoodController.GetMessageFilterMoods getMessageFilterMoods =
                                new ElasticMoodController.GetMessageFilterMoods();
                        getMessageFilterMoods.execute(nameList.get(i).toString(), filterMessage.toLowerCase());

                        try {
                            moodArrayList = getMessageFilterMoods.get();

                        } catch (Exception e) {
                            Log.i("error", "failed to get the mood out of the async matched");
                        }

                    }
                    sortArrayList2= MoodController.sortMoods(moodArrayList);
                    adapter = new MoodAdapter(this, R.layout.timeline_list, sortArrayList2);
                    moodTimeline.setAdapter(adapter);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


        adapter = new MoodAdapter(this, R.layout.timeline_list, moodArrayList);

        moodTimeline.setAdapter(adapter);
        moodTimeline.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Mood viewMood = moodArrayList.get(position);
                Intent viewMoodIntent = new Intent(FilterResultsActivity.this, ViewMoodActivity.class);
                String trigger = "filter";
                viewMoodIntent.putExtra("trigger",trigger);
                viewMoodIntent.putExtra("viewMood", viewMood);
                startActivity(viewMoodIntent);
            }


        });

    }

}