package com.cmput301w17t07.moody;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ImageView;

import com.robotium.solo.Solo;

/**
 * Created by xin on 2017/3/12.
 */

/**
 * Intent testing of creating and deleting moods in the Moody Application. Requires a username
 * to already have been created on device.
 */

public class CreateMoodActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public CreateMoodActivityTest() {
        super(com.cmput301w17t07.moody.CreateMoodActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());

    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void testAddMood() {
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
       // ImageView photo1 = (ImageView) solo.getView(R.id.editImageView);
        //assertEquals(photo, photo1);
        solo.waitForText("Test Tweet!");
        solo.waitForText("with two person");
        solo.clickOnButton("Delete");

        //assertTrue(solo.waitForText("Test Mood!"));

//        solo.clickOnButton(("Clear"));
//        assertFalse(solo.searchText("Test Tweet!"));
    }
}



