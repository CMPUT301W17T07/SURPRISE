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


public class ElasticSearchMoodyController extends ElasticController{

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

                } catch (Exception e) {
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
                query="{\"from\":0,\"size\":2}";

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


}


