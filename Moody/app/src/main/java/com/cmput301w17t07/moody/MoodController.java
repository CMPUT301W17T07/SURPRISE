package com.cmput301w17t07.moody;

import android.media.Image;
import java.util.Date;

/**
 * Created by mike on 2017-02-23.
 */

public class MoodController {

    private static Mood mood = null;

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
        mood.getLocation()
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
        mood.getSocialSituation();
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
