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

import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by mike on 2017-03-25.
 */

/**
 * ElasticSearch controller for
 */
public class ElasticSearchFollowController extends ElasticMoodController {

    public static class AddFollowingList extends AsyncTask<FollowingList, Void, Void> {

        @Override
        protected Void doInBackground(FollowingList... followingLists) {
            verifySettings();

            for(FollowingList followingList: followingLists){
                Index index = new Index.Builder(followingList).index("cmput301w17t07").type("followinglist").build();
                try {

                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()) {
                        followingList.setId(result.getId());
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

    public static class AddFollowerList extends AsyncTask<FollowerList, Void, Void> {

        @Override
        protected Void doInBackground(FollowerList... followerLists) {
            verifySettings();

            for(FollowerList followerList: followerLists){
                Index index = new Index.Builder(followerList).index("cmput301w17t07").type("followerlist").build();
                try {

                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()) {
                        followerList.setId(result.getId());
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

    public static class GetFollowerList extends AsyncTask<String, Void, FollowerList> {

        @Override
        protected FollowerList doInBackground(String... search_parameters) {
            verifySettings();

            FollowerList followerList = null;

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
            }

            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t07")
                    .addType("followerlist").build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    followerList = result.getSourceAsObject(FollowerList.class);
                }
                else {
                    Log.i("Error", "The search query failed to find any followerlists that matched, buddy");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return followerList;
        }
    }


    public static class GetFollowingList extends AsyncTask<String, Void, FollowingList> {

        @Override
        protected FollowingList doInBackground(String... search_parameters) {
            verifySettings();

            FollowingList followingList = null;

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
            }

            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t07")
                    .addType("followinglist").build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    followingList = result.getSourceAsObject(FollowingList.class);
                }
                else {
                    Log.i("Error", "The search query failed to find any followerlists that matched, buddy");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return followingList;
        }
    }


    public static class DeleteFollowerList extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... search_parameters) {
            verifySettings();

            String search_string = "{\n" +
                    "    \"query\" : {\n" +
                    "        \"term\" : { \"username\" :\"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}";

            DeleteByQuery delete = new DeleteByQuery.Builder(search_string)
                    .addIndex("cmput301w17t07")
                    .addType("followerList")
                    .build();

            try {
                client.execute(delete);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class DeleteFollowingList extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... search_parameters) {
            verifySettings();

            String search_string = "{\n" +
                    "    \"query\" : {\n" +
                    "        \"term\" : { \"username\" :\"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}";

            DeleteByQuery delete = new DeleteByQuery.Builder(search_string)
                    .addIndex("cmput301w17t07")
                    .addType("followinglist")
                    .build();

            try {
                client.execute(delete);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }



}
