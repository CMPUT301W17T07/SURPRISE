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