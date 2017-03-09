package com.cmput301w17t07.moody;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class ProfileActivity extends BarMenuActivity {

    private ListView moodTimeline;
    private TimelineAdapter adapter;
    private ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpMenuBar(this);
        UserController userController = new UserController();
//        username = userController.getUsername();

    }

    @Override
    protected void onStart(){
        super.onStart();
        ElasticMoodController.GetUserMoods getUserMoods = new ElasticMoodController.GetUserMoods();
        getUserMoods.execute("xin");
        final ListView moodTimelineListView = (ListView) findViewById(R.id.test_list);

        try {
            moodArrayList= getUserMoods.get();
//               System.out.println("this is moodlist"+moodArrayList);

        }catch (Exception e){
            Log.i("error","failed to get the mood out of the async matched");
        }

        adapter = new TimelineAdapter(this, R.layout.timeline_list, moodArrayList);
//        Toast.makeText(ProfileActivity.this, moodArrayList.get(1).getFeeling(), Toast.LENGTH_SHORT).show();

        moodTimelineListView.setAdapter(adapter);

    }

}


