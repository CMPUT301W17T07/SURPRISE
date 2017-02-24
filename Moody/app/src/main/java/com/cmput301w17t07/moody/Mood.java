package com.cmput301w17t07.moody;

import android.media.Image;

import java.util.Date;

/**
 * Created by mike on 2017-02-23.
 */

public class Mood {
    private String moodInformation;
    private Date date;
    private String location;
    private Image moodImage;

    public enum feeling{
        happy,sad
    }

    public Mood(String moodInformation){
        this.moodInformation=moodInformation;
        this.date=new Date();
        this.location=null;
        this.moodImage=null;

    }
    public Mood(String moodInformation, String location, Image moodImage) {
        this.moodInformation = moodInformation;
        this.date = new Date();
        this.location = location;
        this.moodImage = moodImage;
    }

    public String getMoodInformation() {
        return moodInformation;
    }

    public void setMoodInformation(String moodInformation) {
        this.moodInformation = moodInformation;
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

    public void setMoodImage(Image moodImage) {
        this.moodImage = moodImage;
    }
}
