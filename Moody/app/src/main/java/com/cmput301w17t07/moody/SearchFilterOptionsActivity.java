package com.cmput301w17t07.moody;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SearchFilterOptionsActivity extends BarMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter_options);
        setUpMenuBar(this);

        Button searchButton = (Button) findViewById(R.id.searchUsers);

        searchButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent search = new Intent(SearchFilterOptionsActivity.this, SearchUserActivity.class);
                startActivity(search);
            }
        });
    }
}
