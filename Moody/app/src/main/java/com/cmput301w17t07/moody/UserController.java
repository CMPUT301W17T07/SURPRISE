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

import android.content.Context;
import android.media.Image;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import static com.cmput301w17t07.moody.ApplicationMoody.FILENAME;


/**
 * Created by mike on 2017-03-04.
 */

/**
 * UserController class for the user class object. Controller that controls user objects with
 * regards to offline functionality. The UserController also acts as a communicator
 * between User objects and the ElasticUserController.
 */

public class UserController {
    private static User user = null;
    // not currently fully implemented
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
     * createUser method. Checks appropriate responses for if a user can be created and then
     * communicates to the ElasticSearchUserController to add the user to the database. Returns
     * int values that act as flags that will indicate appropriate response on the activity pages
     * @param username      user's desired username
     * @return              0,1, or 3. 0 indicates that the user was created. 1 indicates that the username
     *                      is not unique. 3 indicates that the device is not connected to the internet
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
            //adding to the database
            user = new User(username);
            ElasticSearchUserController.AddUser addUser = new ElasticSearchUserController.AddUser();
            addUser.execute(user);
        }

        return 0;
    }

    /**
     * createUser method. Checks appropriate responses for if a user can be created and then
     * communicates to the ElasticSearchUserController to add the user to the database. Returns
     * int values that act as flags that will indicate appropriate response on the activity pages
     * @param username          user's desired username
     * @param profilePicture    user's desired profile picture
     * @return
     */
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
            user = new User(username);
            ElasticSearchUserController.AddUser addUser = new ElasticSearchUserController.AddUser();
            addUser.execute(user);
        }

        return 0;
    }

    /**
     * checkUsername method that communicates with the ElasticSearchUserController to determine
     * if a user's username is unique.
     * @param username          user's desired username
     * @return                  boolean value indicating whether username is unique
     */
    public static Boolean checkUsername(String username){
        // function to check if username is unique
        Boolean uniqueFlag = false;

        ElasticSearchUserController.UniqueUsername uniqueUsername =
                new ElasticSearchUserController.UniqueUsername();

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

    /**
     * Method that saves the username locally. This allows us to always have a username
     * to pass to the elasticSearch controllers and then on to our server.
     * @param username          User's username
     * @param ctx
     */
    public static void saveUsername(String username, Context ctx) {
        FileOutputStream outputStream;
        try {
            outputStream = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            outputStream.write(username.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Method that returns the locally saved username.
     *
     * Logic for reading a locally saved file from:
     * link: http://stackoverflow.com/questions/9095610/android-fileinputstream-read-txt-file-to-string
     * Author: user370305  Feb 1 '12 at 12:19
     * Taken by: Nick Anic 2017/03/09
     * @param ctx
     * @return  fileContent         StringBuffer of the saved username
     */
    public StringBuffer readUsername(Context ctx) {
        FileInputStream inputStream;
        StringBuffer fileContent = new StringBuffer("");
        int n;
        byte[] buffer = new byte[1024];
        try {
            inputStream = ctx.openFileInput(FILENAME);
            while ((n = inputStream.read(buffer)) != -1)
            {
                fileContent.append(new String(buffer, 0, n));
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}