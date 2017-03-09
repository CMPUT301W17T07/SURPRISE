package com.cmput301w17t07.moody;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Alex on 2017-03-08.
 */

public abstract class BarMenuActivity extends AppCompatActivity {

    public void setUpMenuBar(Context context) {

        final Context currentContext = context;

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                Class timelineActivity = TimelineActivity.class;
                                // makes sure that you don't try to switch to same activity
                                if(currentContext.getClass() == timelineActivity){
                                    break;
                                }
                                else{
                                    Intent home = new Intent (currentContext, timelineActivity);
                                    startActivity(home);
                                    //setContentView(R.layout.activity_timeline);
                                    break;
                                }
                            case R.id.action_search:
                                Class searchFilterActivity = SearchFilterOptionsActivity.class;
                                if(currentContext.getClass() == searchFilterActivity){
                                    break;
                                }
                                else {
                                    Intent search = new Intent(currentContext, searchFilterActivity);
                                    startActivity(search);
                                    //setContentView(R.layout.activity_search_filter_options);
                                    break;
                                }
                            case R.id.action_profile:
                                Class profileActivity = ProfileActivity.class;
                                if(currentContext.getClass() == profileActivity){
                                    break;
                                }
                                else {
                                    Intent profile = new Intent(currentContext, profileActivity);
                                    startActivity(profile);
                                    //setContentView(R.layout.activity_profile);
                                    break;
                                }
                            case R.id.action_create:
                                Class createMoodActivity = CreateMoodActivity.class;
                                if(currentContext.getClass() == createMoodActivity){
                                    break;
                                }
                                else {
                                    Intent create = new Intent(currentContext, createMoodActivity);
                                    startActivity(create);
                                    //setContentView(R.layout.activity_create_mood);
                                    break;
                                }
                        }
                        return false;
                    }
                }
        );
    }
}
