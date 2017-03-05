package com.cmput301w17t07.moody;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

/**
 * Created by mike on 2017-03-04.
 */

/* Based on structure from LonelyTwitter. Superclass for other elastic controllers */

public class ElasticController {
    protected static JestDroidClient client;

    protected static void verifySettings() {
        if (client == null) {
<<<<<<< HEAD
            DroidClientConfig.Builder builder =
                    new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
=======
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/");
>>>>>>> 86445c274da3f8e48db3e2e73b20f57035fd38c8
            DroidClientConfig config = builder.build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
