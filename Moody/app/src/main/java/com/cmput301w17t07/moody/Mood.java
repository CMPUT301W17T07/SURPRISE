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
    private Feeling feeling;

    public enum Feeling{
        happy,sad
    }

    public Mood(Feeling feeling){
        this.feeling=feeling;
        this.moodMessage =null;
        this.date=new Date();
        this.location=null;
        this.moodImage=null;
        this.socialSituation=null;

    }

    public Mood(String moodMessage){
        this.moodMessage =moodMessage;
        this.date=new Date();
        this.location=null;
        this.moodImage=null;
        this.socialSituation=null;

    }
    public Mood(String moodMessage, String location, Image moodImage, String socialSituation) {
        this.moodMessage = moodMessage;
        this.date = new Date();
        this.location = location;
        this.moodImage = moodImage;
        this.socialSituation=socialSituation;
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

    public Feeling getFeeling() {
        return feeling;
    }

    public void setFeeling(Feeling feeling) {
        this.feeling = feeling;
    }
}
