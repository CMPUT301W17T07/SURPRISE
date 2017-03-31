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

import android.os.AsyncTask;
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

/**
 * ElasticSearchUserController class for the Moody application. ElasticMoodController is the communicator
 * between our application and our server for all user related tasks.
 */

public class ElasticSearchUserController extends ElasticController{

    /**
     * The AddUser nested class of ElasticSearchUserController. Allows for new users to be added
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

    /**
     * The GetUser nested class. Allows a user to be returned from the server
     */
    public static class GetUser extends AsyncTask<String, Void, ArrayList<User>> {
        /**
         * doInBackground returns a user from the server
         * @param params            username is passed in
         * @return users             a user object is returned
         */
        @Override
        protected ArrayList<User> doInBackground(String... params) {
            verifySettings();


            ArrayList<User> users = new ArrayList<>();
            String query;
            if (params[0].equals("")){
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
                    Log.i("error","the search query failed to find any tweets that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            //System.out.println("this is user"+users);
            return users;

        }
    }

    /**
     * Nested class for identifying if a user's desired username is unique before allocating it to him or her
     */
    public static class UniqueUsername extends AsyncTask<String, Void, Boolean> {
        Boolean uniqueFlag;

        /**
         * doInBackground returns a boolean value indicating whether a user's desired username is
         * unique. This lets the user know if he or she can have his/her desired username
         *
         * Knowledge of how to return a boolean value while using async task is from:
         * link: http://stackoverflow.com/questions/33616123/asynctask-doinbackgroundstring-clashes-with-doinbackgroundparams
         * Author: prashant Nov 9 '15 at 19:01
         * Referenced by: Michael Simion 2017/03/5
         * @param search_parameters             desired username
         * @return uniqueFlag                   Boolean value indicating whether unique (true = unique)
         */
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


