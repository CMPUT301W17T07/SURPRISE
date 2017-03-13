package com.cmput301w17t07.moody;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by anicn on 2017-03-12.
 */

public class FilterActivity extends BarMenuActivity {

    private String userText;
    private String feelingText;
    private String dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter_mood);
        setUpMenuBar(this);

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
                feelingText = "Mood Feelings: " + parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(FilterActivity.this, "Please pick a feeling!", Toast.LENGTH_SHORT).show();
            }
        });

        // -------------------------Filter by Date------------------------------------------------
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



    }

}