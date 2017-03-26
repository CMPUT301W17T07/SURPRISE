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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *  The SearchFilterOptionsActivity handles the user interface logic for when a user is selecting
 *  the type of searching or filtering they would like to conduct.
 */

public class SearchFilterOptionsActivity extends BarMenuActivity {

    private Button searchUser;
    private TextView searchUsername;
    private Button filterMood;
    private Button searchMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter_options);
        setUpMenuBar(this);

        searchUser = (Button) findViewById(R.id.searchUser);
        searchUsername = (TextView) findViewById(R.id.searchEditname);



        // for filtering
        filterMood = (Button) findViewById(R.id.filterMood);
        filterMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filterMoodIntent = new Intent(SearchFilterOptionsActivity.this, FilterActivity.class);
                startActivity(filterMoodIntent);
                finish();
            }
        });
        // for searching
        searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUser = new Intent(SearchFilterOptionsActivity.this, SearchUserActivity.class);
                String username = searchUsername.getText().toString();
                intentUser.putExtra("editUsername", username);
                startActivity(intentUser);
                finish();
            }
        });

        searchMap = (Button) findViewById(R.id.searchMap);
        searchMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(SearchFilterOptionsActivity.this, MapsActivity.class);
                startActivity(mapIntent);
                finish();
            }
        });



    }
}

