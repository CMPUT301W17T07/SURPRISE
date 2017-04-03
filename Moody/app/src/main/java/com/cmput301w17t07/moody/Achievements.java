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
    protected ArrayList<String> achievementsTitleArray = new ArrayList<String >();
    protected ArrayList<String> achievementsDescriptionArray = new ArrayList<String >();

    // -------------------------- Achievements ------------------------------------
    // Achievement Title String
    String signUpMoody = "Welcome!\nCreated an account.";
    String firstMood = "Feeling Moody!\nPosted your first mood.";
    String fiveHappy = "Happy Camper!\nPosted 5 happiness moods.";
    String fiveAngry = "Rampage!\nPosted 5 angry moods.";
    String fiveSad = "You are morose!\nPosted 5 sad moods.";
    String editAMood = "Big Mistake!\nEdited a mood.";
    String followThem = "Follower!\nFollow another person.";
    String followMe = "Popular!\nGain a follower.";
    String showMeTheWay = "Show Me The Way!\nLaunch google maps.";
    String scaredyCat = "Scaredy Cat!\nPost a fear mood.";
    // ----------------------------------------------------------------------------------

    // user's total score counter
    int score = 0;

    // first time registration
    public int firstTimeRegFlag = 0;
    boolean firstTimeRegFlagEarned = false;

    // follow attributes
    public int followCount = 0;
    public int followerCount = 0;
    boolean followCountEarned = false;
    boolean followerCountEarned = false;

    // launch google maps
    public int launchMapsFlag = 0;
    boolean launchMapsFlagEarned = false;

    // first time edit
    public int firstTimeEditFlag = 0;
    boolean firstTimeEditFlagEarned = false;

    // fear mood
    public int fearMoodCount = 0;
    boolean fearMoodCountEarned = false;

    // Mood specific attributes
    public int moodCount = 0;
    boolean moodCountEarned = false;
    public int fiveHappyMoodCount = 0;
    boolean fiveHappyMoodCountEarned = false;
    public int fiveAngryMoodCount = 0;
    boolean fiveAngryMoodCountEarned = false;
    public int fiveSadMoodCount = 0;
    boolean fiveSadMoodCountEarned = false;


    public Achievements() {
    }

}
