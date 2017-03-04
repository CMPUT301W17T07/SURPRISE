package com.cmput301w17t07.moody;

/**
 * Created by mike on 2017-03-04.
 */

public class ElasticController {
    protected static JestDroidClient client;

    /** Sets up the client to be used for Elastic Search */
    protected static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder =
                    new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
