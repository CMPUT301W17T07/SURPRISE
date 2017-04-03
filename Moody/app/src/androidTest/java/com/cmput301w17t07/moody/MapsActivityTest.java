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

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Panchy on 2017/4/2.
 */
public class MapsActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public MapsActivityTest() {
        super(com.cmput301w17t07.moody.SearchUserActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());

    }


    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void onMapReady() throws Exception {
        solo.assertCurrentActivity("Wrong Activity",SearchUserActivity.class);
        solo.clickOnButton("Map");
        solo.pressSpinnerItem(0, 0);

        assertFalse(solo.searchText("test tweet!"));

    }

    @Test
    public void setMarkerColor() throws Exception {

    }

}