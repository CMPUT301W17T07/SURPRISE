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
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Alex on 2017-03-08.
 */

/**
 * The BarMenuActivity extended by all of our currently implemented activities. Activity allows
 * us to easily display the menu bar at the bottom of our application screen without re-writing
 * the same piece of code in our application.
 */
public class BarMenuActivity extends AppCompatActivity {
    /**
     * This method contains the logic for responding to specific button presses on the menu bar.
     * @param context
     */
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
                                    break;
                                }
                        }
                        return false;
                    }
                }
        );
    }
}
