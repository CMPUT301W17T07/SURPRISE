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
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends BarMenuActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String username;
    private String filterFeeling;
    private Intent intent;
    private ArrayList<Mood> moodArrayList = new ArrayList<Mood>();

    private String selectedUser; // equals My Moods/Timeline Moods
    private Integer user; // user = 0 if My Moods else user = 1 if Timeline Moods
    ArrayList nameList = new ArrayList();

    private LocationManager locationManager;
    private double latitude, longitude;
    private Location location;
    private String provider;
    private ArrayList<Mood> currLocationArrayList = new ArrayList<Mood>();
    private ArrayList<Mood> currLocationArrayListWith5Km = new ArrayList<Mood>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMenuBar(this);

        AchievementManager.initManager(MapsActivity.this);
        Achievements achievements = AchievementController.getAchievements();
        achievements.launchMapsFlag = 1;
        AchievementController.checkForMoodAchievements(MapsActivity.this);
        AchievementController.saveAchievements();


        UserController userController = new UserController();
        username = userController.readUsername(MapsActivity.this).toString();

        intent = getIntent();

        selectedUser = intent.getStringExtra("selectedUser");
        if (selectedUser.equals("My Moods")) {
            user = 0;
        } else if (selectedUser.equals("Timeline Moods")) {
            user = 1;
        } else {
            user = 2;
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //check the permission
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Toast.makeText(getApplicationContext(), "Unable to access location." +
                    " Please check your permissions", Toast.LENGTH_SHORT).show();
            return;
        }

        filterFeeling = intent.getStringExtra("feelingFilter");
        //------------------------- MAP FILTERS FOR USER'S MOODS -----------------------------------
        if (user == 0) {

            if(filterFeeling.equals("all")){
                try {
                    moodArrayList = MoodController.getUserMoods(username,
                            String.valueOf(0), MapsActivity.this, false, String.valueOf(100));
                } catch (Exception e){
                    System.out.println("Error when trying to retrieve user's " +
                            "location based mood history for all feelings" + e);
                }
            }
            else {
                ElasticMoodController.GetFeelingFilterMoods getFeelingFilterMoods =
                        new ElasticMoodController.GetFeelingFilterMoods();
                getFeelingFilterMoods.execute(username, filterFeeling);

                try {
                    moodArrayList = getFeelingFilterMoods.get();
                } catch (Exception e) {
                    Log.i("error", "failed to get filtered feeling moods in map activity");
                }
            }
            // Plotting of location based points on map
            for (int i = 0; i < moodArrayList.size(); i++) {
                Mood mood = moodArrayList.get(i);
                if (mood.getLongitude() == 0 && mood.getLatitude() == 0) {
                    break;
                } else {
                    double longitude;
                    double latitude;
                    longitude = moodArrayList.get(i).getLongitude();
                    latitude = moodArrayList.get(i).getLatitude();
                    LatLng tmp = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(tmp).title(mood.getFeeling()).
                            icon(BitmapDescriptorFactory.
                                    defaultMarker(setMarkerColor(mood.getFeeling()))));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(tmp));
                }
            }

        }
        //--------------------- MAP FILTERS FOR USER'S TIMELINE'S MOODS ----------------------------
        else if (user == 1) {
            FollowController followController = new FollowController();
            FollowingList followingList = followController.getFollowingList(username);

            if(filterFeeling.equals("all")){
                try {
                    moodArrayList = MoodController.getTimelineMoods(username,
                            String.valueOf(0), MapsActivity.this);
                } catch(Exception e){
                    System.out.println("Error with getting timeline mood in MapsActivity" + e);

                }
            }
            else {

                nameList.addAll(followingList.getFollowingList());
                try {
                    for (int i = 0; i < nameList.size(); i++) {

                        ElasticMoodController.GetFeelingFilterMoods getFeelingFilterMoods =
                                new ElasticMoodController.GetFeelingFilterMoods();
                        getFeelingFilterMoods.execute(nameList.get(i).toString(), filterFeeling);

                        try {
                            moodArrayList.addAll(getFeelingFilterMoods.get());

                        } catch (Exception e) {
                            System.out.println("Error with getting filtered" +
                                    " timeline moods in MapsActivity" + e);
                        }
                    }
                } catch (Exception e) {
                }
            }

                    for (int j = 0; j < moodArrayList.size(); j++) {
                        Mood mood = moodArrayList.get(j);
                        if (mood.getLongitude() == 0 && mood.getLatitude() == 0) {
                            break;
                        } else {
                            double longitude;
                            double latitude;
                            longitude = mood.getLongitude();
                            latitude = mood.getLatitude();
                            Toast.makeText(MapsActivity.this, "" + longitude, Toast.LENGTH_SHORT).show();
                            LatLng tmp = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(tmp).
                                    title(mood.getDisplayUsername()).
                                    snippet(mood.getFeeling()).
                                    icon(BitmapDescriptorFactory.
                                            defaultMarker(setMarkerColor(mood.getFeeling()))));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(tmp));

                        }
                    }


        }
        //--------------------------- ALL MOODS WITHIN 5KM OF THE USER -----------------------------
        else if (user == 2) {

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


            location = locationManager.getLastKnownLocation(provider);
            if (location == null) {
                latitude = 0;
                longitude = 0;
            } else {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            UserController userController = new UserController();
            username = userController.readUsername(MapsActivity.this).toString();

            ElasticMoodController.FilterMapByLocation filterMapByLocation =
                    new ElasticMoodController.FilterMapByLocation();
            filterMapByLocation.execute(location);


            try {
                currLocationArrayList.addAll(filterMapByLocation.get());
            } catch (Exception e) {
                System.out.println("this is fff" + e);
            }

            for (int p=0;p< currLocationArrayList.size();p++){
                Location locationNear=new Location("near");
                locationNear.setLatitude(currLocationArrayList.get(p).getLatitude());
                locationNear.setLongitude(currLocationArrayList.get(p).getLongitude());
                float distance=location.distanceTo(locationNear);
                if (distance<=5000.0) {
                    currLocationArrayListWith5Km.add(currLocationArrayList.get(p));
                }

            }

            for (int j = 0; j < currLocationArrayListWith5Km.size(); j++) {
                Mood mood = currLocationArrayListWith5Km.get(j);
                if (mood.getLongitude() == 0 && mood.getLatitude() == 0) {
                    break;
                } else {
                    double longitude;
                    double latitude;
                    longitude = mood.getLongitude();
                    latitude = mood.getLatitude();
                    LatLng tmp = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(tmp).
                            title(mood.getDisplayUsername()).snippet(mood.getFeeling()).
                            icon(BitmapDescriptorFactory.
                                    defaultMarker(setMarkerColor(mood.getFeeling()))));
                    float zoomLevel = 12.0f;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tmp, zoomLevel));
                }
            }

        }

    }


    public Float setMarkerColor(String feeling) {
        double d = 0.0;
        float markerColor = (float) d;
        switch (feeling) {
            case "anger":
                markerColor = BitmapDescriptorFactory.HUE_RED;
                break;
            case "confusion":
                markerColor = BitmapDescriptorFactory.HUE_ORANGE;
                break;
            case "disgust":
                markerColor = BitmapDescriptorFactory.HUE_GREEN;
                break;
            case "fear":
                markerColor = BitmapDescriptorFactory.HUE_CYAN;
                break;
            case "happiness":
                markerColor = BitmapDescriptorFactory.HUE_YELLOW;
                break;
            case "sadness":
                markerColor = BitmapDescriptorFactory.HUE_AZURE;
                break;
            case "shame":
                markerColor = BitmapDescriptorFactory.HUE_ROSE;
                break;
            case "surprise":
                markerColor = BitmapDescriptorFactory.HUE_VIOLET;
                break;
        }
        return markerColor;

    }
}