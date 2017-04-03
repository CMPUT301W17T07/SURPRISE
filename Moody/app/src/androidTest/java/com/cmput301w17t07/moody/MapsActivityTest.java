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

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Panchy on 2017/4/2.
 */
public class MapsActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    String username;
    private ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private ArrayList<Mood> currLocationArrayList = new ArrayList<Mood>();
    private ArrayList<Mood> currLocationArrayListWith5Km = new ArrayList<Mood>();

    public MapsActivityTest() {
        super(com.cmput301w17t07.moody.CreateMoodActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());

    }
    @Test
    public void testonMapReady() throws Exception {
        solo.assertCurrentActivity("Wrong Activity",CreateMoodActivity.class);
        solo.pressSpinnerItem(0, 0);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(0, "anger"));
        solo.clickOnImage(2);

        solo.clickOnButton("Send");
        solo.clickOnMenuItem("Search");

        solo.clickOnButton("MAP");
        solo.pressSpinnerItem(0, 0);

        solo.clickOnButton(0);

        UserController userController = new UserController();
        username = userController.readUsername(getActivity().getApplicationContext()).toString();

        ElasticMoodController.GetFeelingFilterMoods getFeelingFilterMoods =
                new ElasticMoodController.GetFeelingFilterMoods();
        getFeelingFilterMoods.execute(username,"anger");
        try {
            moodArrayList = getFeelingFilterMoods.get();
        } catch (Exception e) {
            Log.i("error", "failed to get filtered feeling moods in map activity");
        }
        solo.waitForView(R.id.map);
        assertNotNull(moodArrayList);


    }

    @Test
    public void testonMapTimeline() throws Exception {
        solo.assertCurrentActivity("Wrong Activity", CreateMoodActivity.class);


        solo.clickOnMenuItem("Search");
        solo.clickOnButton(2);
        solo.pressSpinnerItem(0, 1);
        solo.clickOnButton(0);
        User user=new User("maptest",null);


        Mood mood=new Mood("happy","maptest","mes",0,0,null,null,null,null);


        try {
            moodArrayList = MoodController.getTimelineMoods(user.getUsername(),
                    String.valueOf(0),getActivity());
        } catch(Exception e){
            System.out.println("Error with getting timeline mood in MapsActivity" + e);

        }

        solo.waitForView(R.id.map);
        assertEquals(moodArrayList.size(),0);

    }


    @Test
    public void testonMap5KM() throws Exception {
        solo.assertCurrentActivity("Wrong Activity",CreateMoodActivity.class);
        solo.pressSpinnerItem(0, 0);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(0, "anger"));
        solo.clickOnImage(2);


        solo.clickOnButton("Send");
        solo.clickOnMenuItem("Search");

        solo.clickOnButton("MAP");
        solo.pressSpinnerItem(0,0);

        solo.clickOnButton(1);

        UserController userController = new UserController();
        username = userController.readUsername(getActivity().getApplicationContext()).toString();

        ElasticMoodController.FilterMapByLocation filterMapByLocation =
                new ElasticMoodController.FilterMapByLocation();
        filterMapByLocation.execute();
        try {
            currLocationArrayList.addAll(filterMapByLocation.get());

        } catch (Exception e) {
            System.out.println("this is fff" + e);
        }

        Location location=new Location("pointa");
        location.setLatitude(84.0);
        location.setLongitude(84.0);

        for (int p=0;p< currLocationArrayList.size();p++){
            Location locationNear=new Location("near");
            locationNear.setLatitude(currLocationArrayList.get(p).getLatitude());
            locationNear.setLongitude(currLocationArrayList.get(p).getLongitude());
            float distance=location.distanceTo(locationNear);
            if (distance<=5000.0) {
                currLocationArrayListWith5Km.add(currLocationArrayList.get(p));
            }

        }

        solo.waitForView(R.id.map);
        assertEquals(currLocationArrayListWith5Km.size(),0);

    }


}