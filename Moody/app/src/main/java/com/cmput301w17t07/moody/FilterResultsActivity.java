package com.cmput301w17t07.moody;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FilterResultsActivity extends BarMenuActivity {

    String username;
    private ListView moodTimeline;
    private TimelineAdapter adapter;
    private ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    private Button feelingFilterButton;
    private Button messageFilterButton;
    private Integer selectedFilter;
    private String filterFeeling;
    private String filterMessage;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_results);

        setUpMenuBar(this);
        UserController userController = new UserController();
        username = userController.readUsername(FilterResultsActivity.this).toString();

        intent = getIntent();
        selectedFilter = intent.getIntExtra("selectedFilter", 0);

    }



    @Override
    protected void onStart(){
        super.onStart();
        moodTimeline = (ListView) findViewById(R.id.filterResultsList);



        if(selectedFilter == 0){
            filterFeeling = intent.getStringExtra("feelingFilter");
            ElasticMoodController.GetFeelingFilterMoods getFeelingFilterMoods =
                    new ElasticMoodController.GetFeelingFilterMoods();
            getFeelingFilterMoods.execute(username, filterFeeling);

            try {
                moodArrayList= getFeelingFilterMoods.get();
//               System.out.println("this is moodlist"+moodArrayList);

            }catch (Exception e){
                Log.i("error","failed to get the mood out of the async matched");
            }

        }
        if(selectedFilter == 2){
            filterMessage = intent.getStringExtra("messageFilter");
            ElasticMoodController.GetMessageFilterMoods getMessageFilterMoods =
                    new ElasticMoodController.GetMessageFilterMoods();
            getMessageFilterMoods.execute(username, filterMessage);

            try {
                moodArrayList= getMessageFilterMoods.get();
//               System.out.println("this is moodlist"+moodArrayList);

            }catch (Exception e){
                Log.i("error","failed to get the mood out of the async matched");
            }

        }


        //Toast.makeText(FilterResultsActivity.this, selectedFilter.toString(), Toast.LENGTH_SHORT).show();



        adapter = new TimelineAdapter(this, R.layout.timeline_list, moodArrayList);
//        Toast.makeText(ProfileActivity.this, moodArrayList.get(1).getFeeling(), Toast.LENGTH_SHORT).show();

        moodTimeline.setAdapter(adapter);
        moodTimeline.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                Mood viewMood = moodArrayList.get(position);

                Intent viewMoodIntent = new Intent(FilterResultsActivity.this, ViewMoodActivity.class);
                viewMoodIntent.putExtra("viewMood", viewMood);
                startActivity(viewMoodIntent);
            }


        });




    }

}
