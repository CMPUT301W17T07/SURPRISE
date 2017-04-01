
/*
 * Copyright 2017 CMPUT301W17T07
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cmput301w17t07.moody;

import java.io.Serializable;
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

public class MoodList implements Serializable{

    //    private static final long serialVersionUID =  6673446047991058932L;
    private static final long serialVersionUID =  2L;


    public ArrayList<Mood> moodList = null;
    public ArrayList<Mood> addedOffline = new ArrayList<Mood>();
    public ArrayList<String> deletedOffline = new ArrayList<String>();
    public ArrayList<Mood> editedOffline = new ArrayList<Mood>();
    // old IDs of edited moods that will need to be deleted upon synching
    public ArrayList<String> oldIDs = new ArrayList<String>();


    public MoodList() {
        moodList = new ArrayList<Mood>();
    }

    public void addMood(Mood mood){
        moodList.add(0,mood);
        addedOffline.add(mood);
    }


    public void deleteMood(Mood mood){
//        moodList.remove(mood);
        deletedOffline.add(mood.getId());
        for(int i = 0; i < moodList.size(); i++) {
            if (moodList.get(i).getId().equals(mood.getId())) {
                moodList.remove(i);
                return;
            }
        }
        }

    public void editMood(Mood mood, String oldMoodID){
        editedOffline.add(mood);
        oldIDs.add(oldMoodID);
        for(int i = 0; i < moodList.size(); i++){
            if(moodList.get(i).getId().equals(oldMoodID)){
                moodList.remove(i);
                moodList.add(i, mood);
                return;
            }
        }
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

    public ArrayList<Mood> getMoods(){
        return moodList;
    }

    public void setMoods(ArrayList<Mood> moods){
        moodList.clear();
        moodList.addAll(moods);
    }

    public void setLoadedMoods(ArrayList<Mood> moods){
        moodList.addAll(moods);
    }

    public ArrayList<Mood> getAddedOffline() {
        return addedOffline;
    }

    public ArrayList<String> getDeletedOffline() {
        return deletedOffline;
    }

    public ArrayList<Mood> getEditedOffline() {
        return editedOffline;
    }

    public ArrayList<String> getOldIDs() {
        return oldIDs;
    }
}