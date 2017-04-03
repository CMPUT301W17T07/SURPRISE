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
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * This is the Mood model class for the Moody application. It contains the data that is displayed
 * in a user's posted mood.
 */

public class Mood implements Serializable {

    //private static final long serialVersionUID = 1L;
    private static final long serialVersionUID = -7060210544600464481L;
    private String moodMessage;
    private Date date;
    private Double[] random;
    private double latitude;
    private double longitude;

    private String moodImageID; //use getMoodImage to decode string into Bitmap
    private String socialSituation;
    private String feeling; //anger, confusion, disgust, fear, happy,sad, shame, surprise
    private String username;
    private String displayUsername;
    private String displayLocation;

    public String getDisplayLocation() {
        return displayLocation;
    }

    public void setDisplayLocation(String displayLocation) {
        this.displayLocation = displayLocation;
    }

    protected String encodedImage;
    protected Boolean idType = true;
    @JestId

    private String id;

    public Mood(Date date) {
        this.date = date;
    }

    /**
     * Function used by jestDroid to retrieve the unique id of the mood object on the server <br>
     * @return id <br>
     */
    public String getId() {
        return id;
    }

    /**
     * Function used by jestDroid to set the unique id of the mood object on the server <br>
     * @param id <br>
     */
    public void setId(String id) {
        this.id = id;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Constructor for the mood object. Takes in a mandatory feeling and username, and "optional"
     * parameters such as location, imageID, mood message, and social situation. "Optional" parameters
     * means that objects can be passed in as null. <br>
     * @param feeling               The associated feeling of a mood (happiness, confusion, etc.) <br>
     * @param username              The user's username <br>
     * @param moodMessage           The textual explanation for a user's mood <br>
     * @param latitude              The user's pinned location (Will be null in part 4, as it's not yet implemented) <br>
     * @param longitude <br>
     * @param socialSituation       The user's selected socialSituation (alone, with a crowd, etc.) <br>
     */
    public Mood(String feeling, String username, String moodMessage, double latitude,double longitude,
                Bitmap image, String socialSituation,Date date,String displayLocation){
        this.feeling = feeling;
        this.username = username.toLowerCase();
        this.displayUsername = username;
        this.moodMessage = moodMessage;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.encodedImage = encodeImage(image);
        this.socialSituation = socialSituation;
        this.displayLocation = displayLocation;
    }


    public String getMoodMessage() {
        return moodMessage;
    }

    public void setMoodMessage(String moodMessage) {
        this.moodMessage = moodMessage;
    }

    /**
     *  Logic on date to string from http://www.java-examples.com/java-date-string-example <br>
     * @return <br>
     */
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRandom(Double passedLatitude, Double passedLongitude){
        this.random = new Double[2];
        this.random[0] = passedLongitude;
        this.random[1] = passedLatitude;
    }

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
    // @Override
    public String toString( ){
        return "Name: " + username + "\n" + "feeling: " + feeling + "\n" + "Moodmessage: " + moodMessage + "\n" +
                "date: " + date + "\n"  + "location: " + latitude + " " + longitude + "\n" + "sociation: " + socialSituation;
    }

    /**
     * This method decodes the encodedImage string that is stored on the Moody application's server
     * and allows the image to be displayed from the server in the ViewMoodActivity. <br>
     * @return bitmap       The decoded bitmap image from the server <br>
     */
    public Bitmap decodeImage() {
        Bitmap bitmap;
        bitmap = null;
        if(encodedImage != null){
            byte[]bitmapArray = null;
            bitmapArray= Base64.decode(encodedImage, Base64.URL_SAFE);
            bitmap= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        }
        return bitmap;
    }

    /**
     * This method encodes and compresses a user's supplied bitmap image for appropriate storage
     * on the application's server. Images stored on the server are all under 65536 bytes. <br>
     *
     * Logic for compression of the image is from: <br>
     * link: http://blog.csdn.net/harryweasley/article/details/51955467 <br>
     * Author: Harry Weasley <br>
     * Taken by: Xin Huang 2017/03/11 <br>
     *
     * Logic for encoding of image string was based upon: <br>
     * link: http://stackoverflow.com/questions/12796579/how-to-send-image-bitmap-to-server-in-android-with-multipart-form-data-json <br>
     * Author: Carnal <br>
     * Taken by: Michael Simion 2017/03/10 <br>
     * @param moodImage         The user's supplied image for his or her mood <br>
     * @return encodedImage     The encodedImage string that will be stored within the object on the server <br>
     */
    public String encodeImage(Bitmap moodImage){
        if(moodImage == null){
            return null;
        }
        try {
            /**
             * Compression of image. From: http://blog.csdn.net/harryweasley/article/details/51955467 <br>
             * author: HarryWeasley 2016-07-20 15:26 <br>
             * taken by Xin Huang 2017-03-04 18:45 <br>
             * for compressing the image to meet the project storage requirements <br>
             */
            while (((moodImage.getRowBytes() * moodImage.getHeight()) / 8) > 65536) {
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inPreferredConfig = Bitmap.Config.RGB_565;
                Matrix matrix = new Matrix();
                matrix.setScale(0.5f, 0.5f);
                moodImage = Bitmap.createBitmap(moodImage, 0, 0, moodImage.getWidth(),
                        moodImage.getHeight(), matrix, true);
            }
        } catch (Exception E){

        }
        // Encoding of Image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        moodImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes,Base64.URL_SAFE);
        return encodedImage;

    }

}