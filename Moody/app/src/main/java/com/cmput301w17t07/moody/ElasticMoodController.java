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
         * doInBackground function that adds mood to server. <br>
         * @param moods     Mood to be added to server <br>
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
     * The GetFeelingFilterMoods nested class object. The class returns a filtered moodList of the
     * user's moods, based on feeling, to the FilterResultsActivity
     *
     */
    public static class GetFeelingFilterMoods extends AsyncTask<String, Void, ArrayList<Mood>> {
        /**
         * doInBackground returns the filtered list of moods. <br>
         * @param search_parameters       the username and desired feeling are passed in <br>
         * @return moods                  the filtered list of moods <br>
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
                        "}}," +
                        "     \"sort\" : {\n" +
                        "      \"date\"  : {\"order\" : \"desc\" }}\n" +
                        "    }"+
                        "}";
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
         * doInBackground returns the filtered list of moods. <br>
         * @param search_parameters    Username and desired filter word are passed in <br>
         * @return moods               A list of the filtered moods <br>
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
                        "}}," +
                        "     \"sort\" : {\n" +
                        "      \"date\"  : {\"order\" : \"desc\" }}\n" +
                        "    }"+
                        "}";
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
     * The GetRecentWeekUserMoods nested class object. Used to return a moodHistory of followers
     * on his or her timeline .
     */

    public static class GetRecentWeekUserMoods extends AsyncTask<String, Void, ArrayList<Mood>> {
        /**
         * doInBackground returns the filtered list of moods. <br>
         * @param search_parameters    Username and desired filter word are passed in <br>
         * @return moods               A list of the filtered moods <br>
         */
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
     * The GetUserMoods nested class object. Used to return a user's moodHistory on his or her
     * profile page.
     */
    public static class GetUserMoods extends AsyncTask<String, Void, ArrayList<Mood>> {
        /**
         * doInBackground returns a list of moods for a particular user, sorted by date with
         * the most recent moods coming first. <br>
         * @param search_parameters         username is passed in <br>
         * @return moods                    list of the user's moods <br>
         */
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            String query;
            //search param 0 = username FOR NOW
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
     * Nested DeleteMood class object. Used to delete a mood from the server.
     */
    public static class DeleteMood extends AsyncTask<String, Void, Void> {
        /**
         * doInBackground deletes the desired mood from the server <br>
         * @param search_parameters        unique server ID of the mood to be deleted <br>
         * @return null <br>
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
            try {
                client.execute(delete);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Nested FilterMapByLocation class object. Used to find all moods within 5km of me.
     */
    public static class FilterMapByLocation extends AsyncTask<Location, Void, ArrayList<Mood>> {
        /**
         * doInBackground finds moods within 5km of my current location <br>
         * @param locations        unique server ID of the moods posted by me <br>
         * @return null <br>
         */
        @Override
        protected ArrayList<Mood> doInBackground(Location... locations) {
            verifySettings();

            String query = "{\n"+
                    "\"from\":0,\"size\":100000,\n"+
                    "\"query\" : {\n" +
                    "\"filtered\": {\n" +
                    "         \"query\" : {\n" +
                    "             \"match_all\" : {}\n" +
                    "  },\n" +
                    "\"filter\":{ \n"+

                    "       }\n" +
                    "   }\n"+
                    "  }\n" +
                    "}";

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

}
