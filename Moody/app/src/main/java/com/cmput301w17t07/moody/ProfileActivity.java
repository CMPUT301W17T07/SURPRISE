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

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.cmput301w17t07.moody.ApplicationMoody.FILENAME;

/**
 *  The ProfileActivity handles the user interface logic for when a user is viewing their
 *  mood history.
 */

public class ProfileActivity extends BarMenuActivity {

    private ListView moodTimeline;
    private TimelineAdapter adapter;
    private ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    String username;

//    final Context currentContext = context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpMenuBar(this);
        UserController userController = new UserController();
//        username = userController.getUsername();
        username = userController.readUsername(ProfileActivity.this).toString();

        Toast.makeText(ProfileActivity.this, userController.readUsername(ProfileActivity.this).toString(), Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onStart(){
        super.onStart();
//        ElasticMoodController.GetUserMoods getUserMoods = new ElasticMoodController.GetUserMoods();
//
//        getUserMoods.execute(username);
        ElasticMoodController.GetUserMoods getUserMoods = new ElasticMoodController.GetUserMoods();
        getUserMoods.execute(username);

        final ListView moodTimelineListView = (ListView) findViewById(R.id.test_list);

        try {
            moodArrayList= getUserMoods.get();
//               System.out.println("this is moodlist"+moodArrayList);

        }catch (Exception e){
            Log.i("error","failed to get the mood out of the async matched");
        }

        adapter = new TimelineAdapter(this, R.layout.timeline_list, moodArrayList);
//        Toast.makeText(ProfileActivity.this, moodArrayList.get(1).getFeeling(), Toast.LENGTH_SHORT).show();

        moodTimelineListView.setAdapter(adapter);
        moodTimelineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                Mood viewMood = moodArrayList.get(position);

                Intent viewMoodIntent = new Intent(ProfileActivity.this, ViewMoodActivity.class);
                viewMoodIntent.putExtra("viewMood", viewMood);
                startActivity(viewMoodIntent);


            }


        });




    }

}


