package com.cmput301w17t07.moody;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * Created by mike on 2017-02-23.
 */

public class Mood {
    private String moodMessage;
    private Date date;
    private Location location;
    private String moodImage; //use getMoodImage to decode string into Bitmap
    private String socialSituation;
    private String feeling; //anger, confusion, disgust, fear, happy,sad, shame, surprise
    private String username;
    private String displayUsername;

    private String encodedImage;
//    private Image emoji;

    @JestId

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public enum Feeling{
//        anger, confusion, disgust, fear, happy,sad, shame, surprise
//    }
//
//    public Mood(String feeling, String username){
//        this.feeling = feeling;
//        this.username = username;
//        this.moodMessage =null;
//        this.date=new Date();
//        this.location=null;
//        this.moodImage=null;
//        this.socialSituation=null;
//
//    }

    public Mood(String feeling, String username, String moodMessage, Location location,
                Bitmap image, String socialSituation){
        this.feeling = feeling;
        this.username = username.toLowerCase();
        this.displayUsername = username;
        this.moodMessage = moodMessage;
        this.date = new Date();
        this.location = location;
        this.moodImage = encodeImage(image);
        this.socialSituation = socialSituation;

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
    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String stringDate = dateFormat.format(this.date);
        return stringDate;
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

    public Bitmap getMoodImage() {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedImage;
    }

    public String getSocialSituation() {
        return socialSituation;
    }

    public void setSocialSituation(String socialSituation) {
        this.socialSituation = socialSituation;
    }

    public void setMoodImage(String moodImage) {
        this.moodImage = moodImage;
    }

    public String encodeImage(Bitmap moodImage){
        if(moodImage == null){
            return null;
        }
        // http://stackoverflow.com/questions/12796579/how-to-send-image-bitmap-to-server-in-android-with-multipart-form-data-json
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        moodImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;

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

//    public void setEmoji(){
//        switch (this.getFeeling()) {
//            case "happy":
//                 //todo set this.emoji to be equal to image
//                break;
//            case "sad":
//                break;
//        }
//    }


    public String getDisplayUsername() {
        return displayUsername;
    }

    public void setDisplayUsername(String displayUsername) {
        this.displayUsername = displayUsername;
    }
}
