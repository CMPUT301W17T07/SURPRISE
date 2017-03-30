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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class MapOptionsActivity extends BarMenuActivity {

    private String userText;
    private String feelingText;
    private Integer selectedFilter;

    private String selectedUser;

    private Intent intent;

    private Button resultMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_map_options);
        setUpMenuBar(this);

        Button feelingMapButton = (Button) findViewById(R.id.filterFeelingMap);
        Button usersNearMe = (Button) findViewById(R.id.filterNearUser);

        // ---------------------Filter by User--------------------------------------
        Spinner dropdownUser = (Spinner) findViewById(R.id.filterMapUser);

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
                Toast.makeText(MapOptionsActivity.this, "Please pick a user!", Toast.LENGTH_SHORT).show();
            }
        });


        // -------------------------Filter by Feeling-------------------------------------------
        Spinner dropdownFeeling = (Spinner) findViewById(R.id.filterMapFeeling);

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
                Toast.makeText(MapOptionsActivity.this, "Please pick a feeling!", Toast.LENGTH_SHORT).show();
            }
        });


        usersNearMe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent createMap2 = new Intent(MapOptionsActivity.this, MapsActivity.class);
                createMap2.putExtra("selectedUser", "nearMe");
                startActivity(createMap2);
                finish();
            }
        });


        feelingMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent createMap = new Intent(MapOptionsActivity.this, MapsActivity.class);
                createMap.putExtra("feelingFilter", feelingText);
                createMap.putExtra("selectedUser", selectedUser);
                startActivity(createMap);
                finish();
            }
        });

    }
}