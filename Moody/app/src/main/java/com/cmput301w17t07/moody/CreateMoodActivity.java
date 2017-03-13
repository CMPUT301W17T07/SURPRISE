package com.cmput301w17t07.moody;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateMoodActivity extends BarMenuActivity {
    private ImageView mImageView;
    private String EmotionText;
    private String SocialSituation;
    private EditText Description;
    private String userName;
    Bitmap bitmap = null;

//    private static final String iconPath = Environment.getExternalStorageDirectory() + "/Image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mood);
        UserController userController = new UserController();
        userName = userController.readUsername(CreateMoodActivity.this).toString();
        setUpMenuBar(this);


        //Spinner drodown taken from http://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
        //Author: Nicolas Tyler, 2013/07/15 8:47
        //taken by Xin Huang 2017/03/10 23:10
        Spinner dropdown = (Spinner) findViewById(R.id.Emotion);

        String[] items = new String[]{"anger", "confusion", "disgust", "fear", "happiness", "sadness", "shame", "surprise"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                EmotionText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CreateMoodActivity.this, "Please pick a feeling!", Toast.LENGTH_SHORT).show();
            }
        });


        Spinner dropdown_SocialSituation = (Spinner) findViewById(R.id.SocialSituation);
        String[] item_SocialSituation = new String[]{"", "alone", "with one other person",
                "with two people", "with several people", "with a crowd"};
        ArrayAdapter<String> adapter_SocialSituation = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, item_SocialSituation);
        dropdown_SocialSituation.setAdapter(adapter_SocialSituation);

        dropdown_SocialSituation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                SocialSituation = parent.getItemAtPosition(position).toString();
                TextView sizeView = (TextView) findViewById(R.id.SocialText);
                sizeView.setText(SocialSituation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Description = (EditText) findViewById(R.id.Description);

        mImageView = (ImageView) findViewById(R.id.editImageView);

        ImageButton chooseButton = (ImageButton) findViewById(R.id.Camera);

        ImageButton locationButton = (ImageButton) findViewById(R.id.location);


        chooseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, 1);
                } catch (Exception e) {
                    Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
                    startActivity(intent);
                }
            }
        });


        chooseButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                try {
                    Intent intent = new Intent("android.intent.action.PICK");
                    intent.setType("image/*");
                    startActivityForResult(intent, 0);
                } catch (Exception e) {
                    Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });


        locationButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            }
        });

        Button submitButton = (Button) findViewById(R.id.button5);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String moodMessage_text = Description.getText().toString();
                MoodController moodController = new MoodController();
                if (moodController.createMood(EmotionText, userName,
                        moodMessage_text, null, bitmap, SocialSituation) == false) {
                    Toast.makeText(CreateMoodActivity.this,
                            "Mood message length is too long. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CreateMoodActivity.this, TimelineActivity.class);
                    startActivity(intent);
                }
            }
        });



    }

    @Override
    //onActivityResult taken from: http://blog.csdn.net/AndroidStudioo/article/details/52077597
    //author: AndroidStudioo 2016-07-31 11:15
    //taken by Xin Huang 2017-03-04 15:30
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            finish();   //no data return
        }
        if (requestCode == 0) {
            //get pic from local photo
            try{
            bitmap = data.getParcelableExtra("data");
            if (bitmap == null) {//if pic is not so big use original one
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }}
            catch(RuntimeException e){
                Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
                startActivity(intent);
            }
        }
        else if (requestCode == 1) {
            try{
            bitmap = (Bitmap) data.getExtras().get("data");
            System.out.println("photosize = " + bitmap.getByteCount());}
            catch (Exception e){
                Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
                startActivity(intent);
            }


        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            try {
                System.out.println("test for ccamere" + data.getExtras().get("data"));
                Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
                startActivity(intent);
            } catch (RuntimeException e) {
            }

        }
        mImageView.setImageBitmap(bitmap);
        }

}
