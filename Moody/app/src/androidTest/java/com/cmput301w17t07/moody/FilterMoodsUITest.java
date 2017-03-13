package com.cmput301w17t07.moody;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by mike on 2017-03-12.
 */

public class FilterMoodsUITest extends ActivityInstrumentationTestCase2<FilterActivity> {

    private Solo solo;



    public FilterMoodsUITest() {
        super(com.cmput301w17t07.moody.FilterActivity.class);
    }


    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testAddMood(){
        // First must create a few moods to have something to filter
        solo.clickOnMenuItem("Create");
        solo.assertCurrentActivity("Wrong Activity", CreateMoodActivity.class);

        // selecting the mood feeling
        solo.pressSpinnerItem(0, 1);
        assertTrue("mood spinner test confusion", solo.isSpinnerTextSelected(0, "confusion"));


        // Entering the mood message
        solo.enterText((EditText) solo.getView(R.id.Description), "Test Mood!");
        solo.waitForText("Test Mood!");
        // Sending mood
        solo.clickOnButton("Send");
        // don't need to enter the ID of the button for this method; can simply enter text on button and it will find it

        solo.assertCurrentActivity("Wrong Activity", TimelineActivity.class);

        //------------------ Creating 2nd Mood ---------------------------
        solo.clickOnMenuItem("Create");
        solo.assertCurrentActivity("Wrong Activity", CreateMoodActivity.class);

        // selecting the mood feeling
        solo.pressSpinnerItem(0, 0);
        assertTrue("mood spinner test confusion", solo.isSpinnerTextSelected(0, "anger"));

        // Entering the mood message
        solo.enterText((EditText) solo.getView(R.id.Description), "hi");
        solo.waitForText("hi");
        // Sending mood
        solo.clickOnButton("Send");
        // don't need to enter the ID of the button for this method; can simply enter text on button and it will find it

        solo.assertCurrentActivity("Wrong Activity", TimelineActivity.class);

        //------------------ Creating 3rd Mood ---------------------------
        solo.clickOnMenuItem("Create");
        solo.assertCurrentActivity("Wrong Activity", CreateMoodActivity.class);

        // selecting the mood feeling
        solo.pressSpinnerItem(0, 1);
        assertTrue("mood spinner test confusion", solo.isSpinnerTextSelected(0, "confusion"));

        // Entering the mood message
        solo.enterText((EditText) solo.getView(R.id.Description), "hi");
        solo.waitForText("hi");
        // Sending mood
        solo.clickOnButton("Send");
        // don't need to enter the ID of the button for this method; can simply enter text on button and it will find it

        solo.assertCurrentActivity("Wrong Activity", TimelineActivity.class);
    }

    public void testFilterFeelingMoods(){
        // navigating to search filter options screen
        solo.clickOnMenuItem("Search");
        solo.assertCurrentActivity("Wrong Activity", SearchFilterOptionsActivity.class);
        // navigating to filter screen
        solo.clickOnButton("Filter Moods");
        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);

        // selecting to filter moods by confusion
        solo.pressSpinnerItem(1, 1);
        assertTrue("mood spinner test confusion", solo.isSpinnerTextSelected(1, "confusion"));

        // pressing button to filter results
        solo.clickOnButton(0);
        solo.assertCurrentActivity("Wrong Activity", FilterResultsActivity.class);
        // Click on 2nd item in the list which should be the test Mood message mood
       // solo.clickInList(0);
        assertTrue(solo.searchText("hi"));
//        solo.waitForText("hi");
        // navigate to profile once it is done
//        solo.clickOnMenuItem("Profile");
//        solo.assertCurrentActivity("Wrong Activity", SearchFilterOptionsActivity.class);

    }

    public void testFilterMessageMoods(){
//        solo.clickOnMenuItem("Search");
//        solo.assertCurrentActivity("Wrong Activity", SearchFilterOptionsActivity.class);
//        // navigating to filter screen
//        solo.clickOnButton("Filter Moods");
//        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);

        // selecting to filter moods by confusion

        // Entering the mood message filter text
        solo.enterText((EditText) solo.getView(R.id.filterMessageText), "hi");
        solo.waitForText("hi");
        // click button to navigate to filter results
        solo.clickOnButton(2);
        solo.assertCurrentActivity("Wrong Activity", FilterResultsActivity.class);
        // Ensure appropriate moods are there
        assertTrue(solo.waitForText("hi"));
        // Ensure incorrect moods are not there
        assertFalse(solo.searchText("Test Mood!"));

    }



}