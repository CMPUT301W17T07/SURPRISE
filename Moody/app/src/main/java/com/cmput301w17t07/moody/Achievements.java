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

import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by mike on 2017-03-30.
 */

public class Achievements implements Serializable {

    private static final long serialVersionUID = 3L;
    private ArrayList<String> achievements = new ArrayList<>();

    // Achievement String
    String firstMood = "Posted your first mood!";

    // follow attributes
    public int followCount;
    public int followerCount;

    // Mood specific attributes
    public int moodCount;
    boolean moodCountEarned = false;
    public int happyMoodCount;
    boolean happyMoodCountEarned = false;
    public int sadMoodCount;

    // filter attributes
    public int timesFiltering;
    public int filterReturnedNoResults;


    public Achievements() {
    }


    public void checkForMoodAchievements(Context context){
        if(!moodCountEarned){
            if(moodCount >= 1){
                displayAchievement(context, firstMood);
            }
        }
    }


    public void displayAchievement(Context context, String achievement){
            NotificationCompat.Builder b = new NotificationCompat.Builder(context);
            b.setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("{your tiny message}")
                    .setContentTitle("Moody")
                    .setContentText(achievement)
                    .setContentInfo("INFO")
                    .setPriority(Notification.PRIORITY_MAX);

            NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            nm.notify(1, b.build());


    }
}
