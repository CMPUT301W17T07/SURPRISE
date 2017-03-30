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

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/**
 *  The ViewMoodActivity handles the user interface logic for when a user is viewing a specific
 *  mood.
 */

public class ViewMoodActivity extends BarMenuActivity {
    private String username;
    public Mood viewMood;
    public Integer id;
    private MoodImage moodImage;
    private Bitmap bitmapImage;
    private Intent intent;
    private String trigger;

    private String viewMoodID;
    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_mood);
        setUpMenuBar(this);
        // get the mood object that was selected
        intent = getIntent();
        viewMood = (Mood) intent.getSerializableExtra("viewMood");
        trigger = intent.getExtras().getString("trigger");
        System.out.println("trigger = "+ trigger);
        TextView location = (TextView) findViewById(R.id.LocationTV);
        Geocoder gcd = new Geocoder(ViewMoodActivity.this, Locale.getDefault());
        try{
            List<Address> addresses = gcd.getFromLocation(viewMood.getLatitude(), viewMood.getLongitude(), 1);

            if (addresses.size() > 0)
                address = "  " + addresses.get(0).getFeatureName() + " " +
                        addresses.get(0).getThoroughfare() + ", " +
                        addresses.get(0).getLocality() + ", " +
                        addresses.get(0).getAdminArea() + ", " +
                        addresses.get(0).getCountryCode();
            location.setText(address);}

        catch(Exception e){
            e.printStackTrace();
        }
        // Get the database id for the selected mood
        viewMoodID =viewMood.getId();
//

        // get username right
        UserController userController = new UserController();
        username = userController.readUsername(ViewMoodActivity.this).toString();
        // if the mood was from user profile allow edit/delete
        if (viewMood.getUsername().equals(username)) {
            //System.out.println("this is erro" +viewMood.getLocation());

            displayAttributes();

            Button deleteButton = (Button) findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    //todo implement this functionality through the mood controller, so that offline
                    // deletion can be handled
                    ElasticMoodController.DeleteMood deleteMood = new ElasticMoodController.DeleteMood();
                    deleteMood.execute(viewMoodID);
                    Intent intent=new Intent(ViewMoodActivity.this, TimelineActivity.class);
                    startActivity(intent);
                    //intent.setClass(ViewMoodActivity.this,TimelineActivity.class);
                    finish();
                }
            });


            // edit mood stuff ...
            Button editButton = (Button) findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent editMoodIntent = new Intent(ViewMoodActivity.this, EditMoodActivity.class);
                    editMoodIntent.putExtra("editMood", viewMood);
                    startActivity(editMoodIntent);

                }
            });
        }
        // else we disable and don't show the edit/delete button
        else {
            Button edit = (Button) findViewById(R.id.deleteButton);
            edit.setVisibility(Button.GONE);
            Button delete = (Button) findViewById(R.id.editButton);
            delete.setVisibility(Button.GONE);
            displayAttributes();
        }
    }

    // display the attributes of the mood that was selected to view
    private void displayAttributes() {

        // NOTE MISSING IMAGE AND LOCATION STILL !!!!!!!!!!!
        TextView user = (TextView) findViewById(R.id.userUsernameTV);
        user.setText(viewMood.getDisplayUsername());

        TextView feeling = (TextView) findViewById(R.id.userFeelingTV);
        feeling.setText(viewMood.getMoodMessage());

        TextView date = (TextView) findViewById(R.id.userDateTV);
        date.setText(viewMood.getDate().toString());



        //TextView location = (TextView) findViewById(R.id.locationTV);
        //System.out.println("thsi is e"+viewMood.locationToString(viewMood.getLocation()));
        //location.setText(viewMood.toString());

        ImageView image = (ImageView) findViewById(R.id.viewMoodImage);
//        //todo handle no image case!!
//        image.setImageBitmap(viewMood.getMoodImage());

        //!!!! NEW STUFF
        String imageID = viewMood.getMoodImageID();
//        Toast.makeText(ViewMoodActivity.this, imageID, Toast.LENGTH_SHORT).show();

        ElasticMoodController.GetMoodImage getMoodImage = new ElasticMoodController.GetMoodImage();
        getMoodImage.execute(imageID);
        // retrieving the image
        try {
            bitmapImage = getMoodImage.get().decodeImage();
        }catch (Exception e){
            Log.i("error","failed to get the moodImage"+imageID);
        }

        image.setImageBitmap(bitmapImage);

        TextView social = (TextView) findViewById(R.id.userSocialTV);
        social.setText(viewMood.getSocialSituation());

        ImageView emoji = (ImageView) findViewById(R.id.userFeelingEmoji);
        switch (viewMood.getFeeling()) {
            case "anger":
                emoji.setImageResource(R.drawable.angry);
                break;
            case "confusion":
                emoji.setImageResource(R.drawable.confused);
                break;
            case "disgust":
                emoji.setImageResource(R.drawable.disgust);
                break;
            case "fear":
                emoji.setImageResource(R.drawable.fear3);
                break;
            case "happiness":
                emoji.setImageResource(R.drawable.happy);
                break;
            case "sadness":
                emoji.setImageResource(R.drawable.sad);
                break;
            case "shame":
                emoji.setImageResource(R.drawable.shame2);
                break;
            case "surprise":
                emoji.setImageResource(R.drawable.surprise2);
                break;
        }

    }
//    @Override
//    public void onBackPressed() {
//        if(trigger.equals("profile")) {
//            Intent intentBack = new Intent(ViewMoodActivity.this, ProfileActivity.class);
//            startActivity(intentBack);
//            this.finish();
//        }
//        else{
//            Intent intentBack = new Intent(ViewMoodActivity.this, TimelineActivity.class);
//            startActivity(intentBack);
//            this.finish();
//        }
//    }

    }
