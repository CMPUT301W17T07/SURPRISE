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
    String signUpMoody11 = "Welcome!\nCreated an account.";
    String firstMood21 = "Feeling Moody!\nPosted your first mood.";
    String fiveHappy31 = "Happy Camper!\nPosted 5 happiness moods.";
    String fiveAngry41 = "Rampage!\nPosted 5 angry moods.";
    String fiveSad51 = "You are morose!\nPosted 5 sad moods.";
    String editAMood61 = "Big Mistake!\nEdited a mood.";
    String followThem71 = "Follower!\nFollow another person.";
    String followMe81 = "Popular!\nGain a follower.";
    String showMeTheWay91 = "Show Me The Way!\nLaunch google maps.";
    String scaredyCat101 = "Scaredy Cat!\nPost a fear mood.";

    // Achievement Description String
    String signUpMoody12 = "Registered an account.";
    String firstMood22 = "Posted your first mood.";
    String fiveHappy32 = "Posted 5 happiness moods.";
    String fiveAngry42 = "Posted 5 angry moods.";
    String fiveSad52 = "Posted 5 sad moods.";
    String editAMood62 = "Edited a mood.";
    String followThem72 = "Follow another person.";
    String followMe82 = "Get one follower.";
    String showMeTheWay92 = "Launch google maps.";
    String scaredyCat102 = "Post a fear mood.";
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

    // filter attributes
    public int timesFiltering;
    public int filterReturnedNoResults;



    public Achievements() {
    }

}