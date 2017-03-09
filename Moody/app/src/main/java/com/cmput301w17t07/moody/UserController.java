package com.cmput301w17t07.moody;

import android.media.Image;

import java.util.concurrent.ExecutionException;

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

    /**
     *
     * @param username
     * @return 0,1, or 3. 0 indicates that the user was created. 1 indicates that the username
     * is not unique. 3 indicates that the device is not connected to the internet
     */
    public static int createUser(String username){
        if(!checkInternet()){
            //if device is not connected to the internet...
            return 3;
        }
        else if(!checkUsername(username)){
            // if checkUsername returns false...
            return 1; // timeline activity will check to see if username meets requirements
        }
        else{
//            User newUser = new User(username);
//            ElasticSearchMoodyController.AddUser addUser = new ElasticSearchMoodyController.AddUser();
//            addUser.execute(newUser);
            user = new User(username);
            ElasticSearchMoodyController.AddUser addUser = new ElasticSearchMoodyController.AddUser();
            addUser.execute(user);
        }

        return 0;
    }

    public static int createUser(String username, Image profilePicture){
        if(!checkInternet()){
            //if device is not connected to the internet...
            return 3;
        }
        else if(!checkUsername(username)){
            // if checkUsername returns false...
            return 1; // timeline activity will check to see if username meets requirements
        }
        else if(!checkProfilePicture(profilePicture)){
            // if checkProfilePicture returns false...
            return 2; // timeline activity will check to see if profilePicture meets size requirements
        }
        else{
//            User newUser = new User(username);
//            ElasticSearchMoodyController.AddUser addUser = new ElasticSearchMoodyController.AddUser();
//            addUser.execute(newUser);
            user = new User(username);
            ElasticSearchMoodyController.AddUser addUser = new ElasticSearchMoodyController.AddUser();
            addUser.execute(user);
        }

        return 0;
    }

    public static Boolean checkUsername(String username){
        // function to check if username is unique
        Boolean uniqueFlag = false;

        ElasticSearchMoodyController.UniqueUsername uniqueUsername =
                new ElasticSearchMoodyController.UniqueUsername();

        try {
            uniqueFlag = uniqueUsername.execute(username).get();
        } catch (Exception E){
            return null; //placeholder catch logic
        }

        return uniqueFlag; //returns true if username is unique
    }

    public static Boolean checkProfilePicture(Image profilePicture){
        //todo implement image size checker method
        // function to check if profile picture meets size requirements

        return true; //placeholder
    }

    public static Boolean checkInternet(){
        //todo implement internet checker code here
        return true;
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
