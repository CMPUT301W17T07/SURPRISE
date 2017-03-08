package com.cmput301w17t07.moody;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Alex on 2017-03-08.
 */

public abstract class BarMenuActivity extends AppCompatActivity {

    public void setUpMenuBar() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                setContentView(R.layout.activity_timeline);
                                break;
                            case R.id.action_search:
                                setContentView(R.layout.activity_search_filter_options);
                                break;
                            case R.id.action_profile:
                                setContentView(R.layout.activity_profile);
                                break;
                            case R.id.action_create:
                                setContentView(R.layout.activity_create_mood);
                                break;
                        }
                        return false;
                    }
                }
        );
    }
}
