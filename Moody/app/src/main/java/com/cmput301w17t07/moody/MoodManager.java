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
 * Created by mike on 2017-03-28.
 */

public class MoodManager {

    /* MoodListManager class object. Handles the saving and loading of the moodList and moods
 * for the SizeBook application. Structure and code of this class follows Abram Hindle's Studentpicker
 * tutorial video series as seen here & logic from my SizeBook application:
 * www.youtube.com/watch?v=5PPD0ncJU1g&list=PL240uJOh_Vb4PtMZ0f7N8ACYkCLv0673O
 *
 * https://github.com/mikeualberta/simion-SizeBook/blob/master/app/src/main/
 * java/com/example/simion_sizebook/RecordListManager.java
 *
 * */

        static final String prefFile = "MoodList";
        static final String mlkey = "moodList";

        Context context;

        static private MoodManager moodManager = null;

        /* intialization method of the object*/
        public static void initManager(Context context){
            if(moodManager == null){
                if(context == null){
                    throw new RuntimeException("Missing context for moodListManager");
                }
            /* Creating a new manager */
                moodManager = new MoodManager(context);
            }
        }
        /* getter */
        public static MoodManager getManager(){
            if(moodManager == null){
                throw new RuntimeException("Didn't initialize manager");
            }
            return moodManager;
        }

        /* constructor */
        public MoodManager(Context context){
            this.context = context;
        }

        /* loadMoodList method. Following implementation detailed by Abram Hindle in video
         *  referenced above*/
        public MoodList loadMoodList() throws IOException, ClassNotFoundException {
            SharedPreferences settings = context.getSharedPreferences(prefFile, context.MODE_PRIVATE);
            String moodListData = settings.getString(mlkey, "");
            if(moodListData.equals("")){
            /* Returning a new MoodList if one does not exist */
                return new MoodList();
            } else{
                return moodListFromString(moodListData);
            }
        }

        /* moodListFromString method. Following implementation detailed by Abram Hindle in video
        *  referenced above*/
        static public MoodList moodListFromString(String moodListData) throws IOException, ClassNotFoundException {
            ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(moodListData, Base64.DEFAULT));
            ObjectInputStream oi = new ObjectInputStream(bi);
            return (MoodList) oi.readObject();
        }

        /* saveMoodList method. Following implementation detailed by Abram Hindle in video
        *  referenced above*/
        public void saveMoodList(MoodList ml) throws IOException {
            SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(mlkey, moodListToString(ml));
            editor.commit();
        }
        /* moodListToString method. Following implementation detailed by Abram Hindle in video
         *  referenced above*/
        static public String moodListToString(MoodList ml) throws IOException {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(ml);
            oo.close();
            byte bytes[] = bo.toByteArray();
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        }
    }
