package com.cmput301w17t07.moody;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.Base64;

import java.io.Serializable;

import java.io.ByteArrayOutputStream;

import java.text.DateFormat;
import java.text.DecimalFormat;
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
    private String moodImageID; //use getMoodImage to decode string into Bitmap
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
                String imageID, String socialSituation){
        this.feeling = feeling;
        this.username = username.toLowerCase();
        this.displayUsername = username;
        this.moodMessage = moodMessage;
        this.date = new Date();
        this.location = location;
        this.moodImageID = imageID;
        this.socialSituation = socialSituation;
        //        this.moodImage = encodeImage(image);

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
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString( ){
        DecimalFormat decimalFormat=new DecimalFormat(".##");
        return "Latitude: "+decimalFormat.format(location.getLatitude())
                +",Longitude: "+decimalFormat.format(location.getLongitude());
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Bitmap getMoodImage() {
        Bitmap bitmap;
        bitmap = null;
        if(encodedImage != null){
            byte[]bitmapArray = null;
            bitmapArray= Base64.decode(encodedImage, Base64.URL_SAFE);
            bitmap= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        }
        return bitmap;
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

//    public String encodeImage(Bitmap moodImage){
//        if(moodImage == null){
//            return null;
//        }
//
//        try {
//            //compress taken from http://blog.csdn.net/harryweasley/article/details/51955467
//            // for compressing the image to meet the project storage requirements
//            while (((moodImage.getRowBytes() * moodImage.getHeight()) / 8) > 65536) {
//                System.out.println("Image size is too big! " + ((moodImage.getRowBytes() * moodImage.getHeight()) / 8));
//
//                BitmapFactory.Options options2 = new BitmapFactory.Options();
//                options2.inPreferredConfig = Bitmap.Config.RGB_565;
//
//                Matrix matrix = new Matrix();
//                matrix.setScale(0.5f, 0.5f);
//                moodImage = Bitmap.createBitmap(moodImage, 0, 0, moodImage.getWidth(),
//                        moodImage.getHeight(), matrix, true);
//
//                System.out.println("Image size is too big! " + ((moodImage.getRowBytes() * moodImage.getHeight()) / 8));
//
//            }
//        } catch (Exception E){
//
//        }
//        System.out.println("Image size is too big! " + ((moodImage.getRowBytes() * moodImage.getHeight()) / 8));
//        // http://stackoverflow.com/questions/12796579/how-to-send-image-bitmap-to-server-in-android-with-multipart-form-data-json
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        moodImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//        byte[] imageBytes = outputStream.toByteArray();
//        encodedImage = Base64.encodeToString(imageBytes,Base64.URL_SAFE);
//        return encodedImage;
//
//    }

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

    public String getMoodImageID(){
        return this.moodImageID;
    }

    public void setMoodImageID(String moodImageID) {
        this.moodImageID = moodImageID;
    }
}
