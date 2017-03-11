package com.cmput301w17t07.moody;

import android.location.Location;
import android.media.Image;
import android.provider.ContactsContract;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * Created by mike on 2017-02-23.
 */

public class Mood implements Serializable {

    private static final long serialVersionUID = 1L;

    private String moodMessage;
    private Date date;
    private Location location;
    private Image moodImage;
    private String socialSituation;
    private String feeling; //anger, confusion, disgust, fear, happy,sad, shame, surprise
    private String username;
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

    public Mood(String feeling, String username){
        this.feeling = feeling;
        this.username = username;
        this.moodMessage =null;
        this.date=new Date();
        this.location=null;
        this.moodImage=null;
        this.socialSituation=null;

    }

    public Mood(String feeling, String username, String moodMessage, Location location,
                Image image, String socialSituation){
        this.feeling = feeling;
        this.username = username;
        this.moodMessage = moodMessage;
        this.date = new Date();
        this.location = location;
        this.moodImage = image;
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

    public Image getMoodImage() {
        return moodImage;
    }

    public String getSocialSituation() {
        return socialSituation;
    }

    public void setSocialSituation(String socialSituation) {
        this.socialSituation = socialSituation;
    }

    public void setMoodImage(Image moodImage) {
        this.moodImage = moodImage;
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

}
