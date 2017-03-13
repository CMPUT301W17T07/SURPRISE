package com.cmput301w17t07.moody;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

/**
 * Created by mike on 2017-03-04.
 */

/* Based on structure from LonelyTwitter. Superclass for other elastic controllers */

/**
 * ElasticController superclass for the other Elastic Controller objects in our application.
 * Structure of class is based on what we learned in theLonelyTwitter application from our CMPUT
 * 301 lab. Initial forking of LonelyTwitter is from:
 * link: https://github.com/joshua2ua/lonelyTwitter
 * author: Joshua Charles Campbell
 *
 * ElasticController utilizes JestDroid library. Link to library is here:
 * https://github.com/searchbox-io/Jest/blob/master/jest/README.md
 */

public class ElasticController {
    protected static JestDroidClient client;

    protected static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/");
            DroidClientConfig config = builder.build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
