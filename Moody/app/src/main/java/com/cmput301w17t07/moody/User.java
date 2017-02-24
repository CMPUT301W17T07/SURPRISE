package com.cmput301w17t07.moody;

import android.media.Image;

/**
 * Created by mike on 2017-02-23.
 */

public class User {

    private String username;
    private Image profilePicture;
    private MoodList moodList;

    public User(String username){
        this.username= username;
        this.profilePicture= null;
        this.moodList= null;
    }

    public User(String username,MoodList moodList){
        this.username=username;
        this.moodList=moodList;
    }

    public User(String username, Image profilePicture, MoodList moodList) {
        this.username = username;
        this.profilePicture = profilePicture;
        this.moodList = moodList;
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
}
