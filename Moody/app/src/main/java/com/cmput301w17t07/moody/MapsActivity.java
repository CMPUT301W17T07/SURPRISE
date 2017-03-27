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
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends BarMenuActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String username;
    private String filterFeeling;
    private Intent intent;
    private ArrayList<Mood> moodArrayList = new ArrayList<Mood>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMenuBar(this);

        UserController userController = new UserController();
        username = userController.readUsername(MapsActivity.this).toString();

        intent = getIntent();

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

        filterFeeling = intent.getStringExtra("feelingFilter");
        Toast.makeText(MapsActivity.this, filterFeeling, Toast.LENGTH_SHORT).show();
        ElasticMoodController.GetFeelingFilterMoods getFeelingFilterMoods =
                new ElasticMoodController.GetFeelingFilterMoods();
        getFeelingFilterMoods.execute(username, filterFeeling);

        try {
            moodArrayList= getFeelingFilterMoods.get();
            System.out.println("this is moodlist " + moodArrayList);

        }catch (Exception e){
            Log.i("error","failed to get the mood out of the async matched");
        }

        for (int i = 0; i < moodArrayList.size(); i++) {
            if (moodArrayList.get(i).getLocation() == null) {
                break;
            }
            else {
                double longitude;
                double latitude;
                longitude = moodArrayList.get(i).getLocation().getLongitude();
                latitude = moodArrayList.get(i).getLocation().getLatitude();
                LatLng tmp = new LatLng(latitude,longitude);
                mMap.addMarker(new MarkerOptions().position(tmp).title(filterFeeling));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(tmp));
            }

        }



//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//        LatLng uofa = new LatLng(-122.084, 37.422);
//        mMap.addMarker(new MarkerOptions().position(uofa).title("Marker in UofA"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(uofa));
    }
}
