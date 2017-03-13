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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import io.searchbox.annotations.JestId;

/**
 * Created by mike on 2017-03-11.
 */

/**
 * The MoodImage model class for the Moody application. The MoodImage is a class object used to
 * store user's mood image data efficiently on the database.
 */

public class MoodImage {

    private String encodedImage;
    private String moodID;


    @JestId

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.moodID = id;
    }

    /**
     * This method decodes the encodedImage string that is stored on the Moody application's server
     * and allows the image to be displayed from the server in the ViewMoodActivity.
     * @return bitmap       The decoded bitmap image from the server
     */
    public Bitmap decodeImage() {
        Bitmap bitmap;
        bitmap = null;
        if(encodedImage != null){
            byte[]bitmapArray = null;
            bitmapArray= Base64.decode(encodedImage, Base64.URL_SAFE);
            bitmap= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        }
        return bitmap;
    }



    /**
     * This method encodes and compresses a user's supplied bitmap image for appropriate storage
     * on the application's server. Images stored on the server are all under 65536 bytes.
     *
     * Logic for compression of the image is from:
     * link: http://blog.csdn.net/harryweasley/article/details/51955467
     * Author: Harry Weasley
     * Taken by: Xin Huang 2017/03/11
     *
     * Logic for encoding of image string was based upon:
     * link: http://stackoverflow.com/questions/12796579/how-to-send-image-bitmap-to-server-in-android-with-multipart-form-data-json
     * Author: Carnal
     * Taken by: Michael Simion 2017/03/10
     * @param moodImage         The user's supplied image for his or her mood
     * @return encodedImage     The encodedImage string that will be stored within the object on the server
     */
    public String encodeImage(Bitmap moodImage){
        if(moodImage == null){
            return null;
        }

        System.out.println("Image size is too big! " + ((moodImage.getRowBytes() * moodImage.getHeight()) / 8));
        try {
            // Compression of image. From: http://blog.csdn.net/harryweasley/article/details/51955467
            // for compressing the image to meet the project storage requirements
            while (((moodImage.getRowBytes() * moodImage.getHeight()) / 8) > 65536) {
                System.out.println("Image size is too big! " + ((moodImage.getRowBytes() * moodImage.getHeight()) / 8));

                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inPreferredConfig = Bitmap.Config.RGB_565;

                Matrix matrix = new Matrix();
                matrix.setScale(0.5f, 0.5f);
                moodImage = Bitmap.createBitmap(moodImage, 0, 0, moodImage.getWidth(),
                        moodImage.getHeight(), matrix, true);

                System.out.println("Image size is too big! " + ((moodImage.getRowBytes() * moodImage.getHeight()) / 8));

            }
        } catch (Exception E){

        }
        // Encoding of Image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        moodImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes,Base64.URL_SAFE);
        return encodedImage;

    }

    public String getmoodID(){
        return this.moodID;
    }


}
