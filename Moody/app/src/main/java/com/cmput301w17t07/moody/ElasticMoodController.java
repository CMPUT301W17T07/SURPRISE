package com.cmput301w17t07.moody;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;

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

    public static class AddImage extends AsyncTask<MoodImage, Void, MoodImage> {

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
    // Gets the moodImage associated to its respective mood when clicking to view a mood's details
    public static class GetMoodImage extends AsyncTask<String, Void, MoodImage> {
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

    public static class GetFilterMoods extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            String query;
            //search param 0 = username, 1 = feeling, (FOR NOW)
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
                        "    },\n" +
                        "     \"sort\" : {\n" +
                        "      \"date\"  : {\"order\" : \"desc\" }}\n" +
                        "    }";
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


    public static class DeleteMood extends AsyncTask<String, Void, Void> {

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
