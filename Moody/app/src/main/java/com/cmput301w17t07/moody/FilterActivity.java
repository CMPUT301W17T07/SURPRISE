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

/**
 * Created by anicn on 2017-03-12.
 */

public class FilterActivity extends BarMenuActivity {

    private String userText;
    private String feelingText;
    private String dateText;
    private Button feelingFilterButton;
    private Button messageFilterButton;
    private Integer selectedFilter;
    private EditText messageFilter;


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
                selectedFilter = 0;
                Intent filterResults = new Intent(FilterActivity.this, FilterResultsActivity.class);
                filterResults.putExtra("feelingFilter", feelingText);
                filterResults.putExtra("selectedFilter", selectedFilter);
                startActivity(filterResults);
                finish();
            }
        });

        // -------------------------Filter by Date------------------------------------------------
        Button dateFilterButton = (Button) findViewById(R.id.filterDateResults);
        dateFilterButton.setEnabled(false);


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

        // ---------------------Filter by Message --------------------------------------

        messageFilterButton = (Button) findViewById(R.id.filterMessageResults);
        messageFilter = (EditText) findViewById(R.id.filterMessageText);



        messageFilterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedFilter = 2;
                String filterMessage = messageFilter.getText().toString();
                Intent filterResults = new Intent(FilterActivity.this, FilterResultsActivity.class);
                filterResults.putExtra("messageFilter", filterMessage);
                filterResults.putExtra("selectedFilter", selectedFilter);
                startActivity(filterResults);
                finish();
            }
        });




    }

}