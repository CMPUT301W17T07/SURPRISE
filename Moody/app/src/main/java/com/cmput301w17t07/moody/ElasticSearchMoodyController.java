package com.cmput301w17t07.moody;


import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by mike on 2017-02-23.
 */

public class ElasticSearchMoodyController extends ElasticController {


    public static class AddUser extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user: users) {
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
            return null;
        }
    }

 }
