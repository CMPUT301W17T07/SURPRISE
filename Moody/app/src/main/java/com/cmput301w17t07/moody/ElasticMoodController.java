package com.cmput301w17t07.moody;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by mike on 2017-03-05.
 */

public class ElasticMoodController extends ElasticController {


    public static class AddMood extends AsyncTask<Mood, Void, Void> {

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

    public static class GetFilterMoods extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            String query;
            //search param 0 = feeling, 1 = date, 2 = message (FOR NOW)
            if ((search_parameters[0]=="") && (search_parameters[1]=="") && search_parameters[2]==""){
                query="{\"from\":0,\"size\":100}"; // CHANGE SIZE and NOT sure if this is what we will want
            }else {
                query = "{\n" +
                        "    \"query\" : {\n" +
                        "        \"term\" : { \"feeling\" :\"" + search_parameters[0] + "\" }\n" +
                        "    }\n" +
                        "}";
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

    public static class GetUserMoods extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            String query;
            //search param 0 = username FOR NOW
            if (search_parameters[0]==""){
                query="{\"from\":0,\"size\":10}"; // CHANGE SIZE and NOT sure if this is what we will want
            }else {
                query = "{\n" +
                        "    \"query\" : {\n" +
                        "        \"term\" : { \"username\" :\"" + search_parameters[0] + "\" }\n" +
                        "    }\n" +
                        "}";
                System.out.println("this is query" + query);
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

}
