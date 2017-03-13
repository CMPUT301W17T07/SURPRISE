package com.cmput301w17t07.moody;

import java.util.ArrayList;

/**
 * Created by mike on 2017-02-23.
 */

/**
 * The MoodList model class for the Moody application. This class object is not currently
 * utilized in our application. May be utilized in the future for part 5. Detailed javadocs
 * would be included then.
 *
 */

public class MoodList {

    public ArrayList<Mood> moodList = new ArrayList<Mood>();


//    public MoodList(ArrayList<Mood> moodList) {
//        moodList = new ArrayList<Mood>();
//    }

    public void addMood(Mood mood){
        // add mood to database?
        if(moodList.contains(mood)){
            throw new IllegalArgumentException();
        }
        else{
            moodList.add(mood);
        }
    }


    public void deleteMood(Mood mood){
        // delete mood from database?
        moodList.remove(mood);
    }

    public int countMoodList(){
        return moodList.size();
    }

    public Mood getMood(int index){
        return moodList.get(index);
    }

    public boolean hasMood(Mood mood){
        return moodList.contains(mood);
    }
//
//    public ArrayList<Mood> getMoodList(){
//        // return moodList from database?
//        return moodList;
//    }
//
//    public void setMoodList(){
//        // something in here potentially
//    }


}
