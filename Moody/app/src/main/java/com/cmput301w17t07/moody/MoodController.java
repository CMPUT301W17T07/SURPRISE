package com.cmput301w17t07.moody;

import android.graphics.Bitmap;
import android.location.Location;
import android.media.Image;
import android.util.Log;

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



        // ID that will link mood to its respective image
        String moodID = null;

        // checking to see if there is an image to add to the database
        if(image != null){
            // Creating a new image object that will be linked to the proper mood; greasy workaround
            // to prevent slow loading of timeline
            ElasticMoodController.AddImage addImage = new ElasticMoodController.AddImage();
            MoodImage newImage = new MoodImage();
            // encoding image
            newImage.encodeImage(image);

            //adding image to database
            addImage.execute(newImage);
            try {
                moodID = addImage.get().getId();
            } catch (Exception E){
                Log.i("Error", "Weird method resulted in error because method is weird and sucks");
            }

        }



        // ID to link mood to image
        System.out.println("test ID"+ moodID);

        Mood newMood = new Mood(feeling, username, moodMessage, location, moodID, socialSituation);

        ElasticMoodController.AddMood addMood = new ElasticMoodController.AddMood();
        addMood.execute(newMood);

        return true;
    }

    public static Boolean checkMoodMessage(String moodMessage){
        String wordCheck = moodMessage.trim();
        if(wordCheck.split("\\s+").length > 3){
            return false;
        }
        else if(moodMessage.length() > 20){
            return false;
        }else{
            return true;
        }
    }

//    public static Boolean checkMoodImage(Bitmap image){
//        //todo implement xin's method of checking image size
//        return true;
//    }

    public static Boolean editMood(String feeling, String username, String moodMessage,
                           Location location, Bitmap image, String socialSituation, Date date, Mood oldMood){

        if(!checkMoodMessage(moodMessage)){
            // if it returns false...
            return false;
        }

        // ID that will link mood to its respective image
        String moodID = oldMood.getMoodImageID();

        // checking to see if there is an image to add to the database
        if(image != null){
            // Creating a new image object that will be linked to the proper mood; greasy workaround
            // to prevent slow loading of timeline
            ElasticMoodController.AddImage addImage = new ElasticMoodController.AddImage();
            MoodImage newImage = new MoodImage();
            // encoding image. Compression of image also happens here.
            newImage.encodeImage(image);

            //adding image to database
            addImage.execute(newImage);
            try {
                moodID = addImage.get().getId();
            } catch (Exception E){
                Log.i("Error", "Weird method resulted in error because method is weird and sucks");
            }

        }



        // ID to link mood to image
        System.out.println("EDIT test ID"+ moodID);

        Mood editMood = new Mood(feeling, username, moodMessage, location, moodID, socialSituation);
        editMood.setDate(oldMood.getDate());
//        editMood.setId(oldMood.getId());    Will need this if we end up implementing a method that updates instead of edit and delete

        ElasticMoodController.AddMood addMood = new ElasticMoodController.AddMood();
        ElasticMoodController.DeleteMood deleteMood = new ElasticMoodController.DeleteMood();

        addMood.execute(editMood);
        deleteMood.execute(oldMood.getId());

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

    public Location getLocation() {
        return mood.getLocation();
    }

    public void setLocation(Location location) {
        mood.setLocation(location);
    }

//    public Bitmap getMoodImage() {
//        return mood.getMoodImage();
//    }

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
