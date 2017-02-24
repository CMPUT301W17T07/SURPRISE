package com.cmput301w17t07.moody;

import android.media.Image;

import java.util.Date;

/**
 * Created by mike on 2017-02-23.
 */

public class Mood {
    private String moodMessage;
    private Date date;
    private String location;
    private Image moodImage;
    private String socialSituation;

    public enum Feeling{
        happy,sad
    }

    public Mood(String moodMessage){
        this.moodMessage =moodMessage;
        this.date=new Date();
        this.location=null;
        this.moodImage=null;

    }
    public Mood(String moodMessage, String location, Image moodImage) {
        this.moodMessage = moodMessage;
        this.date = new Date();
        this.location = location;
        this.moodImage = moodImage;
    }

    public String getMoodMessage() {
        return moodMessage;
    }

    public void setMoodMessage(String moodMessage) {
        this.moodMessage = moodMessage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
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
}
