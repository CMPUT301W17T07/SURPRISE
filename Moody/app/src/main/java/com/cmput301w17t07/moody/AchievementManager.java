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

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by mike on 2017-03-30.
 */

public class AchievementManager {

    static final String prefFile = "Achievements";
    static final String akey = "achievements";

    Context context;

    static private AchievementManager achievementManager = null;

    /* intialization method of the object*/
    public static void initManager(Context context){
        if(achievementManager == null){
            if(context == null){
                throw new RuntimeException("Missing context for achievementManager");
            }
            /* Creating a new manager */
            achievementManager = new AchievementManager(context);
        }
    }
    /* getter */
    public static AchievementManager getManager(){
        if(achievementManager == null){
            throw new RuntimeException("Didn't initialize manager");
        }
        return achievementManager;
    }

    /* constructor */
    public AchievementManager(Context context){
        this.context = context;
    }

    /* loadRecordList method. Following implementation detailed by Abram Hindle in video
     *  referenced above*/
    public Achievements loadAchievements() throws IOException, ClassNotFoundException {
        SharedPreferences settings = context.getSharedPreferences(prefFile, context.MODE_PRIVATE);
        String achievementData = settings.getString(akey, "");
        if(achievementData.equals("")){
            /* Returning a new Achievements if one does not exist */
            return new Achievements();
        } else{
            return achievementsFromString(achievementData);
        }
    }

    /* recordListFromString method. Following implementation detailed by Abram Hindle in video
    *  referenced above*/
    static public Achievements achievementsFromString(String achievementsData) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(achievementsData, Base64.DEFAULT));
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (Achievements) oi.readObject();
    }

    /* saveRecordList method. Following implementation detailed by Abram Hindle in video
    *  referenced above*/
    public void saveAchievements(Achievements a) throws IOException {
        SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(akey, achievementsToString(a));
        editor.apply();
    }
    /* recordListToString method. Following implementation detailed by Abram Hindle in video
     *  referenced above*/
    static public String achievementsToString(Achievements a) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(a);
        oo.close();
        byte bytes[] = bo.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}

