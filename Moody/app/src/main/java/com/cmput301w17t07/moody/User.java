package com.cmput301w17t07.moody;

import android.media.Image;

import io.searchbox.annotations.JestId;

/**
 * Created by mike on 2017-02-23.
 */

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
     * Function used by jestDroid to retrieve the unique id of the user object on the server
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Function used by jestDroid to set the unique id of the user object on the server
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Constructor for the user object. Takes in a username, and stores it as both "displayUsername"
     * that will be displayed in the application, and as a "username", which is a lowercase version
     * used to help with elastic search of the database
     * @param username      The user's selected username
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
     * that the user selects. Not yet implemented in project part 4.
     * @param username         The user's selected username
     * @param profilePicture   The user's selected profile picture
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