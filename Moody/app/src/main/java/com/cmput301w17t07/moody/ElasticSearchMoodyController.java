package com.cmput301w17t07.moody;


import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by mike on 2017-02-23.
 */

public class ElasticSearchMoodyController {

    //private static JestDroidClient client;

    public static class AddUser extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... params) {
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

    public static void verifySettings() {

    }
 }
