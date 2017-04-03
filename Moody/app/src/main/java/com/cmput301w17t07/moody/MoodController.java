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
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by mike on 2017-02-23.
 */

/**
 * MoodController class object. Controller that controls mood objects with regards to offline
 * functionality. The MoodController also acts as a communicator between Mood objects and
 * the ElasticMoodController.
 */

public class MoodController {

    private static Mood mood = null;
    static MoodList moodList = null;
    private static MoodList timelineMoodList = null;
    private static ConnectivityManager manager;


    /**
     * createMood method that will create a new Mood object if the mood message is determined
     * to be appropriate. Method will also provided the new Mood object with the appropriate image
     * ID for its associated MoodImage object if it is added by the user. This means that this
     * method also creates MoodImage objects.
     *
     * Also communicates with the ElasticMood controller to get it to add the Mood to the server.
     * For project part 5, we will implement an internet checker method to determine whether
     * online or offline code logic needs to be implemented
     * @param feeling           user's selected feeling
     * @param username          user's username
     * @param moodMessage       user's textual explanation for their mood
     * @param image             bitmap of user's attached image
     * @param socialSituation   user's socialSituation
     * @return                  a boolean value indicating whether the mood was created
     */
    public static Boolean createMood(String feeling, String username, String moodMessage,
                                     double latitude, double longitude, Bitmap image,
                                     String socialSituation,Date date,String displayLocation, Context context){
        if(!checkMoodMessage(moodMessage)){
            // if it returns false...
            return false;
        }

        Mood newMood = new Mood(feeling, username, moodMessage, latitude, longitude, image, socialSituation, date,displayLocation);


        if(checkNetwork(context)) {
            ElasticMoodController.AddMood addMood = new ElasticMoodController.AddMood();
            addMood.execute(newMood);
        }
        else{
            newMood.setId(UUID.randomUUID().toString());
            newMood.idType = false;
            moodList = getOfflineMoodList();
            moodList.addMood(newMood);
            saveMoodList();
        }

        return true;
    }

    /**
     * Method for checking the message for appropriate word and character count. Returns a boolean
     * value indicating whether the message complies to requirements.
     * @param moodMessage
     * @return
     */
    public static Boolean checkMoodMessage(String moodMessage){
        String wordCheck = moodMessage.trim();
        if(wordCheck.split("\\s+").length > 3){
            return false;
        }
        else if(moodMessage.length() > 20){
            return false;
        }else{
            return true;
        }
    }


    /**
     *  EditMood method that works almost identically to the createMood method. Editing of mood
     *  date's is currently not implemented. Will discuss more thoroughly with prof/TA about
     *  what is required for date editing, as it is a strange feature...
     *
     *  Noticeable difference of the EditMood method is that it is passed a Date parameter (as
     *  mentioned above), and Mood object. The Mood object passed in is the "oldMood" that will
     *  need to be deleted from the server, as the new "edited mood" will need to be added in its
     *  place.
     * @param feeling
     * @param username
     * @param moodMessage
     * @param image
     * @param socialSituation
     * @param date
     * @param oldMood
     * @return
     */
    public static Boolean editMood(String feeling, String username, String moodMessage,
                                   double latitude,double longitude, Bitmap image,
                                   String socialSituation, Date date, String displayLocation, Mood oldMood, Context context){

        if(!checkMoodMessage(moodMessage)){
            // if it returns false...
            return false;
        }


        Mood editMood = new Mood(feeling, username, moodMessage, latitude,longitude, image, socialSituation,date,displayLocation);

        if(checkNetwork(context)) {
            ElasticMoodController.AddMood addMood = new ElasticMoodController.AddMood();
            ElasticMoodController.DeleteMood deleteMood = new ElasticMoodController.DeleteMood();

            addMood.execute(editMood);
            deleteMood.execute(oldMood.getId());
        }
        else{
            editMood.setId(UUID.randomUUID().toString());
            moodList = getOfflineMoodList();
            moodList.editMood(editMood, oldMood);
            saveMoodList();
        }

        return true;

    }

    static public void deleteMood(Mood mood, Context context){
        if(checkNetwork(context))
            try {
                ElasticMoodController.DeleteMood deleteMood = new ElasticMoodController.DeleteMood();
                deleteMood.execute(mood.getId());
            }catch(Exception e){
                System.out.println("Error when deleting mood in the mood Controller"+e);
            }
        else{
            moodList.deleteMood(mood);
            saveMoodList();
        }
    }

    static public ArrayList<Mood> getUserMoods(String username, String indexOfScroll,
                                               Context context, Boolean profileMoods,
                                               String numberOfMoods){

        ArrayList<Mood> moodArrayList = null;

        if(checkNetwork(context)) {
            // if the user is connected to the network...
            ElasticMoodController.GetUserMoods getUserMoods = new ElasticMoodController.GetUserMoods();
            getUserMoods.execute(username, indexOfScroll, numberOfMoods);

            try {
                moodArrayList = getUserMoods.get();
                // If moods are retrieved from server set them to the local moodList
                if(profileMoods) {
                    if(indexOfScroll.equals("0")) {
                        moodList.setMoods(moodArrayList);
                    }
                    else{
                        moodList.setLoadedMoods(moodArrayList);
                    }
                }


            } catch (Exception e) {
                System.out.println("Error in getUserMoods catch"+e);
                getOfflineMoodList().getMoods();
            }

            return moodArrayList;
        }
        else{
            //to prevent doubling of list with infinite scroll when offline
            if(indexOfScroll.equals("0")) {
                return getOfflineMoodList().getMoods();
            }
            else{
                return new ArrayList<Mood>();
            }
        }
    }


    static public ArrayList<Mood> getTimelineMoods(String username, String indexOfScroll, Context context){

        if(checkNetwork(context)) {

            // setting up array lists for moods and people the user follows
            ArrayList nameList = new ArrayList();
            ArrayList<Mood> moodArrayList = new ArrayList<Mood>();

            // retrieving the list of users the user follows
            FollowController followController = new FollowController();
            FollowingList followingList = followController.getFollowingList(username);

            // getting all the tweets for the timeline
            nameList.addAll(followingList.getFollowingList());

            if(context.getClass() == MapsActivity.class){
                try {
                    for (int i = 0; i < nameList.size(); i++) {
                        moodArrayList.addAll(MoodController.getUserMoods(nameList.get(i).toString(),
                                String.valueOf(indexOfScroll), context, false, String.valueOf(30)));
                    }
                    // sorting the tweets

                } catch (Exception e) {
                    System.out.println("this is a SAD! error with the timeline moods in MoodController" + e);
                    return getOfflineTimelineMoodList().getMoods();
                }
                // returning the sorted array of moods
                return moodArrayList;
            }


            try {
                for (int i = 0; i < nameList.size(); i++) {
                    moodArrayList.addAll(MoodController.getUserMoods(nameList.get(i).toString(),
                            String.valueOf(indexOfScroll), context, false, String.valueOf(6)));
                }
                // sorting the tweets
                moodArrayList = MoodController.sortMoods(moodArrayList);
                // updating local timeline moodlist
                timelineMoodList.setMoods(moodArrayList);
                saveTimelineMoodList();
            } catch (Exception e) {
                System.out.println("this is a SAD! error with the timeline moods in MoodController" + e);
                return getOfflineTimelineMoodList().getMoods();
            }
            // returning the sorted array of moods
            return moodArrayList;
        }
        else{
            return getOfflineTimelineMoodList().getMoods();
        }
    }

    static public MoodList getOfflineMoodList(){
        if (moodList == null){
            try {
                /* Seeing if a previous recordList was saved */
                moodList = MoodManager.getManager().loadMoodList();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("MoodList cannot be de-serialized from MoodManager");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("MoodList cannot be de-serialized from MoodManager");
            }
        }
        return moodList;
    }

    static public MoodList getOfflineTimelineMoodList(){
        if (timelineMoodList == null){
            try {
                /* Seeing if a previous recordList was saved */
                timelineMoodList = MoodManager.getManager().loadTimelineMoodList();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Timeline MoodList cannot be de-serialized from MoodManager");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Timeline MoodList cannot be de-serialized from MoodManager");
            }
        }
        return timelineMoodList;
    }

    static public ArrayList<Mood> sortMoods(ArrayList<Mood> moods){
        Date d1;
        Date d2;
        Mood mood;
        //pop sort maybe binary sort....
        for (int i = 0; i < moods.size() - 1; i++) {
            for (int j = i + 1; j < moods.size(); j++) {

                d1 = moods.get(i).getDate();
                d2 = moods.get(j).getDate();
                if (d1.before(d2)) {
                    mood = moods.get(i);
                    moods.set(i, moods.get(j));
                    moods.set(j, mood);
                }
            }
        }
        return moods;
    }

    /* saveRecordList method*/
    static public void saveMoodList(){
        try {
            MoodManager.getManager().saveMoodList(getOfflineMoodList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("MoodList cannot be de-serialized from recordListManager");
        }
    }

    static public void saveTimelineMoodList(){
        try {
            MoodManager.getManager().saveTimelineMoodList(getOfflineTimelineMoodList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Timeline MoodList cannot be de-serialized from recordListManager");
        }
    }


    static public Boolean checkNetwork(Context context){
        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            // setting to true because now offline and will be going back to online
            ElasticController.connectionFlag = true;
            Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            // determining if we need to re-sync content when coming from offline to online
            if(ElasticController.connectionFlag){
                Toast.makeText(context, "SYNCHING", Toast.LENGTH_SHORT).show();
                synchOffline();
                ElasticController.connectionFlag = false;
            }
            //todo determine if setting the flag to false here is causing any errors
//            ElasticController.connectionFlag = false;
            return true;
        }

    }


    static public void synchOffline(){
        // sending any newly added moods to the server
        int numberAdded = moodList.getAddedOffline().size();
        if(numberAdded > 0){
            for(int i = 0; i < numberAdded; i++){
                try {
                    ElasticMoodController.AddMood addMood = new ElasticMoodController.AddMood();
                    addMood.execute(moodList.addedOffline.get(i));
                }catch(Exception e){
                    System.out.println("Error when synching added moods with the server"+ e);
                }
            }
            moodList.addedOffline.clear();
        }
        int numberDeleted = moodList.getDeletedOffline().size();
        if(numberDeleted > 0){
            for(int i = 0; i < numberDeleted; i++){
                try {
                    ElasticMoodController.DeleteMood deleteMood = new ElasticMoodController.DeleteMood();
                    deleteMood.execute(moodList.deletedOffline.get(i));
                }catch(Exception e){
                    System.out.println("Error when synching deleted moods with the server"+ e);
                }
            }
            moodList.deletedOffline.clear();
        }
    }


    public String getMoodMessage() {
        return mood.getMoodMessage();
    }

    public void setMoodMessage(String moodMessage) {
        mood.setMoodMessage(moodMessage);
    }

    public String getDisplayLocation() {
        return mood.getDisplayLocation();
    }

    public void setDisplayLocation(String displayLocation) {
        mood.setDisplayLocation(displayLocation);
    }

    public Date getDate() {
        return mood.getDate();
    }

    public void setDate(Date date) {
        mood.setDate(date);
    }

    public double getLatitude() {
        return mood.getLatitude();
    }

    public void setLatitude(double latitude) {
        mood.setLatitude(latitude);
    }

    public double getLongitude() {
        return mood.getLongitude();
    }

    public void setLongitude(double longitude) {
        mood.setLongitude(longitude);
    }

    public void setMoodImage(String moodImage) {
        mood.setMoodImage(moodImage);
    }

    public String getSocialSituation() {
        return mood.getSocialSituation();
    }

    public void setSocialSituation(String socialSituation) {
        mood.setSocialSituation(socialSituation);
    }

    public String getFeeling() {
        return mood.getFeeling();
    }

    public void setFeeling(String feeling) {
        mood.setFeeling(feeling);
    }

    public String getUsername() {
        return mood.getUsername();
    }

    public void setUsername(String username) {
        mood.setUsername(username);
    }

}