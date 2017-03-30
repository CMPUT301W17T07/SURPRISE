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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * FilterResultsActivity class. User interface logic for the view that allows the user
 * to view the filtered moods they requested.
 */

public class FilterResultsActivity extends BarMenuActivity {

    String username;
    private ListView moodTimeline;
    private TimelineAdapter adapter;
    private ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private Button feelingFilterButton;
    private Button messageFilterButton;
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
//               System.out.println("this is moodlist"+moodArrayList);

                } catch (Exception e) {
                    Log.i("error", "failed to get the mood out of the async matched");
                }
            }
            // if equals 1 filter by date
            if(selectedFilter == 1) {
                ElasticMoodController.GetRecentWeekUserMoods getRecentWeekUserMoods =
                        new ElasticMoodController.GetRecentWeekUserMoods();
                //todo will have to change this elastic controller if we want other types of date filters
                getRecentWeekUserMoods.execute(username);

                try {
                    moodArrayList = getRecentWeekUserMoods.get();
//               System.out.println("this is moodlist"+moodArrayList);

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
//               System.out.println("this is moodlist"+moodArrayList);

                } catch (Exception e) {
                    Log.i("error", "failed to get the mood out of the async matched");
                }
            }
        }
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
                            System.out.println("this is fff" + e);
                        }
                    }
                    sortArrayList2=invertOrderList(moodArrayList);
                    adapter = new TimelineAdapter(this, R.layout.timeline_list, sortArrayList2);
                    moodTimeline.setAdapter(adapter);

                }catch (Exception e){
                    System.out.println("this is fff error"+e);
                }

            }
            if (selectedFilter == 1) {
                try {
                    for (int i = 0; i < nameList.size(); i++) {

                        ElasticMoodController.GetRecentWeekUserMoods getRecentWeekUserMoods =
                                new ElasticMoodController.GetRecentWeekUserMoods();
                        //todo will have to change this elastic controller if we want other types of date filters
                        getRecentWeekUserMoods.execute(nameList.get(i).toString());

                        try {
                            moodArrayList = getRecentWeekUserMoods.get();

                        } catch (Exception e) {
                            Log.i("error", "failed to get the recent week moods out of the async matched");
                        }
                    }
                    sortArrayList2=invertOrderList(moodArrayList);
                    adapter = new TimelineAdapter(this, R.layout.timeline_list, sortArrayList2);
                    moodTimeline.setAdapter(adapter);

                }catch (Exception e){
                    System.out.println("this is fff error"+e);
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
                    sortArrayList2=invertOrderList(moodArrayList);
                    adapter = new TimelineAdapter(this, R.layout.timeline_list, sortArrayList2);
                    moodTimeline.setAdapter(adapter);

                }catch (Exception e){
                    System.out.println("this is fff error"+e);
                }
            }
        }


        adapter = new TimelineAdapter(this, R.layout.timeline_list, moodArrayList);
//        Toast.makeText(ProfileActivity.this, moodArrayList.get(1).getFeeling(), Toast.LENGTH_SHORT).show();

        moodTimeline.setAdapter(adapter);
        moodTimeline.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                Mood viewMood = moodArrayList.get(position);

                Intent viewMoodIntent = new Intent(FilterResultsActivity.this, ViewMoodActivity.class);
                viewMoodIntent.putExtra("viewMood", viewMood);
                startActivity(viewMoodIntent);
            }


        });


    }

    // function to sort the timeline moods by date
    // taken from TimelineActivity
    private ArrayList<Mood> invertOrderList(ArrayList<Mood> L) {

        Date d1;
        Date d2;
        Mood mood;
        //pop sort maybe binary sort....
        System.out.println("this is fff lll size "+L.size());
        for (int i = 0; i < L.size() - 1; i++) {
            for (int j = i + 1; j < L.size(); j++) {

                d1=L.get(i).getDate();
                d2=L.get(j).getDate();
                if (d1.before(d2)) {
                    mood = L.get(i);
                    L.set(i,L.get(j));
                    L.set(j,mood);
                }
            }
        }

        return L;
    }

}
