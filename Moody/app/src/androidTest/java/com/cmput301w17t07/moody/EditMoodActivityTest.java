package com.cmput301w17t07.moody;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by xin on 2017/3/12.
 */

public class EditMoodActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public EditMoodActivityTest() {
        super(com.cmput301w17t07.moody.ProfileActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());

    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void testAddMood() {
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        solo.clickInList(0);
        solo.clickOnButton("Edit");
        solo.assertCurrentActivity("Wrong Activity", EditMoodActivity.class);
        solo.pressSpinnerItem(0, 2);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(0, "fear"));
        solo.pressSpinnerItem(1, 1);
        assertTrue("mood spinner test anger", solo.isSpinnerTextSelected(1, "with several people"));
        solo.enterText((EditText) solo.getView(R.id.editDescription), "Edit Mood!");

        solo.clickOnButton("Send");
        solo.clickOnMenuItem("Profile");
        solo.clickInList(0);

        solo.waitForText("Test Tweet!Edit Mood!");
        solo.waitForText("with several people");
//        solo.clickOnButton("Delete");

        //assertTrue(solo.waitForText("Test Mood!"));

//        solo.clickOnButton(("Clear"));
//        assertFalse(solo.searchText("Test Tweet!"));
    }
}

