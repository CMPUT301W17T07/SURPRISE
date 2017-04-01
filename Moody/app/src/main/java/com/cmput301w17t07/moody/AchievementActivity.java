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

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AchievementActivity extends BarMenuActivity {

    private String username;
    private static Achievements achievements;


    // Array of strings for ListView Title
    String[] listviewTitle = new String[]{"MoneyMan"};


    int[] listviewImage = new int[]{R.drawable.achievement2};

    String[] listviewShortDescription = new String[]{"Good Job!"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        setUpMenuBar(this);

        AchievementManager.initManager(AchievementActivity.this);

        UserController userController = new UserController();
        username = userController.readUsername(AchievementActivity.this).toString();

        AchievementController achievementController = new AchievementController();
        achievements = achievementController.getAchievements();

        // display the users username
        final TextView userName = (TextView) findViewById(R.id.usernameText);
        userName.setText(username);
        userName.setTextColor(getResources().getColor(R.color.redTheme));
        userName.setTypeface(null, Typeface.BOLD_ITALIC);

        // display the users score
        final TextView userScore = (TextView) findViewById(R.id.scoreText);
        userScore.setText("Score: " + String.valueOf(achievements.score));
        userScore.setTextColor(getResources().getColor(R.color.redTheme));
        userScore.setTypeface(null, Typeface.BOLD_ITALIC);



//        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
//
//        for (int i = 0; i < achievements.achievementsTitleArray.size(); i++) {
//            HashMap<String, String> hm = new HashMap<String, String>();
//            hm.put("achievement_title", achievements.achievementsTitleArray.get(i));
//            hm.put("achievement_description", achievements.achievementsDescriptionArray.get(i));
//            hm.put("achievement_image", Integer.toString(listviewImage[i]));
//            aList.add(hm);
//        }

//
//        String[] from = {"achievement_image", "achievement_title", "achievement_description"};
//        int[] to = {R.id.achievement_image, R.id.achievement_title, R.id.achievement_short_description};
////
//        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.achievement_list, from, to);
        ListView androidListView = (ListView) findViewById(R.id.test3_list);



        AchievementsAdapter achievementsAdapter = new AchievementsAdapter(AchievementActivity.this, R.layout.achievement_list, achievements.achievementsTitleArray);
        androidListView.setAdapter(achievementsAdapter);


    }
}
