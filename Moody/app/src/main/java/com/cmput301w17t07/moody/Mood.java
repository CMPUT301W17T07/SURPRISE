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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.Base64;

import java.io.Serializable;

import java.io.ByteArrayOutputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * Created by mike on 2017-02-23.
 */

/**
 * This is the Mood model class for the Moody application. It contains the data that is displayed
 * in a user's posted mood.
 */

public class Mood implements Serializable {

    private static final long serialVersionUID = 1L;

    private String moodMessage;
    private Date date;
    private Location location;
    private String moodImageID; //use getMoodImage to decode string into Bitmap
    private String socialSituation;
    private String feeling; //anger, confusion, disgust, fear, happy,sad, shame, surprise
    private String username;
    private String displayUsername;

    private String encodedImage;

    @JestId

    private String id;

    /**
     * Function used by jestDroid to retrieve the unique id of the mood object on the server
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Function used by jestDroid to set the unique id of the mood object on the server
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Constructor for the mood object. Takes in a mandatory feeling and username, and "optional"
     * parameters such as location, imageID, mood message, and social situation. "Optional" parameters
     * means that objects can be passed in as null.
     * @param feeling               The associated feeling of a mood (happiness, confusion, etc.)
     * @param username              The user's username
     * @param moodMessage           The textual explanation for a user's mood
     * @param location              The user's pinned location (Will be null in part 4, as it's not yet implemented)
     * @param imageID               The unique server ID of the mood's associated image
     * @param socialSituation       The user's selected socialSituation (alone, with a crowd, etc.)
     */
    public Mood(String feeling, String username, String moodMessage, Location location,
                String imageID, String socialSituation){
        this.feeling = feeling;
        this.username = username.toLowerCase();
        this.displayUsername = username;
        this.moodMessage = moodMessage;
        this.date = new Date();
        this.location = location;
        this.moodImageID = imageID;
        this.socialSituation = socialSituation;
        //        this.moodImage = encodeImage(image);

    }


    public String getMoodMessage() {
        return moodMessage;
    }

    public void setMoodMessage(String moodMessage) {
        this.moodMessage = moodMessage;
    }

    /**
     *  Logic on date to string from http://www.java-examples.com/java-date-string-example
     * @return
     */
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    // todo might be utilized for offline functionality
//    public Bitmap getMoodImage() {
//        Bitmap bitmap;
//        bitmap = null;
//        if(encodedImage != null){
//            byte[]bitmapArray = null;
//            bitmapArray= Base64.decode(encodedImage, Base64.URL_SAFE);
//            bitmap= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
//        }
//        return bitmap;
//    }

    public String getSocialSituation() {
        return socialSituation;
    }

    public void setSocialSituation(String socialSituation) {
        this.socialSituation = socialSituation;
    }

    public void setMoodImage(String moodImage) {
        this.moodImageID = moodImage;
    }



    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getDisplayUsername() {
        return displayUsername;
    }

    public void setDisplayUsername(String displayUsername) {
        this.displayUsername = displayUsername;
    }

    public String getMoodImageID(){
        return this.moodImageID;
    }

    public void setMoodImageID(String moodImageID) {
        this.moodImageID = moodImageID;
    }
}