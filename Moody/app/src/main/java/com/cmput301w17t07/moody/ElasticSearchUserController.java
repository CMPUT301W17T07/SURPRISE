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
         * doInBackground returns a user from the server <br>
         * @param params            username is passed in <br>
         * @return users             a user object is returned <br>
         */
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
            }
            Search search=new Search.Builder(query)
                    .addIndex("cmput301w17t07")
                    .addType("user")
                    .build();

            try {
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
         * unique. This lets the user know if he or she can have his/her desired username <br>
         *
         * Knowledge of how to return a boolean value while using async task is from:
         * link: http://stackoverflow.com/questions/33616123/asynctask-doinbackgroundstring-clashes-with-doinbackgroundparams <br>
         * Author: prashant Nov 9 '15 at 19:01 <br>
         * Referenced by: Michael Simion 2017/03/5 <br>
         * @param search_parameters             desired username <br>
         * @return uniqueFlag                   Boolean value indicating whether unique (true = unique) <br>
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

            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t07")
                    .addType("user").build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    // if username is not unique
                    if(result.getTotal()  >= 1) {
                        uniqueFlag = false;
                    }
                    else{
                        // if username is not found
                        uniqueFlag = true;
                    }

                }
                else {
                    uniqueFlag = false;
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


