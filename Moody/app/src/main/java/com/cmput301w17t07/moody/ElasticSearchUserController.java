package com.cmput301w17t07.moody;

import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by mike on 2017-02-23.
 */


public class ElasticSearchUserController extends ElasticController{

    /**
     * The AddUser nested class of ElasticSearchMoody controller. Allows for new users to be added
     * to the database.
     */
    public static class AddUser extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {

            verifySettings();

            for (User user : users) {
                Index index = new Index.Builder(user).index("cmput301w17t07").type("user").build();

                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        user.setId(result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was unable to add the user");
                    }

                } catch (IOException e) {
                    Log.i("Error", "The application failed to add the user");
                }

            }

            return null;
        }
    }

    public static class GetUser extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... params) {
            verifySettings();


            ArrayList<User> users = new ArrayList<User>();
            String query;
            if (params[0]==""){
                query="{\"from\":0,\"size\":20}";

            }else {
                query = "{\n" +
                        "    \"query\" : {\n" +
                        "        \"term\" : { \"username\" :\"" + params[0] + "\" }\n" +
                        "    }\n" +
                        "}";
                System.out.println("This is query" + query);
            }
            // TODO Build the query
            Search search=new Search.Builder(query)
                    .addIndex("cmput301w17t07")
                    .addType("user")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result=client.execute(search);
                if (result.isSucceeded()){
                    List<User> foundUsers=result.getSourceAsObjectList(User.class);
                    users.addAll(foundUsers);
                }else {
                    Log.i("erroe","the search quary failed to find any tweet that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            System.out.println("this is user"+users);
            return users;

        }
    }

    /**
     * Class for identifying if a user's desired username is unique before allocating it to him or her
     */
    public static class UniqueUsername extends AsyncTask<String, Void, Boolean> {
        // http://stackoverflow.com/questions/33616123/asynctask-doinbackgroundstring-clashes-with-doinbackgroundparams
        // reference for how to return type Boolean while using async task
        Boolean uniqueFlag;

        @Override
        protected Boolean doInBackground(String... search_parameters) {
            verifySettings();

            String query;

            // search query for identical username
            query = "{\n" +
                    "    \"query\" : {\n" +
                    "        \"term\" : { \"username\" :\"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}";
            System.out.println("this is query" + query);


            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t07")
                    .addType("user").build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    // if username is not unique
                    //todo for some reason it always succeeds
                    if(result.getTotal()  >= 1) {
                        uniqueFlag = false;
                        System.out.println("should NOT be unique" + result.getTotal());
                    }
                    else{
                        // if username is not found
                        uniqueFlag = true;
                        System.out.println("should be unique" + result.getTotal());


                    }

                }
                else {
                    uniqueFlag = false;
                    System.out.println("something happened here");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            // should not reach this point
//            return uniqueFlag;
            return uniqueFlag;

        }

    }


}


