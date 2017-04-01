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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by anicn on 2017-03-12.
 */

/**
 * FilterActivity class. User interface logic for the view that allows the user to customize filter
 * options.
 */
public class FilterActivity extends BarMenuActivity {

    private String userText;
    private String feelingText;
    private String dateText;
    private Button feelingFilterButton;
    private Button messageFilterButton;
    private Integer selectedFilter;
    private EditText messageFilter;

    private String selectedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_filter_mood);
        setUpMenuBar(this);

        feelingFilterButton = (Button) findViewById(R.id.filterFeelingResults);

        // ---------------------Filter by User--------------------------------------
        Spinner dropdownUser = (Spinner) findViewById(R.id.filterUser);

        String[] users = new String[]{"My Moods", "TimeLine Moods"};
        ArrayAdapter<String> adapterUsers = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, users);
        dropdownUser.setAdapter(adapterUsers);

        dropdownUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                userText = "Filter: " + parent.getItemAtPosition(position).toString();
                selectedUser = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(FilterActivity.this, "Please pick a user!", Toast.LENGTH_SHORT).show();
            }
        });

        // -------------------------Filter by Feeling-------------------------------------------
        Spinner dropdownFeeling = (Spinner) findViewById(R.id.filterFeeling);

        String[] feelings = new String[]{"anger", "confusion", "disgust", "fear", "happiness", "sadness", "shame", "surprise"};
        ArrayAdapter<String> adapterFeelings = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, feelings);
        dropdownFeeling.setAdapter(adapterFeelings);

        dropdownFeeling.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                feelingText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(FilterActivity.this, "Please pick a feeling!", Toast.LENGTH_SHORT).show();
            }
        });

        feelingFilterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MoodController.checkNetwork(FilterActivity.this)){
                    selectedFilter = 0;
                Intent filterResults = new Intent(FilterActivity.this, FilterResultsActivity.class);
                filterResults.putExtra("feelingFilter", feelingText);
                filterResults.putExtra("selectedFilter", selectedFilter);
                filterResults.putExtra("selectedUser", selectedUser);
                startActivity(filterResults);
                finish();
            }
            else{
                return;
            }
            }
        });

        // -------------------------Filter by Date------------------------------------------------
        Button dateFilterButton = (Button) findViewById(R.id.filterDateResults);
//        dateFilterButton.setEnabled(false);


        Spinner dropdownDate = (Spinner) findViewById(R.id.filterDate);

        String[] dates = new String[]{"Last Week"};
        ArrayAdapter<String> adapterDates = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dates);
        dropdownDate.setAdapter(adapterDates);

        dropdownDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                dateText = "Mood Date: " + parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(FilterActivity.this, "Please pick a date!", Toast.LENGTH_SHORT).show();
            }
        });

        // on click of filter date results button
        dateFilterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(MoodController.checkNetwork(FilterActivity.this)) {
                    selectedFilter = 1;
                    Intent filterResults = new Intent(FilterActivity.this, FilterResultsActivity.class);
                    //todo see if we need to pass this in or not (i.e. if we allow for other filter options aside from last week)
//                filterResults.putExtra("dateFilter", dateText);
                    filterResults.putExtra("selectedFilter", selectedFilter);
                    filterResults.putExtra("selectedUser", selectedUser);
                    startActivity(filterResults);
                    finish();
                }
                else{
                    return;
                }
            }
        });

        // ---------------------Filter by Message --------------------------------------

        //todo restrict message to a single word!!
        messageFilterButton = (Button) findViewById(R.id.filterMessageResults);
        messageFilter = (EditText) findViewById(R.id.filterMessageText);



        messageFilterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(MoodController.checkNetwork(FilterActivity.this)) {
                    selectedFilter = 2;
                    String filterMessage = messageFilter.getText().toString();
                    Intent filterResults = new Intent(FilterActivity.this, FilterResultsActivity.class);
                    filterResults.putExtra("messageFilter", filterMessage);
                    filterResults.putExtra("selectedFilter", selectedFilter);
                    filterResults.putExtra("selectedUser", selectedUser);
                    startActivity(filterResults);
                    finish();
                }
                else{
                    return;
                }
            }
        });


    }

}