package com.cmput301w17t07.moody;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by mike on 2017-02-23.
 */

public class MoodController {

    public class ElasticSearchMoodyController {

        //private static JestDroidClient client;

        public class AddMood extends AsyncTask<Mood, Void, Void> {
            @Override
            protected Void doInBackground(Mood... params) {
                return null;
            }
        }

        public class GetMood extends AsyncTask<String, Void, ArrayList<Mood>> {
            @Override
            protected ArrayList<Mood> doInBackground(String... params) {
                verifySettings();
                return null;
            }
        }

        public void verifySettings() {

        }
    }
}
