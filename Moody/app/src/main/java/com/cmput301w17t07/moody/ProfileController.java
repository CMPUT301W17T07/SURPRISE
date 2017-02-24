package com.cmput301w17t07.moody;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by mike on 2017-02-23.
 */

public class ProfileController {
    public class ElasticSearchMoodyController {

        //private static JestDroidClient client;

        public class AddProfile extends AsyncTask<User, Void, Void> {
            @Override
            protected Void doInBackground(User... params) {
                return null;
            }
        }

        public class GetProfile extends AsyncTask<String, Void, ArrayList<User>> {
            @Override
            protected ArrayList<User> doInBackground(String... params) {
                verifySettings();
                return null;
            }
        }

        public void verifySettings() {

        }
    }
}
