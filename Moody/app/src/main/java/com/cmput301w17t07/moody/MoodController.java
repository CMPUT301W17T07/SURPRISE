package com.cmput301w17t07.moody;

import android.graphics.Bitmap;
import android.location.Location;
import android.media.Image;
import java.util.Date;

/**
 * Created by mike on 2017-02-23.
 */

public class MoodController {

    private static Mood mood = null;

    //todo getMood method. Not sure how exactly to do this yet. Needs to retrieve the mood the user
    // has selected

    public static Boolean createMood(String feeling, String username, String moodMessage,
                                     Location location, Bitmap image, String socialSituation){
        if(!checkMoodMessage(moodMessage)){
            // if it returns false...
            return false;
        }
        if(!checkMoodImage(image)){
            // if moodImage size does not meet requirements...
            return false;
        }

        //todo implement mood constructor with all parameters

//        Mood newMood = new Mood(feeling, username);
        Mood newMood = new Mood(feeling, username, moodMessage, location, image, socialSituation);

        ElasticMoodController.AddMood addMood = new ElasticMoodController.AddMood();
        addMood.execute(newMood);

        return true;
    }

    public static Boolean checkMoodMessage(String moodMessage){
        //todo implement method to check message length
        return true;
    }

    public static Boolean checkMoodImage(Bitmap image){
        //todo implement xin's method of checking image size
        return true;
    }


    public String getMoodMessage() {
        return mood.getMoodMessage();
    }

    public void setMoodMessage(String moodMessage) {
        mood.setMoodMessage(moodMessage);
    }

    public String getDate() {
        return mood.getDate();
    }

    public void setDate(Date date) {
        mood.setDate(date);
    }

    public Location getLocation() {
        return mood.getLocation();
    }

    public void setLocation(Location location) {
        mood.setLocation(location);
    }

    public Bitmap getMoodImage() {
        return mood.getMoodImage();
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
