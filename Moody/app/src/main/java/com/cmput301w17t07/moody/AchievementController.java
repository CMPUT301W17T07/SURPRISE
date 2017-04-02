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

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by mike on 2017-03-30.
 */

public class AchievementController {

    private static Achievements achievements = null;

    static public Achievements getAchievements(){
        if (achievements == null){
            try {
                /* Seeing if a previous recordList was saved */
                achievements = AchievementManager.getManager().loadAchievements();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Achievements cannot be de-serialized from recordListManager");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Achievements cannot be de-serialized from recordListManager");
            }
        }
        return achievements;
    }


    static public void saveAchievements(){
        try {
            AchievementManager.getManager().saveAchievements(getAchievements());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Achievements cannot be de-serialized from recordListManager");
        }
    }

    public static void checkForMoodAchievements(Context context){
        achievements = getAchievements();
        if(!achievements.moodCountEarned){
            if(achievements.moodCount >= 1){
                displayAchievement(context, achievements.firstMood);
                //add achievement to list and increment score
                achievements.achievementsTitleArray.add(achievements.firstMood);
                achievements.achievementsDescriptionArray.add(achievements.firstMood);
                achievements.moodCountEarned = true;
                achievements.score = achievements.score + 10;
            }
        }
        if (!achievements.fiveHappyMoodCountEarned) {
            if (achievements.fiveHappyMoodCount >= 5) {
                displayAchievement(context, achievements.fiveHappy);
                //add achievement to list and increment score
                achievements.achievementsTitleArray.add(achievements.fiveHappy);
                achievements.fiveHappyMoodCountEarned = true;
                achievements.score = achievements.score + 10;
            }
        }
        if (!achievements.fiveAngryMoodCountEarned) {
            if (achievements.fiveAngryMoodCount >= 5) {
                displayAchievement(context, achievements.fiveAngry);
                //add achievement to list and increment score
                achievements.achievementsTitleArray.add(achievements.fiveAngry);
                achievements.fiveAngryMoodCountEarned = true;
                achievements.score = achievements.score + 10;
            }
        }
        if (!achievements.fiveSadMoodCountEarned) {
            if (achievements.fiveSadMoodCount >= 5) {
                displayAchievement(context, achievements.fiveSad);
                //add achievement to list and increment score
                achievements.achievementsTitleArray.add(achievements.fiveSad);
                achievements.fiveSadMoodCountEarned = true;
                achievements.score = achievements.score + 10;
            }
        }
        if (!achievements.firstTimeRegFlagEarned) {
            if (achievements.firstTimeRegFlag != 0) {
                displayAchievement(context, achievements.signUpMoody);
                //add achievement to list and increment score
                achievements.achievementsTitleArray.add(achievements.signUpMoody);
                achievements.firstTimeRegFlagEarned = true;
                achievements.score = achievements.score + 10;
            }
        }
        if (!achievements.firstTimeEditFlagEarned) {
            if (achievements.firstTimeEditFlag != 0) {
                displayAchievement(context, achievements.editAMood);
                //add achievement to list and increment score
                achievements.achievementsTitleArray.add(achievements.editAMood);
                achievements.firstTimeEditFlagEarned = true;
                achievements.score = achievements.score + 10;
            }
        }
        if (!achievements.followCountEarned) {
            if (achievements.followCount != 0) {
                displayAchievement(context, achievements.followThem);
                //add achievement to list and increment score
                achievements.achievementsTitleArray.add(achievements.followThem);
                achievements.followCountEarned = true;
                achievements.score = achievements.score + 10;

            }
        }
        if (!achievements.followerCountEarned) {
            if (achievements.followerCount != 0) {
                displayAchievement(context, achievements.followMe);
                //add achievement to list and increment score
                achievements.achievementsTitleArray.add(achievements.followMe);
                achievements.followerCountEarned = true;
                achievements.score = achievements.score + 10;

            }
        }
        if (!achievements.launchMapsFlagEarned) {
            if (achievements.launchMapsFlag != 0) {
                displayAchievement(context, achievements.showMeTheWay);
                //add achievement to list and increment score
                achievements.achievementsTitleArray.add(achievements.showMeTheWay);
                achievements.launchMapsFlagEarned = true;
                achievements.score = achievements.score + 10;

            }
        }
        if (!achievements.fearMoodCountEarned) {
            if (achievements.fearMoodCount != 0) {
                displayAchievement(context, achievements.scaredyCat);
                //add achievement to list and increment score
                achievements.achievementsTitleArray.add(achievements.scaredyCat);
                achievements.fearMoodCountEarned = true;
                achievements.score = achievements.score + 10;

            }
        }
    }


    public void incrementMoodCounter (String feeling) {
        switch (feeling) {
            case "anger":
                achievements.fiveAngryMoodCount += 1;
                break;
            case "confusion":
                break;
            case "disgust":
                break;
            case "fear":
                achievements.fearMoodCount += 1;
                break;
            case "happiness":
                achievements.fiveHappyMoodCount += 1;
                break;
            case "sadness":
                achievements.fiveSadMoodCount += 1;
                break;
            case "shame":
                break;
            case "surprise":
                break;
        }

    }


    public static void displayAchievement(Context context, String achievement){
        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        b.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.achievement2)
                .setTicker("{your tiny message}")
                .setContentTitle("Moody")
                .setContentText(achievement)
                .setContentInfo("INFO")
                .setPriority(Notification.PRIORITY_MAX);

        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1, b.build());
    }


}
