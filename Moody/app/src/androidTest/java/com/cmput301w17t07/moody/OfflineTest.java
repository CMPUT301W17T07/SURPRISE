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

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.robotium.solo.Solo;

import java.lang.reflect.Method;

/**
 * Created by xin on 2017/4/2.
 */

public class OfflineTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public OfflineTest() {
        super(com.cmput301w17t07.moody.CreateMoodActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        MoodManager.initManager(getActivity().getApplicationContext());

    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void testAddMood() {

        setWifiEnabled(false);
        solo.assertCurrentActivity("Wrong Activity", CreateMoodActivity.class);
        solo.pressSpinnerItem(0, 0);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(0, "anger"));
        solo.pressSpinnerItem(0, 1);
        assertTrue("mood spinner test confusion", solo.isSpinnerTextSelected(0, "confusion"));

        solo.pressSpinnerItem(1, 1);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(1, "alone"));
        solo.pressSpinnerItem(1, 2);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(1, "with two people"));


        solo.enterText((EditText) solo.getView(R.id.Description), "Test Mood!");

        // solo.clickOnImageButton(0);
        // solo.click
        ImageView photo = (ImageView) solo.getView(R.id.editImageView);



        solo.clickOnButton("Send");
        solo.clickOnMenuItem("Profile");

        solo.clickInList(0);
        solo.clickOnImageButton(1);
        solo.assertCurrentActivity("Wrong Activity", EditMoodActivity.class);
        solo.pressSpinnerItem(0, 2);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(0, "fear"));
        solo.pressSpinnerItem(1, 1);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(1, "with several people"));
        solo.enterText((EditText) solo.getView(R.id.editDescription), "Edit Mood!");
        solo.clickOnImageButton(1);

        solo.clickOnButton("Send");
        solo.clickOnMenuItem("Profile");
        solo.clickInList(0);

        solo.waitForText("Test Tweet!Edit Mood!");
        solo.waitForText("with several people");
        ImageButton imageButton = (ImageButton) solo.getView(R.id.deleteButton);
        solo.clickOnView(imageButton);
    }

    private void setWifiEnabled(boolean state) {
        WifiManager wifiManager = (WifiManager)solo.getCurrentActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(state);
        try{
            ConnectivityManager dataManager=(ConnectivityManager)solo.getCurrentActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            Method dataClass = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
            dataClass.setAccessible(false);
            dataClass.invoke(dataManager, false);}
        catch(Exception e){
            e.printStackTrace();
        }
    }

}