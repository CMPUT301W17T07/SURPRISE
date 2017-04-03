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

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

/* Based on structure from LonelyTwitter. Superclass for other elastic controllers */

/**
 * ElasticController superclass for the other Elastic Controller objects in our application.
 * Structure of class is based on what we learned in theLonelyTwitter application from our CMPUT
 * 301 lab. Initial forking of LonelyTwitter is from: <br>
 * link: https://github.com/joshua2ua/lonelyTwitter <br>
 * author: Joshua Charles Campbell <br>
 *
 * ElasticController utilizes JestDroid library. Link to library is here: <br>
 * https://github.com/searchbox-io/Jest/blob/master/jest/README.md <br>
 */

public class ElasticController {
    protected static JestDroidClient client;
    static boolean connectionFlag = false;

    protected static void verifySettings() {
        if (client == null || connectionFlag) {
            createElasticClient();
        }
    }

    private static void createElasticClient(){
        DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/");
        DroidClientConfig config = builder.build();
        JestClientFactory factory = new JestClientFactory();
        factory.setDroidClientConfig(config);
        client = (JestDroidClient) factory.getObject();
    }

}
