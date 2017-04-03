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

import android.media.Image;

import io.searchbox.annotations.JestId;

/**
 * The User model class for the Moody application. Stores the information for a user of the
 * Moody application.
 */

public class User {

    private String username;
    private String displayUsername;
    private Image profilePicture;
    private MoodList moodList;
    private FollowingList followingList;
    private FollowerList followerList;

    @JestId

    private String id;

    /**
     * Function used by jestDroid to retrieve the unique id of the user object on the server <br>
     * @return id <br>
     */
    public String getId() {
        return id;
    }

    /**
     * Function used by jestDroid to set the unique id of the user object on the server <br>
     * @param id <br>
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Constructor for the user object. Takes in a username, and stores it as both "displayUsername"
     * that will be displayed in the application, and as a "username", which is a lowercase version
     * used to help with elastic search of the database <br>
     * @param username      The user's selected username <br>
     */
    public User(String username){

        this.username=username.toLowerCase();
        this.displayUsername = username;
        this.profilePicture=null;  // todo make stock profile image user's image
        this.moodList=null;
        this.followingList=null;
        this.followerList=null;
    }

    /**
     * Constructor for the user object. Takes in a username, and stores it as both "displayUsername"
     * that will be displayed in the application, and as a "username", which is a lowercase version
     * used to help with elastic search of the database. This constructor also takes in a profile picture
     * that the user selects. <br>
     * @param username         The user's selected username <br>
     * @param profilePicture   The user's selected profile picture <br>
     */
    public User(String username, Image profilePicture) {
        this.username=username.toLowerCase();
        this.displayUsername = username;
        this.profilePicture = profilePicture;
        this.moodList = null;
        this.followingList=null;
        this.followerList=null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        // Need to check image's size. Separate class object for this?
        this.profilePicture = profilePicture;
    }

    public MoodList getMoodList() {
        return moodList;
    }

    public void setMoodList(MoodList moodList) {
        this.moodList = moodList;
    }

    public FollowingList getFollowingList() {
        return followingList;
    }

    public void setFollowingList(FollowingList followingList) {
        this.followingList = followingList;
    }

    public FollowerList getFollowerList() {
        return followerList;
    }

    public void setFollowerList(FollowerList followerList) {
        this.followerList = followerList;
    }

    public String getDisplayUsername() {
        return displayUsername;
    }

    public void setDisplayUsername(String displayUsername) {
        this.displayUsername = displayUsername;
    }
}