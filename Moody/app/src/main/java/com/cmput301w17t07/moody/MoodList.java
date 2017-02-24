package com.cmput301w17t07.moody;

import java.util.ArrayList;

/**
 * Created by mike on 2017-02-23.
 */

public class MoodList {

    public ArrayList<Mood> moodList = null;

    public MoodList(ArrayList<Mood> moodList) {
        moodList = new ArrayList<Mood>();
    }

    public void addMood(Mood mood){
        // add mood to database?
    }

    public void deleteMood(Mood mood){
        // delete mood from database?
    }

    public ArrayList<Mood> getMoodList(){
        // return moodList from database?
        return moodList;
    }

    public void setMoodList(){
        // something in here potentially
    }


}
