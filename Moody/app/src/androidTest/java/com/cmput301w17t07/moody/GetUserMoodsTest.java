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

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by mike on 2017-03-05.
 */

/**
 * Tests for the GetUserMoods subclass of the ElasticMood controller. Tests whether a user's mood
 * history is retrieved from the server.
 */

public class GetUserMoodsTest extends ActivityInstrumentationTestCase2 {

    public GetUserMoodsTest() {super(ElasticMoodController.GetUserMoods.class);}

    @Test
    public void testDoInBackground() {
        //todo implement proper test and fix other outdated test cases
        ArrayList<Mood> moods = null;
        Mood mood;
        ElasticMoodController.GetUserMoods getUserMoods = new ElasticMoodController.GetUserMoods();
        getUserMoods.execute("xin");
        try {
            moods = getUserMoods.get();
        } catch (Exception E){
            Log.i("Error","Error when retrieving user moods");
        }

        for(int i = 0; i < 3;i++){
            mood = moods.get(i);
            if( mood.getFeeling() == "sad" ){
                assertTrue(true);
                return;
            }

        }
        assertTrue(false);


    }
}
