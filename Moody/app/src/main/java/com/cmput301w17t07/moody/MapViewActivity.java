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
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.Location;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//import org.osmdroid.api.IMapController;
//import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
//import org.osmdroid.util.GeoPoint;
//import org.osmdroid.views.MapView;
//import org.osmdroid.views.overlay.Overlay;
//import org.osmdroid.views.overlay.OverlayItem;
//import org.osmdroid.views.overlay.Polyline;
//import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;

import java.util.ArrayList;
import java.util.List;




/*
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;
*/


public class MapViewActivity extends BarMenuActivity {

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // Cmput 301 lab 8 based on open resources.
//        // based on https://github.com/MKergall/osmbonuspack/wiki/Tutorial_0
//        // October 25th, 2016
//        // updated with http://stackoverflow.com/questions/38539637/osmbonuspack-roadmanager-networkonmainthreadexception
//        // October 25th, 2016
//        // answered by: yubaraj poudel
//        setContentView(R.layout.activity_map_view);
//        map = (MapView) findViewById(R.id.map);
//        map.setTileSource(TileSourceFactory.MAPNIK);
//        map.setBuiltInZoomControls(true);
//        map.setMultiTouchControls(true);
//
//
//
//        Criteria criteria = new Criteria();
//        /*String bestProvider = locationManager.getBestProvider(criteria, true);
//        Location location = locationManager.getLastKnownLocation(bestProvider);
//        locationManager.requestLocationUpdates(bestProvider, 0, 0, new TestLocationListener());*/
//        startPoint = new GeoPoint(48.13, -1.63);
//        destinationPoint = new GeoPoint(48.4, -1.9);
//
//        IMapController mapController = map.getController();
//        mapController.setZoom(9);
//        mapController.setCenter(startPoint);
//
//        // to get a key http://developer.mapquest.com/
//        //roadManager = new MapQuestRoadManager("--");
//        //roadManager = new OSRMRoadManager(myActivity);
//
//        ArrayList<OverlayItem> overlayItemArray;
//        overlayItemArray = new ArrayList<>();
//
//        overlayItemArray.add(new OverlayItem("Starting Point", "This is the starting point", startPoint));
//        overlayItemArray.add(new OverlayItem("Destination", "This is the detination point", destinationPoint));
//        //getRoadAsync();
//    }
//
//    // Global variables for testing
//    // TODO refactor
//    //RoadManager roadManager;
//    MapView map;
//    Activity myActivity = this;
//    //Road[] mRoads;
//    GeoPoint startPoint;
//    GeoPoint destinationPoint;

    /*public void getRoadAsync() {
        mRoads = null;
        GeoPoint roadStartPoint = startPoint;

        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>(2);
        waypoints.add(roadStartPoint);

        waypoints.add(destinationPoint);
        new UpdateRoadTask().execute(waypoints);
    }

    private class UpdateRoadTask extends AsyncTask<Object, Void, Road[]> {

        protected Road[] doInBackground(Object... params) {
            @SuppressWarnings("unchecked")
            ArrayList<GeoPoint> waypoints = (ArrayList<GeoPoint>) params[0];

            return roadManager.getRoads(waypoints);
        }

        @Override
        protected void onPostExecute(Road[] roads) {
            mRoads = roads;
            if (roads == null)
                return;
            if (roads[0].mStatus == Road.STATUS_TECHNICAL_ISSUE)
                Toast.makeText(map.getContext(), "Technical issue when getting the route", Toast.LENGTH_SHORT).show();
            else if (roads[0].mStatus > Road.STATUS_TECHNICAL_ISSUE) //functional issues
                Toast.makeText(map.getContext(), "No possible route here", Toast.LENGTH_SHORT).show();
            Polyline[] mRoadOverlays = new Polyline[roads.length];
            List<Overlay> mapOverlays = map.getOverlays();
            for (int i = 0; i < roads.length; i++) {
                Polyline roadPolyline = RoadManager.buildRoadOverlay(roads[i]);
                mRoadOverlays[i] = roadPolyline;
                String routeDesc = roads[i].getLengthDurationText(myActivity.getBaseContext(), -1);
                roadPolyline.setTitle(getString(R.string.app_name) + " - " + routeDesc);
                roadPolyline.setInfoWindow(new BasicInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble, map));
                roadPolyline.setRelatedObject(i);

                mapOverlays.add(0, roadPolyline);
                map.invalidate();
                //we insert the road overlays at the "bottom", just above the MapEventsOverlay,
                //to avoid covering the other overlays.
            }
        }
    }*/
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}

