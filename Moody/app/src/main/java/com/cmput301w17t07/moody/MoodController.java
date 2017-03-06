package com.cmput301w17t07.moody;

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
                                     String location, Image image, String socialSituation){
        if(!checkMoodMessage(moodMessage)){
            // if it returns false...
            return false;
        }
        if(!checkMoodImage(image)){
            // if moodImage size does not meet requirements...
            return false;
        }

        //todo implement mood constructor with all parameters

        Mood newMood = new Mood(feeling, username);
        ElasticMoodController.AddMood addMood = new ElasticMoodController.AddMood();
        addMood.execute(newMood);

        return true;
    }

    public static Boolean checkMoodMessage(String moodMessage){
        //todo implement method to check message length
        return true;
    }

    public static Boolean checkMoodImage(Image image){
        //todo implement xin's method of checking image size
        return true;
    }


    public String getMoodMessage() {
        return mood.getMoodMessage();
    }

    public void setMoodMessage(String moodMessage) {
        mood.setMoodMessage(moodMessage);
    }

    public Date getDate() {
        return mood.getDate();
    }

    public void setDate(Date date) {
        mood.setDate(date);
    }

    public String getLocation() {
        return mood.getLocation();
    }

    public void setLocation(String location) {
        mood.setLocation(location);
    }

    public Image getMoodImage() {
        return mood.getMoodImage();
    }
    public void setMoodImage(Image moodImage) {
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
