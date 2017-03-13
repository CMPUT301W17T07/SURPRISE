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



    }
}

