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

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;


/**
 * The CreateMoodActivity for handles the user interface logic for when a user is creating a mood.
 */

public class CreateMoodActivity extends BarMenuActivity implements LocationListener {
    private ImageView mImageView;
    private String EmotionText;
    private String SocialSituation;
    private EditText Description;
    private String userName;

    private LocationManager locationManager;
    private double latitude, longitude;
    private String provider;
    private TextView locationText;
    private Location location;
    private String address;



    Bitmap bitmap = null;

//    private static final String iconPath = Environment.getExternalStorageDirectory() + "/Image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mood);
        UserController userController = new UserController();
        userName = userController.readUsername(CreateMoodActivity.this).toString();
        setUpMenuBar(this);
        location = null;



        /**
         * Spinner dropdown logic taken from http://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
         * Author: Nicolas Tyler, 2013/07/15 8:47
         * taken by Xin Huang 2017/03/10
         */
        Spinner dropdown = (Spinner) findViewById(R.id.Emotion);

        String[] items = new String[]{"anger", "confusion", "disgust", "fear", "happiness", "sadness", "shame", "surprise"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                EmotionText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CreateMoodActivity.this, "Please pick a feeling!", Toast.LENGTH_SHORT).show();
            }
        });


        Spinner dropdown_SocialSituation = (Spinner) findViewById(R.id.SocialSituation);
        String[] item_SocialSituation = new String[]{"", "alone", "with one other person",
                "with two people", "with several people", "with a crowd"};
        ArrayAdapter<String> adapter_SocialSituation = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, item_SocialSituation);
        dropdown_SocialSituation.setAdapter(adapter_SocialSituation);

        dropdown_SocialSituation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                SocialSituation = parent.getItemAtPosition(position).toString();
                TextView sizeView = (TextView) findViewById(R.id.SocialText);
                sizeView.setText(SocialSituation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Description = (EditText) findViewById(R.id.Description);

        mImageView = (ImageView) findViewById(R.id.editImageView);

        ImageButton chooseButton = (ImageButton) findViewById(R.id.Camera);

        ImageButton locationButton = (ImageButton) findViewById(R.id.location);


        chooseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, 1);
                } catch (Exception e) {
                    Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
                    startActivity(intent);
                }
            }
        });


        chooseButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                try {
                    Intent intent = new Intent("android.intent.action.PICK");
                    intent.setType("image/*");
                    startActivityForResult(intent, 0);
                } catch (Exception e) {
                    Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });


        locationButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                locationText = (TextView) findViewById(R.id.locationText);
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //check available tools
                List<String> locationList = locationManager.getProviders(true);
                if (locationList.contains(LocationManager.GPS_PROVIDER)) {
                    provider = LocationManager.GPS_PROVIDER;
                } else if (locationList.contains(LocationManager.NETWORK_PROVIDER)) {
                    provider = LocationManager.NETWORK_PROVIDER;
                } else {
                    Toast.makeText(getApplicationContext(), "No map to use", Toast.LENGTH_LONG).show();
                }

                //check the permission
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    Toast.makeText(getApplicationContext(), "Get location felled, Please check the Permission", Toast.LENGTH_SHORT).show();
                    return;
                }

                location = locationManager.getLastKnownLocation(provider);
                if (location == null) {
                    latitude = 0;
                    longitude = 0;
                } else {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }

                Geocoder gcd = new Geocoder(CreateMoodActivity.this, Locale.getDefault());
                try{
                    List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);

                if (addresses.size() > 0)
                    address = "  " + addresses.get(0).getFeatureName() + " " +
                            addresses.get(0).getThoroughfare() + ", " +
                            addresses.get(0).getLocality() + ", " +
                            addresses.get(0).getAdminArea() + ", " +
                            addresses.get(0).getCountryCode();
                    locationText.setText(address);

                    System.out.println(addresses.get(0));}
                catch(Exception e){}

            }
        });



        Button submitButton = (Button) findViewById(R.id.button5);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String moodMessage_text = Description.getText().toString();
                MoodController moodController = new MoodController();
                if(location != null){
                if (moodController.createMood(EmotionText, userName,
                        moodMessage_text, location, bitmap, SocialSituation) == false) {
                    Toast.makeText(CreateMoodActivity.this,
                            "Mood message length is too long. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("loca = " + location);
                    Intent intent = new Intent(CreateMoodActivity.this, TimelineActivity.class);
                    startActivity(intent);
                }}
                else{
                    if (moodController.createMood(EmotionText, userName,
                            moodMessage_text, null, bitmap, SocialSituation) == false) {
                        Toast.makeText(CreateMoodActivity.this,
                                "Mood message length is too long. Please try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(CreateMoodActivity.this, TimelineActivity.class);
                        startActivity(intent);
                    }}

            }
        });


    }


    /**
     * Method handles user interface response to when a user adds an image to their mood
     * from either their camera or their gallery.
     *
     * Knowledge and logic of onActivityResult referenced and taken from
     * link: http://blog.csdn.net/AndroidStudioo/article/details/52077597
     * author: AndroidStudio 2016-07-31 11:15
     * taken by Xin Huang 2017-03-04 15:30
     * @param requestCode          integer indicating the kind of action taken by the user
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            finish();   //no data return
        }
        if (requestCode == 0) {
            //get pic from local photo
            try {
                bitmap = data.getParcelableExtra("data");
                if (bitmap == null) {//if pic is not so big use original one
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        bitmap = BitmapFactory.decodeStream(inputStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (RuntimeException e) {
                Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
                startActivity(intent);
            }

        } else if (requestCode == 1) {
            try {
                bitmap = (Bitmap) data.getExtras().get("data");
                System.out.println("photosize = " + bitmap.getByteCount());
            } catch (Exception e) {
                Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
                startActivity(intent);

            }


        } else if (resultCode == Activity.RESULT_CANCELED) {
            try {
                System.out.println("test for ccamere" + data.getExtras().get("data"));
                Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
                startActivity(intent);
            } catch (RuntimeException e) {
            }

        }
        mImageView.setImageBitmap(bitmap);


    }

    @Override
    public void onLocationChanged(Location location) {
        locationText = (TextView) findViewById(R.id.locationText);
        locationText.setText("Latitude:" + location.getLatitude() + ",Longitude:" + location.getLongitude());

        Log.e("Map", "Location changed : Lat: " + location.getLatitude()
                + " Lng: " + location.getLongitude());
        System.out.printf("this is loc ");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "disable");

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "disable");

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }


}
