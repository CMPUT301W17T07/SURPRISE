package com.cmput301w17t07.moody;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class EditMoodActivity extends BarMenuActivity {
    public Mood editMood;
    private Bitmap bitmapImage = null;
    private String userName;
    private String EmotionText;
    private String SocialSituation;
    private EditText Description;
    private EditText date;
//    private Date dateValue;

//    Bitmap bitmap = null;


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

        displayAttributes();

        ImageButton editCameraButton = (ImageButton) findViewById(R.id.editCamera);

        editCameraButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 1);
//                Intent intent = new Intent("android.intent.action.PICK");
//                intent.setType("image/*");
//                startActivityForResult(intent, 0);
            }
        });

        editCameraButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                try {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, 1);
                } catch (Exception e) {
                    Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        Button submitButton = (Button) findViewById(R.id.button5);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String moodMessage_text = Description.getText().toString();
//                String dateValue = date.getText().toString();
                MoodController moodController = new MoodController();
                if (moodController.editMood(EmotionText, userName, moodMessage_text,
                        null, bitmapImage, SocialSituation, null, editMood ) == false) {
                    Toast.makeText(EditMoodActivity.this,
                            "Mood message length is too long. Please try again", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(EditMoodActivity.this, TimelineActivity.class);
                    startActivity(intent);
                }
            }
        });

        // TODO button needs only display when image present in Mood
        final ImageButton deletePicture = (ImageButton) findViewById(R.id.deletePicture);
        deletePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ImageView image = (ImageView) findViewById(R.id.editImageView);
                image.setImageDrawable(null);
                deletePicture.setImageResource(android.R.color.transparent);

            }
        });
    }



    // display the attributes of the mood that was selected to view
    private void displayAttributes() {

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
                TextView sizeView = (TextView) findViewById(R.id.editSocialText);
                sizeView.setText("Feeling " + editMood.getFeeling() + " "  + SocialSituation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        date = (EditText) findViewById(R.id.editDate);
//        date.setText(editMood.getDate());

        Description = (EditText) findViewById(R.id.editDescription);
        Description.setText(editMood.getMoodMessage());

        ImageView image = (ImageView) findViewById(R.id.editImageView);

        String imageID = editMood.getMoodImageID();

        ElasticMoodController.GetMoodImage getMoodImage = new ElasticMoodController.GetMoodImage();
        getMoodImage.execute(imageID);


        // retrieving the image
        try {
            bitmapImage = getMoodImage.get().decodeImage();
        }catch (Exception e){
            Log.i("error","failed to get the moodImage"+imageID);
        }

        image.setImageBitmap(bitmapImage);


    }


    @Override
    //onActivityResult taken from: http://blog.csdn.net/AndroidStudioo/article/details/52077597
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView image = (ImageView) findViewById(R.id.editImageView);

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
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 1) {
            bitmapImage = (Bitmap) data.getExtras().get("data");
            System.out.println("photosize = " + bitmapImage.getByteCount());
            // saveToSDCard(bitmap);
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Intent intent = new Intent(getApplicationContext(), CreateMoodActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        image.setImageBitmap(bitmapImage);



    }


}
