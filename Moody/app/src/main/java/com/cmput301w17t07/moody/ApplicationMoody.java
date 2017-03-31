
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

import android.app.Application;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by anicn on 2017-03-08.
 */

public class ApplicationMoody extends Application {
    public static final String FILENAME = "users";
    public static final String FOLLOWERS = "followers";
    public static final String FOLLOWING = "following";
    public static final String PENDING = "pending";

}
