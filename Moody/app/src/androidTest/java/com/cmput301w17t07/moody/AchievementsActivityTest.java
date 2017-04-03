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
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ImageButton;

import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * Created by anicn on 2017-04-01.
 */

public class AchievementsActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public AchievementsActivityTest() {
        super(com.cmput301w17t07.moody.ProfileActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());

    }
    
    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    @Test
    public void testAchievementsCreateTest() {
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        solo.clickOnMenuItem("Create");
        solo.pressSpinnerItem(0, 0);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(0, "anger"));
        solo.pressSpinnerItem(0, 1);
        assertTrue("mood spinner test confusion", solo.isSpinnerTextSelected(0, "confusion"));

        solo.pressSpinnerItem(1, 1);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(1, "alone"));
        solo.pressSpinnerItem(1, 2);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(1, "with two people"));


        solo.enterText((EditText) solo.getView(R.id.Description), "achievement");
        solo.clickOnButton("Send");

        solo.clickOnMenuItem("Profile");
        solo.clickOnImageButton(0);
        solo.waitForText("Post your first mood");

    }

    public void testAchievementsEditTest(){
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        solo.clickInList(0);
        solo.clickOnImageButton(1);
        solo.enterText((EditText) solo.getView(R.id.editDescription), "!");
        solo.clickOnButton("Send");
        solo.clickOnMenuItem("Profile");
        solo.clickOnImageButton(0);
        solo.waitForText("Edited a mood");
        solo.clickOnMenuItem("Profile");
        solo.clickInList(0);
        ImageButton imageButton = (ImageButton) solo.getView(R.id.deleteButton);
        solo.clickOnView(imageButton);
    }

}
