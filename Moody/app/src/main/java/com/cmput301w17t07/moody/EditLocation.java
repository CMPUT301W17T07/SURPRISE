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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class EditLocation extends AppCompatActivity  implements OnMapReadyCallback {
    public Mood editMood;
    private GoogleMap mMap;
    public double newLatitude;
    public double newLongitude;
    public LatLng mMarkerPosition;
    public Bitmap bitmap;
    public String address;
    public int deleteImage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        editMood = (Mood) intent.getSerializableExtra("EditMood");
        bitmap = (Bitmap) intent.getParcelableExtra("bitmap");
        System.out.println("bitmap:"+ bitmap);
        setContentView(R.layout.activity_edit_location);
        newLatitude = editMood.getLatitude();
        newLongitude = editMood.getLongitude();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.edit_map);
        mapFragment.getMapAsync(this);
        Button OKButton = (Button) findViewById(R.id.OK);
        OKButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editMood.setLatitude(newLatitude);
                editMood.setLongitude(newLongitude);
                Intent editLocation = new Intent(EditLocation.this,EditMoodActivity.class);
                editLocation.putExtra("editMood", editMood);
                if(bitmap == null){
                    deleteImage = 1;
                }
                else{
                    editLocation.putExtra("bitmapback",bitmap);}
                editLocation.putExtra("bitmapdelete",deleteImage);
                System.out.println("Bitmapdelete = " + deleteImage);
                startActivity(editLocation);
                finish();
            }
        });


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng tmp = new LatLng(editMood.getLatitude(), editMood.getLongitude());
        mMap.addMarker(new MarkerOptions().draggable(true).position(tmp).title("Select location").icon(BitmapDescriptorFactory.defaultMarker()));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(tmp));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mMarkerPosition = marker.getPosition();
                newLatitude = mMarkerPosition.latitude;
                newLongitude = mMarkerPosition.longitude;
                Geocoder gcd = new Geocoder(EditLocation.this, Locale.getDefault());
                try{
                    List<Address> addresses = gcd.getFromLocation(newLatitude, newLongitude, 1);

                    if (addresses.size() > 0)
                        address = "  " + addresses.get(0).getFeatureName() + " " +
                                addresses.get(0).getThoroughfare() + ", " +
                                addresses.get(0).getLocality() + ", " +
                                addresses.get(0).getAdminArea() + ", " +
                                addresses.get(0).getCountryCode();
                    editMood.setDisplayLocation(address);}
                catch(Exception e){
                    e.printStackTrace();
                }
                System.out.println("Position: " + newLongitude);
            }
        });


    }
}
