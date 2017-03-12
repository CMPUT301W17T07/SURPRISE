package com.cmput301w17t07.moody;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewMoodActivity extends BarMenuActivity {
    private String username;
    public Mood viewMood;
    public Integer id;
    private MoodImage moodImage;
    private Bitmap bitmapImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_mood);
        setUpMenuBar(this);
        // get the mood object that was selected
        Intent intent = getIntent();
        viewMood = (Mood) intent.getSerializableExtra("viewMood");

        // get username right
        UserController userController = new UserController();
        username = userController.readUsername(ViewMoodActivity.this).toString();
        // if the mood was from user profile allow edit/delete
        if (viewMood.getUsername().equals(username)) {

            displayAttributes();
            // edit mood stuff ...
            Button editButton = (Button) findViewById(R.id.userEditButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent editMoodIntent = new Intent(ViewMoodActivity.this, EditMoodActivity.class);
                    editMoodIntent.putExtra("editMood", viewMood);
                    startActivity(editMoodIntent);
                }
            });
        }
        // else we disable and don't show the edit/delete button
        else {
            Button edit = (Button) findViewById(R.id.userDeleteButton);
            edit.setVisibility(Button.GONE);
            Button delete = (Button) findViewById(R.id.userEditButton);
            delete.setVisibility(Button.GONE);
            displayAttributes();
        }
    }

    // display the attributes of the mood that was selected to view
    private void displayAttributes() {

        // NOTE MISSING IMAGE AND LOCATION STILL !!!!!!!!!!!
        TextView user = (TextView) findViewById(R.id.userUsernameTV);
        user.setText(viewMood.getUsername());

        TextView feeling = (TextView) findViewById(R.id.userFeelingTV);
        feeling.setText(viewMood.getMoodMessage());

        TextView date = (TextView) findViewById(R.id.userDateTV);
        date.setText(viewMood.getDate());

        ImageView image = (ImageView) findViewById(R.id.viewMoodImage);
//        //todo handle no image case!!
//        image.setImageBitmap(viewMood.getMoodImage());

        //!!!! NEW STUFF
        String imageID = viewMood.getMoodImageID();
//        Toast.makeText(ViewMoodActivity.this, imageID, Toast.LENGTH_SHORT).show();

        ElasticMoodController.GetMoodImage getMoodImage = new ElasticMoodController.GetMoodImage();
        getMoodImage.execute(imageID);
        // retrieving the image
        try {
            bitmapImage = getMoodImage.get().decodeImage();
        }catch (Exception e){
            Log.i("error","failed to get the moodImage"+imageID);
        }

        image.setImageBitmap(bitmapImage);

        TextView social = (TextView) findViewById(R.id.userSocialTV);
        social.setText(viewMood.getSocialSituation());

        ImageView emoji = (ImageView) findViewById(R.id.userFeelingEmoji);
        switch (viewMood.getFeeling()) {
            case "anger":
                emoji.setImageResource(R.drawable.angry);
                break;
            case "confusion":
                emoji.setImageResource(R.drawable.confused);
                break;
            case "disgust":
                emoji.setImageResource(R.drawable.disgust);
                break;
            case "fear":
                emoji.setImageResource(R.drawable.fear);
                break;
            case "happiness":
                emoji.setImageResource(R.drawable.happy);
                break;
            case "sadness":
                emoji.setImageResource(R.drawable.sad);
                break;
            case "shame":
                emoji.setImageResource(R.drawable.shame);
                break;
            case "surprise":
                emoji.setImageResource(R.drawable.surprise);
                break;
        }

    }

}
