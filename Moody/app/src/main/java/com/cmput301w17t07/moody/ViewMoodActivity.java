package com.cmput301w17t07.moody;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.data;

public class ViewMoodActivity extends BarMenuActivity {
    private String username;
    private ImageView mImageView;
    private String EmotionText;
    private String SocialSituation;
    private EditText Description;
    public Mood viewMood;

    public Integer id;

    private TimelineAdapter adapter;
    private ArrayList<Mood> moodArrayList = new ArrayList<Mood>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_user_mood);
        setUpMenuBar(this);

        Intent intent = getIntent();
        viewMood = (Mood) intent.getSerializableExtra("viewMood");

        // get username right?
        UserController userController = new UserController();
        username = userController.readUsername(ViewMoodActivity.this).toString();

        if (viewMood.getUsername().equals(username)) {
            displayAttributes(username);

        }


    }

    private void displayAttributes(String username) {

        TextView user = (TextView) findViewById(R.id.userUsernameTV);
        user.setText(viewMood.getUsername());

        TextView feeling = (TextView) findViewById(R.id.userFeelingTV);
        feeling.setText(viewMood.getMoodMessage());

        TextView date = (TextView) findViewById(R.id.userDateTV);
        date.setText(viewMood.getDate());


    }

}
