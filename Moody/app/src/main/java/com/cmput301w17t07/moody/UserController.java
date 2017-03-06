package com.cmput301w17t07.moody;

import android.media.Image;

/**
 * Created by mike on 2017-03-04.
 */

/**
 * UserController class for the user class object.
 */

public class UserController {
    private static User user = null;

    static public User getUser(){
        if(user == null){
            try{
                return null; // temporary
            }catch (Exception e){
                throw new RuntimeException("User cannot be retrieved by controller"); // temporary
            }
        }
        return user;
    }

    public static Boolean createUser(String username){
        if(!checkUsername(username)){
            // if checkUsername returns false...
            return false; // timeline activity will check to see if username meets requirements
        }
        else{
//            User newUser = new User(username);
//            ElasticSearchMoodyController.AddUser addUser = new ElasticSearchMoodyController.AddUser();
//            addUser.execute(newUser);
            user = new User(username);
            ElasticSearchMoodyController.AddUser addUser = new ElasticSearchMoodyController.AddUser();
            addUser.execute(user);
        }

        return true;
    }

    public static Boolean createUser(String username, Image profilePicture){
        if(!checkUsername(username)){
            // if checkUsername returns false...
            return false; // timeline activity will check to see if username meets requirements
        }
        else if(!checkProfilePicture(profilePicture)){
            // if checkProfilePicture returns false...
            return false; // timeline activity will check to see if profilePicture meets size requirements
        }
        else{
//            User newUser = new User(username);
//            ElasticSearchMoodyController.AddUser addUser = new ElasticSearchMoodyController.AddUser();
//            addUser.execute(newUser);
            user = new User(username);
            ElasticSearchMoodyController.AddUser addUser = new ElasticSearchMoodyController.AddUser();
            addUser.execute(user);
        }

        return true;
    }

    public static Boolean checkUsername(String username){
        // function to check if username is unique
        return true; // placeholder
    }

    public static Boolean checkProfilePicture(Image profilePicture){
        // function to check if profile picture meets size requirements

        return true; //placeholder
    }

    public void setUsername(String username){
        user.setUsername(username);
    }

    public void setProfilePicture(Image profilePicture){
        user.setProfilePicture(profilePicture);
    }

    public void setMoodList(MoodList moodList){
        user.setMoodList(moodList);
    }

    public void setFollowingList(FollowingList followingList){
        user.setFollowingList(followingList);
    }

    public void setFollowerList(FollowerList followerList){
        user.setFollowerList(followerList);
    }

    public String getUsername(){
        return user.getUsername();
    }

    public Image getProfilePicture(){
        return user.getProfilePicture();
    }

    public MoodList getMoodList(){
        return user.getMoodList();
    }

    public FollowingList getFollowingList(){
        return user.getFollowingList();
    }

    public FollowerList getFollowerList(){
        return user.getFollowerList();
    }


}
