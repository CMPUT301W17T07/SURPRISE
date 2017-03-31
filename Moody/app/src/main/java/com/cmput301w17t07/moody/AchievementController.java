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

import java.io.IOException;

/**
 * Created by mike on 2017-03-30.
 */

public class AchievementController {

    private static Achievements achievements = null;

    static public Achievements getAchievements(){
        if (achievements == null){
            try {
                /* Seeing if a previous recordList was saved */
                achievements = AchievementManager.getManager().loadAchievements();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Achievements cannot be de-serialized from recordListManager");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Achievements cannot be de-serialized from recordListManager");
            }
        }
        return achievements;
    }


    static public void saveAchievements(){
        try {
            AchievementManager.getManager().saveAchievements(getAchievements());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Achievements cannot be de-serialized from recordListManager");
        }
    }

    public void checkForMoodAchievements(Context context){
    }


}
