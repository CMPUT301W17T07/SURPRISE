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
 * AchievementManager class object. Handles the saving and loading of the achievements
 * for the Moody application. Structure and code of this class follows Abram Hindle's Studentpicker
 * tutorial video series as seen here & logic from our Moody application: <br>
 * www.youtube.com/watch?v=5PPD0ncJU1g&list=PL240uJOh_Vb4PtMZ0f7N8ACYkCLv0673O
 */

public class AchievementManager {

    static final String prefFile = "Achievements";
    static final String akey = "activity_achievements";

    Context context;

    static private AchievementManager achievementManager = null;

    /* initialization method of the object*/
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

    /**
     * loadAchievements method is used to load the achievements that are stored
     * in the phones internal storage. Following implementation detailed by Abram Hindle in video
     * referenced above <br>
     * @return Achievements object <br>
     * @throws IOException <br>
     * @throws ClassNotFoundException <br>
     */
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


    /**
     * achievementFromString method. Following implementation by Abraham Hindle in video <br>
     * @param achievementsData <br>
     * @return Achievements object <br>
     * @throws IOException <br>
     * @throws ClassNotFoundException <br>
     */
    static public Achievements achievementsFromString(String achievementsData) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(achievementsData, Base64.DEFAULT));
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (Achievements) oi.readObject();
    }

    /**
     * saveAchievements method. Used to save the achievements to the phone internally.
     * Following implementation detailed by Abram Hindle in video referenced above. <br>
     * @param a <br>
     * @throws IOException <br>
     */
    public void saveAchievements(Achievements a) throws IOException {
        SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(akey, achievementsToString(a));
        editor.commit();
    }

    /**
     * achievementsToString method. Following implementation detailed by
     * Abram Hindle in video referenced above. <br>
     * @param a <br>
     * @return String <br>
     * @throws IOException <br>
     */
    static public String achievementsToString(Achievements a) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(a);
        oo.close();
        byte bytes[] = bo.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}

