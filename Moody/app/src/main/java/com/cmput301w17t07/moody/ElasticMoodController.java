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

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by mike on 2017-03-05.
 */

/**
 * ElasticMoodController class for the Moody application. ElasticMoodController is the communicator
 * between our application and our server for all mood related tasks.
 */

public class ElasticMoodController extends ElasticController {

    /**
     * AddMood nested class object. Used to add Moods to the server under the "mood" type on the
     * server.
     */
    public static class AddMood extends AsyncTask<Mood, Void, Void> {

        /**
         * doInBackground function that adds mood to server.
         * @param moods     Mood to be added to server
         * @return
         */
        @Override
        protected Void doInBackground(Mood... moods) {
            verifySettings();

            for(Mood mood: moods){
                Index index = new Index.Builder(mood).index("cmput301w17t07").type("mood").build();
                try {

                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()) {
                        mood.setId(result.getId());
                    }
                    else{
                        Log.i("Error", "Elasticsearch was not able to build and send the mood");
                    }

                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the mood");
                }

            }
            return null;

        }
    }

    /**
     * AddImage nested class object. Used to add MoodImages to the server under the "image" type.
     */
    public static class AddImage extends AsyncTask<MoodImage, Void, MoodImage> {
        /**
         * doInBackgorund adds the MoodImage to the server. It returns the added moodImage so that
         * it's assigned server ID can be passed to the Mood object that will need to reference it.
         * This allows us to only "load" the image objects once they are viewed.
         *
         * @param moodImages        the moodImage to be added
         * @return moodImage[0]     the added moodImage
         */
        @Override
        protected MoodImage doInBackground(MoodImage... moodImages) {
            verifySettings();

            for(MoodImage moodImage: moodImages){
                Index index = new Index.Builder(moodImage).index("cmput301w17t07").type("image").build();
                try {

                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()) {
                        moodImage.setId(result.getId());
                    }
                    else{
                        Log.i("Error", "Elasticsearch was not able to build and send the mood");
                    }

                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the mood");
                }

            }
            return moodImages[0];

        }
    }

    /**
     * GetMoodImage nested class object. Gets the moodImage associated to its respective mood
     * when clicking to view a mood's details.
     */
    public static class GetMoodImage extends AsyncTask<String, Void, MoodImage> {
        /**
         * doInBackground retrieves the moodImage from the server.
         * @param search_parameters     the unique ID that identifies the image on the server
         * @return moodImage            the MoodImage associated to the mood object
         */
        @Override
        protected MoodImage doInBackground(String... search_parameters) {
            verifySettings();

            MoodImage moodImage = null;

            String query;
            //search param 0 = username FOR NOW
            if (search_parameters[0]==""){
                query="{\"from\":0,\"size\":10}"; // CHANGE SIZE and NOT sure if this is what we will want
            }else {
                query = "{\n" +
                        "    \"query\" : {\n" +
                        "        \"term\" : { \"_id\" :\"" + search_parameters[0] + "\" }\n" +
                        "    }\n" +
                        "}";
                System.out.println("this is query" + query);
            }

            // TODO Build the query
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t07")
                    .addType("image").build();


            try {
                // TODO get the results of the query


                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    moodImage = result.getSourceAsObject(MoodImage.class);
                }
                else {
                    Log.i("Error", "The search query failed to find any images that matched, buddy");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server IMAGE!");
            }
            return moodImage;
        }
    }

    /**
     * The GetFeelingFilterMoods nested class object. The class returns a filtered moodList of the
     * user's moods, based on feeling, to the FilterResultsActivity
     *
     */
    public static class GetFeelingFilterMoods extends AsyncTask<String, Void, ArrayList<Mood>> {
        /**
         * doInBackground returns the filtered list of moods.
         * @param search_parameters       the username and desired feeling are passed in
         * @return moods                  the filtered list of moods
         */
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            String query;
            if ((search_parameters[0]=="") && (search_parameters[1]=="") && search_parameters[2]==""){
                query="{\"from\":0,\"size\":100}"; // CHANGE SIZE and NOT sure if this is what we will want
            }else {
                query = "{\"query\":\n" +
                        "{\"bool\":\n" +
                        " {\"must\": [\n" +
                        "{\"term\": {\"username\": \""+ search_parameters[0] +"\"}},\n" +
                        "{\"term\": {\"feeling\": \""+ search_parameters[1] +"\"}}\n" +
                        "]\n" +
                        "}}}";
                System.out.println("this is query" + query);
            }

            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t07")
                    .addType("mood").build();

            try {


                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<Mood> foundMoods = result.getSourceAsObjectList(Mood.class);
                    moods.addAll(foundMoods);
                }
                else {
                    Log.i("Error", "The search query failed to find any tweets that matched, buddy");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return moods;
        }
    }

    /**
     * The GetMessageFilterMoods nested class object. The class returns a filtered moodList of the
     * user's moods, based on a specific word, to the FilterResultsActivity
     *
     */
    public static class GetMessageFilterMoods extends AsyncTask<String, Void, ArrayList<Mood>> {
        /**
         * doInBackground returns the filtered list of moods.
         * @param search_parameters    Username and desired filter word are passed in
         * @return moods               A list of the filtered moods
         */
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            String query;
            if ((search_parameters[0]=="") && (search_parameters[1]=="") && search_parameters[2]==""){
                query="{\"from\":0,\"size\":100}"; // CHANGE SIZE and NOT sure if this is what we will want
            }else {
                query = "{\"query\":\n" +
                        "{\"bool\":\n" +
                        " {\"must\": [\n" +
                        "{\"term\": {\"username\": \""+ search_parameters[0] +"\"}},\n" +
                        "{\"term\": {\"moodMessage\": \""+ search_parameters[1] +"\"}}\n" +
                        "]\n" +
                        "}}}";
                System.out.println("this is query" + query);
            }

            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t07")
                    .addType("mood").build();

            try {


                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<Mood> foundMoods = result.getSourceAsObjectList(Mood.class);
                    moods.addAll(foundMoods);
                }
                else {
                    Log.i("Error", "The search query failed to find any tweets that matched, buddy");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return moods;
        }
    }

    public static class GetRecentWeekUserMoods extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            String query;
            //search param 0 = username FOR NOW
            if (search_parameters[0]==""){
                query="{\"from\":0,\"size\":10}"; // CHANGE SIZE and NOT sure if this is what we will want
            }else {
                query = "{ \"query\" : " +
                            "{ \"filtered\" : " +
                                 "{ \"filter\" : " +
                                    "{ \"term\" : " +
                                        "{\"username\": \"" + search_parameters[0] + "\"}}}}," +
                                    " \"sort\" : " +
                                        "{ \"date\" : " +
                                            "{ \"order\" :" +
                                                " \"desc\"}}," +
                                        " \"filter\" :" +
                                            " {\"range\" :" +
                                                " { \"date\" :" +
                                                    " { \"gte\" : \"now-1w\" }}}}";

                System.out.println("this is recent week query" + query);
            }

            // TODO Build the query
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t07")
                    .addType("mood").build();

            try {
                // TODO get the results of the query


                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<Mood> foundMoods = result.getSourceAsObjectList(Mood.class);
                    moods.addAll(foundMoods);
                }
                else {
                    Log.i("Error", "The search query failed to find any tweets that matched, buddy");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return moods;
        }
    }

    /**
     * The GetUserMoods nested class object. Used to return a user's moodHistory on his or her
     * profile page.
     */
    public static class GetUserMoods extends AsyncTask<String, Void, ArrayList<Mood>> {
        /**
         * doInBackground returns a list of moods for a particular user, sorted by date with
         * the most recent moods coming first.
         * @param search_parameters         username is passed in
         * @return moods                    list of the user's moods
         */
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            String query;
            //search param 0 = username FOR NOW
//            System.out.println("this is fff "+search_parameters[2]);
            if (search_parameters[0]==""){
                query="{\"from\":0,\"size\":10}"; // CHANGE SIZE and NOT sure if this is what we will   want
            }else {
                query = "{\n" +
                        "    \"from\":\"" + search_parameters[1] + "\",\"size\":\"" + search_parameters[2] + "\", \n"+
                        "    \"query\" : {\n" +
                        "        \"term\" : { \"username\" :\"" + search_parameters[0] + "\" }\n" +
                        "    },\n" +
                        "     \"sort\" : {\n" +
                        "      \"date\"  : {\"order\" : \"desc\" }}\n" +
                        "    }";
                System.out.println("this is query "+query);
            }

            // TODO Build the query
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t07")
                    .addType("mood").build();

            try {
                // TODO get the results of the query


                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<Mood> foundMoods = result.getSourceAsObjectList(Mood.class);
                    moods.addAll(foundMoods);
                }
                else {
                    Log.i("Error", "The search query failed to find any tweets that matched, buddy");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return moods;
        }
    }

    /**
     * Nested DeleteMood class object. Used to delete a mood from the server.
     */
    public static class DeleteMood extends AsyncTask<String, Void, Void> {
        /**
         * doInBackground deletes the desired mood from the server
         * @param search_parameters        unique server ID of the mood to be deleted
         * @return null
         */
        @Override
        protected Void doInBackground(String... search_parameters) {
            verifySettings();

            String search_string = "{\n" +
                    "    \"query\" : {\n" +
                    "        \"term\" : { \"_id\" :\"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}";

            DeleteByQuery delete = new DeleteByQuery.Builder(search_string)
                    .addIndex("cmput301w17t07")
                    .addType("mood")
                    .build();

            //todo probably worth trying to see if an associated image could also be deleted here

            try {
                client.execute(delete);
            } catch (Exception e) {
                e.printStackTrace();
//                   throw new IllegalArgumentException();
            }

            return null;
        }
    }

    /**
     * Nested FilterMapByLocation class object. Used to find all moods within 5km of me.
     */
    public static class FilterMapByLocation extends AsyncTask<Location, Void, ArrayList<Mood>> {
        /**
         * doInBackground finds moods within 5km of my current location
         * @param locations        unique server ID of the moods posted by me
         * @return null
         */
        @Override
        protected ArrayList<Mood> doInBackground(Location... locations) {
            verifySettings();

            String DISTANCE = "5";

            String query = "{\n" +
                    "\"query\" : {\n" +
                        "\"match_all\" : {}\n" +
                            "},\n" +
                    "\"filter\" : {\n" +
                        "\"bool\" : {\n" +
                            "\"must\" : [\n" +
                    "{\n" +
                        "\"geo_distance\" : {\n" +
                            "\"distance\" : \"5km\",\n" +
                                "\"random\" : [" + locations[0].getLongitude() + ", "
                                    + locations[0].getLatitude() + "]\n" +
                    "}\n" +
                    "} ,\n" +
                    "] ,\n" +
                    "}\n" +
                    "}\n" +
                    "}";



//                    "{\n" +
//                    "\"query\" : {\n" +
//                        "\"match_all\" : {}\n" +
//                            "},\n" +
//                    "\"filter\" : {\n" +
//                    "        \"geo_distance\" : {\n" +
//                    "          \"distance\" : \"" + DISTANCE + "km\",\n" +
//                    "          \"random\" : [" + locations[0].getLongitude() + ", "
//                    + locations[0].getLatitude() + "]\n" +
//                    "}\n" +
//                    "}\n" +
//                    "}\n";


//                    "{\n" +
//                    "\"query\" : {\n" +
//                    "\"filter\" : {\n" +
//                        "\"geo_distance\" : {\n" +
//                            "\"distance\" : \"5km\",\n" +
//                    "\"pin\" : {\n" +
//                        "\"random\" : [" + locations[0].getLongitude() + ", "
//                    + locations[0].getLatitude() + "]\n" +
//                    "}\n" +
//                    "}\n" +
//                                    "}\n" +
//                                        "}\n" +
//                                            "}\n";
//



//            "\"filtered\" : {\n" +
//                    "\"query\" : {\n" +
//                    "\"match_all\" : {}\n" +
//                    "},\n" +




//                    "{\n" +
//                    "\"query\" : {\n" +
//                    "\"bool\" : {\n" +
//                    "\"must\" : {\n" +
//                    " \"match_all\" : {}\n" +
//                    "}," +
//                    "\"filter\" : {\n" +
//                    "\"geo_distance\" : {\n" +
//                    "\"distance : 5km\",\n" +
//                    "\"location\" : [" + locations[0].getLongitude() + ", "
//                    + locations[0].getLatitude() + "]\n" +
//                    "}}}";

            System.out.println("this is location query "+query);


            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t07")
                    .addType("mood")
                    .build();

            ArrayList<Mood> allLocations = new ArrayList<> ();

            try {

                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<Mood> foundMoods = result.getSourceAsObjectList(Mood.class);
                    allLocations.addAll(foundMoods);
                }
                else {
                    Log.i("Error", "The search query failed to find any tweets that matched, buddy");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return allLocations;
        }

    }




    //todo part 5 implement EditMood nested class, as opposed to relying on add and delete for editing
//    public static class EditMood extends AsyncTask<Mood, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Mood... moods) {
//            verifySettings();
//
//            for (Mood mood : moods ) {
//
//                String query;
//
//
//                Update update = new Update.Builder(query)
//                        .index("cmput301w17t07")
//                        .type("mood")
//                        .id(mood.getId())
//                        .build();
//
//                try {
//                    client.execute( update );
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            return null;
//        }
//    }

}
