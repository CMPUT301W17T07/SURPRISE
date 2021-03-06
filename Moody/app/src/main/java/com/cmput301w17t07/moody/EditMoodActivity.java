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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * The EditMoodActivity handles the user interface logic for when a user is editing a mood object.
 */
public class EditMoodActivity extends BarMenuActivity {
    public Mood editMood;
    private Bitmap bitmapImage = null;
    private String userName;
    private String EmotionText;
    private String SocialSituation;
    private int deletedPic = 0;
    private EditText Description;
    private TextView locationText;
    private ImageView image;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private String provider;
    private String address;
    private String moodMessage_text;
    private Bitmap editBitmapImage = null;
    Location location1 = new Location(LocationManager.NETWORK_PROVIDER);

    //----------------------------- date parameters ----------------------------
    // parameters for datetimePicker

    AlertDialog TimeDialog;
    AlertDialog DateDialog;
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH);
    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    int currentMinte = calendar.get(Calendar.MINUTE);
    String dateString;
    Date date;

    private Achievements achievements;

    //initialize the data, because user may back from map if they want to edit location
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserController userController = new UserController();
        userName = userController.readUsername(EditMoodActivity.this).toString();
        setContentView(R.layout.activity_edit_mood);
        setUpMenuBar(this);

        // get the mood object that was selected
        Intent intent = getIntent();
        editMood = (Mood) intent.getSerializableExtra("editMood");
        date = editMood.getDate();
        bitmapImage = (Bitmap) intent.getParcelableExtra("bitmapback");
        editBitmapImage = bitmapImage;
        deletedPic = (int) intent.getExtras().getInt("bitmapdelete");
        latitude = editMood.getLatitude();
        longitude = editMood.getLongitude();
        image = (ImageView) findViewById(R.id.editImageView);
        final TextView location = (TextView) findViewById(R.id.locationText);
        address = editMood.getDisplayLocation();
        location.setText(address);

        if (latitude == 0 && longitude == 0) {
            location1 = null;
        }
        displayAttributes();
        if (deletedPic == 1) {
            image.setImageBitmap(null);
        }

        //set up the button and imageButton
        ImageButton editLocation = (ImageButton) findViewById(R.id.location);
        editLocation.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                locationText = (TextView) findViewById(R.id.locationText);
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //check available tools
                List<String> locationList = locationManager.getProviders(true);
                if (locationList.contains(LocationManager.GPS_PROVIDER)) {
                    provider = LocationManager.GPS_PROVIDER;
                } else if (locationList.contains(LocationManager.NETWORK_PROVIDER)) {
                    provider = LocationManager.NETWORK_PROVIDER;
                } else {
                    Toast.makeText(getApplicationContext(), "Please check your permissions", Toast.LENGTH_LONG).show();
                }
                //check the permission
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    Toast.makeText(getApplicationContext(), "Getting location failed, Please check the application permissions", Toast.LENGTH_SHORT).show();
                    return;
                }

                location1 = locationManager.getLastKnownLocation(provider);
                if (location1 == null) {
                    latitude = 0;
                    longitude = 0;
                } else {
                    latitude = location1.getLatitude();
                    longitude = location1.getLongitude();
                }

                //get the location name by latitude and longitude
                Geocoder gcd = new Geocoder(EditMoodActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);

                    if (addresses.size() > 0)
                        address = "  " + addresses.get(0).getFeatureName() + " " +
                                addresses.get(0).getThoroughfare() + ", " +
                                addresses.get(0).getLocality() + ", " +
                                addresses.get(0).getAdminArea() + ", " +
                                addresses.get(0).getCountryCode();
                    location.setText(address);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final ImageButton deleteLocation = (ImageButton) findViewById(R.id.deleteLocation);
                deleteLocation.setVisibility(View.VISIBLE);
                deleteLocation.setEnabled(true);
            }
        });


        editLocation.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                moodMessage_text = Description.getText().toString();
                editMood.setMoodMessage(moodMessage_text);
                editMood.setDate(date);
                Intent editLocation = new Intent(EditMoodActivity.this, EditLocation.class);
                editLocation.putExtra("EditMood", editMood);
                editLocation.putExtra("bitmap", compress(editBitmapImage));
                startActivity(editLocation);
                return true;
            }
        });

        final ImageButton deleteLocation = (ImageButton) findViewById(R.id.deleteLocation);
        deleteLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                location1 = null;
                locationText = (TextView) findViewById(R.id.locationText);
                address = null;
                locationText.setText(address);
                latitude = 0;
                longitude = 0;
                deleteLocation.setVisibility(View.INVISIBLE);
                deleteLocation.setEnabled(false);
            }
        });

        ImageButton editCameraButton = (ImageButton) findViewById(R.id.editCamera);
        editCameraButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, 1);
                } catch (Exception e) {
                    Intent intent = new Intent(getApplicationContext(), EditMoodActivity.class);
                    startActivity(intent);
                }
            }
        });

        editCameraButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                try {
                    Intent intent = new Intent("android.intent.action.PICK");
                    intent.setType("image/*");
                    startActivityForResult(intent, 0);
                } catch (Exception e) {
                    Intent intent = new Intent(getApplicationContext(), EditMoodActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        ImageButton PickerButton = (ImageButton) findViewById(R.id.EditDate);
        PickerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                innit();
                TimeDialog.show();
            }
        });
        Button submitButton = (Button) findViewById(R.id.button5);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moodMessage_text = Description.getText().toString();
                MoodController moodController = new MoodController();
                editBitmapImage = bitmapImage;
                AchievementManager.initManager(EditMoodActivity.this);
                AchievementController achievementController = new AchievementController();
                achievements = achievementController.getAchievements();
                achievements.firstTimeEditFlag = 1;
                achievementController.saveAchievements();

                if (!moodController.editMood(EmotionText, userName, moodMessage_text,
                        latitude, longitude, editBitmapImage, SocialSituation,
                        date, address, editMood, EditMoodActivity.this)) {
                    Toast.makeText(EditMoodActivity.this,
                            "Mood message length is too long. Please try again", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(EditMoodActivity.this, TimelineActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // TODO button needs only display when image present in Mood
        final ImageButton deletePicture = (ImageButton) findViewById(R.id.deletePicture);
        if (editBitmapImage == null ||  deletedPic == 1) {
            deletePicture.setVisibility(View.INVISIBLE);
            deletePicture.setEnabled(false);
        }
        deletePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bitmapImage = null;
                editBitmapImage = null;
                image.setImageDrawable(null);
                deletePicture.setVisibility(View.INVISIBLE);
                deletePicture.setEnabled(false);
            }
        });
    }


    /**
     * display the attributes of the mood that was selected to view <br>
     * Spinner dropdown logic taken from <br>
     * link: http://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list <br>
     * Author: Nicolas Tyler, 2013/07/15 8:47 <br>
     * taken by Xin Huang 2017-03-04 15:30 (used and swith function written by Nick 2017/03/12 14:30) <br>
     */
    private void displayAttributes() {
        final ImageButton deleteLocation = (ImageButton) findViewById(R.id.deleteLocation);
        if(address == null){
            deleteLocation.setVisibility(View.INVISIBLE);
            deleteLocation.setEnabled(false);
        }

        Spinner dropdown = (Spinner) findViewById(R.id.editEmotion);

        String[] items = new String[]{"anger", "confusion", "disgust", "fear", "happiness", "sadness", "shame", "surprise"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        switch (editMood.getFeeling()) {
            case "anger":
                dropdown.setSelection(0);
                break;
            case "confusion":
                dropdown.setSelection(1);
                break;
            case "disgust":
                dropdown.setSelection(2);
                break;
            case "fear":
                dropdown.setSelection(3);
                break;
            case "happiness":
                dropdown.setSelection(4);
                break;
            case "sadness":
                dropdown.setSelection(5);
                break;
            case "shame":
                dropdown.setSelection(6);
                break;
            case "surprise":
                dropdown.setSelection(7);
                break;

        }
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                EmotionText = parent.getItemAtPosition(position).toString();
                editMood.setFeeling(EmotionText);
                TextView sizeView = (TextView) findViewById(R.id.editSocialText);
                sizeView.setText("Feeling " + EmotionText + " " + SocialSituation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(EditMoodActivity.this, "Please pick a feeling!", Toast.LENGTH_SHORT).show();
            }
        });

        Spinner dropdown_SocialSituation = (Spinner) findViewById(R.id.editSocialSituation);
        String[] item_SocialSituation = new String[]{"", "alone", "with one other person",
                "with two people", "with several people", "with a crowd"};
        ArrayAdapter<String> adapter_SocialSituation = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, item_SocialSituation);
        dropdown_SocialSituation.setAdapter(adapter_SocialSituation);
        switch (editMood.getSocialSituation()) {
            case "alone":
                dropdown_SocialSituation.setSelection(1);
                break;
            case "with one other person":
                dropdown_SocialSituation.setSelection(2);
                break;
            case "with two people":
                dropdown_SocialSituation.setSelection(3);
                break;
            case "with several people":
                dropdown_SocialSituation.setSelection(4);
                break;
            case "with a crowd":
                dropdown_SocialSituation.setSelection(5);
                break;

        }

        dropdown_SocialSituation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                SocialSituation = parent.getItemAtPosition(position).toString();
                editMood.setSocialSituation(SocialSituation);
                TextView sizeView = (TextView) findViewById(R.id.editSocialText);
                sizeView.setText("Feeling " + EmotionText + " " + SocialSituation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Description = (EditText) findViewById(R.id.editDescription);
        Description.setText(editMood.getMoodMessage());
        editBitmapImage = editMood.decodeImage();
        if (editBitmapImage != null) {
            image.setImageBitmap(editBitmapImage);
        } else {
            image.setImageBitmap(bitmapImage);
        }
    }


    /**
     * Method handles user interface response to when a user adds an image to their mood
     * from either their camera or their gallery.
     *
     * Knowledge and logic of onActivityResult referenced and taken from <br>
     * link: http://blog.csdn.net/AndroidStudioo/article/details/52077597 <br>
     * author: AndroidStudio 2016-07-31 11:15 <br>
     * taken by Xin Huang 2017-03-04 15:30 <br>
     *
     * @param requestCode integer indicating the kind of action taken by the user
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ImageView image = (ImageView) findViewById(R.id.editImageView);

        if (data == null) {
            return;   //no data return
        }
        if (requestCode == 0) {
            //get pic from local photo
            bitmapImage = data.getParcelableExtra("data");
            if (bitmapImage == null) {//if pic is not so big use original one
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    bitmapImage = BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                }
            }
        } else if (requestCode == 1) {
            // saveToSDCard(bitmap);
            try {
                bitmapImage = (Bitmap) data.getExtras().get("data");
            } catch (Exception e) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            try {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
            }
        }
        image.setImageBitmap(bitmapImage);
        editBitmapImage = bitmapImage;

        if (editBitmapImage == null) {
            final ImageButton deletePicture = (ImageButton) findViewById(R.id.deletePicture);
            deletePicture.setVisibility(View.INVISIBLE);
            deletePicture.setEnabled(false);
        }
        else{
            final ImageButton deletePicture = (ImageButton) findViewById(R.id.deletePicture);
            deletePicture.setVisibility(View.VISIBLE);
            deletePicture.setEnabled(true);
        }
    }

    /**
     * set up the datetimePicker <br>
     * link: http://blog.csdn.net/hzflogo/article/details/62423240 <br>
     * author: hzflogo 2017-03-16 14:58 <br>
     * taken by Xin Huang 2017-03-29 21:42 <br>
     */

    private void innit() {
        final View dateView = View.inflate(getApplicationContext(), R.layout.datepicker, null);
        final View timeView = View.inflate(getApplicationContext(), R.layout.timepicker, null);
        TimePicker timePicker = (TimePicker) timeView.findViewById(R.id.time);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                currentHour = hourOfDay;
                currentMinte = minute;
            }
        });
        DatePicker datePicker = (DatePicker) dateView.findViewById(R.id.pick);
        datePicker.setDrawingCacheBackgroundColor(getResources().getColor(R.color.colorAccent));
        datePicker.init(currentYear, currentMonth, currentDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                currentYear = year;
                currentMonth = monthOfYear + 1;
                currentDay = dayOfMonth;
            }
        });
        DateDialog = new AlertDialog.Builder(EditMoodActivity.this)
                .setView(dateView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dateString = currentYear + "-" + currentMonth + "-" + currentDay + " " + currentHour + ":" + currentMinte;
                        try {
                            java.text.SimpleDateFormat formatter = new SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm");
                            date = formatter.parse(dateString);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(EditMoodActivity.this, "" + date, Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DateDialog.dismiss();
                    }
                })
                .create();
        TimeDialog = new AlertDialog.Builder(EditMoodActivity.this)
                .setView(timeView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DateDialog.show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TimeDialog.dismiss();
                    }
                })
                .create();

    }

    /**
     * Compression of image. From: http://blog.csdn.net/harryweasley/article/details/51955467 <br>
     * author: HarryWeasley 2016-07-20 15:26 <br>
     * taken by Xin Huang 2017-03-04 18:45 <br>
     * for compressing the image to meet the project storage requirements <br>
     * @param image  // the image to be compressed <br>
     * @return Bitmap  // the compressed image <br>
     */
    public Bitmap compress(Bitmap image) {
        try {
            // Compression of image. From: http://blog.csdn.net/harryweasley/article/details/51955467
            // for compressing the image to meet the project storage requirements
            while (((image.getRowBytes() * image.getHeight()) / 8) > 65536) {
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inPreferredConfig = Bitmap.Config.RGB_565;
                Matrix matrix = new Matrix();
                matrix.setScale(0.5f, 0.5f);
                image = Bitmap.createBitmap(image, 0, 0, image.getWidth(),
                        image.getHeight(), matrix, true);
            }
        } catch (Exception E) {
            E.printStackTrace();
        }
        return image;
    }
}
