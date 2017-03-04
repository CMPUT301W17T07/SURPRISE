package com.cmput301w17t07.moody;

import android.media.Image;

import io.searchbox.annotations.JestId;

/**
 * Created by mike on 2017-02-23.
 */

public class User {

    private String username;
    private Image profilePicture;
    private MoodList moodList;
    private FollowingList followingList;
    private FollowerList followerList;

    @JestId

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public User(String username){

        this.username= username;
        this.profilePicture= null;
        this.moodList= null;
        this.username=username;
        this.profilePicture=null;
        this.moodList=null;
        this.followingList=null;
        this.followerList=null;
    }

    public User(String username,MoodList moodList){
        this.username=username;
        this.moodList=moodList;
    }

    public User(String username, Image profilePicture, MoodList moodList,FollowingList followingList
    ,FollowerList followerList) {
        this.username = username;
        this.profilePicture = profilePicture;
        this.moodList = moodList;
        this.followingList=followingList;
        this.followerList=followerList;
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
}
