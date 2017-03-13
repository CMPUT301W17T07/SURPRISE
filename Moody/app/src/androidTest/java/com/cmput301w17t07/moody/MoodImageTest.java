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



import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Panchy on 2017/3/12.
 */

/**
 * Tests for the MoodImage model class.
 */
public class MoodImageTest {


    @Test
    public void testgetId() throws Exception {
        MoodImage moodImage=new MoodImage();
        moodImage.setId("2");
        assertEquals(moodImage.getId(),"2");

    }

    @Test
    public void testsetId() throws Exception {
        MoodImage moodImage=new MoodImage();
        moodImage.setId("2");
        moodImage.setId("3");
        assertEquals(moodImage.getId(),"3");


    }
    @Test
    public void testdecodeImage() throws Exception {
        MoodImage moodImage=new MoodImage();
        assertNull(moodImage.decodeImage());

    }

    @Test
    public void testencodeImage() throws Exception {

        Bitmap mPicture = null;
        mPicture = Bitmap.createBitmap(10,10 ,Bitmap.Config.ARGB_8888);
        if (mPicture==null){
            assertNull(mPicture);
        }else {
            MoodImage moodImage = new MoodImage();
            String string = moodImage.encodeImage(mPicture);
            assertNotNull(string);
        }

    }


}