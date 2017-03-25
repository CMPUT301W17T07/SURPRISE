package com.cmput301w17t07.moody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfileActivity extends BarMenuActivity {

    private ListView moodTimeline;
    private MoodAdapter adapter;
    private ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
    String username;

    private ArrayList<Mood> templist = new ArrayList<Mood>();
    int indexOfScroll=0;
    int lastItem;

//    final Context currentContext = context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpMenuBar(this);
        UserController userController = new UserController();
//        username = userController.getUsername();
        username = userController.readUsername(ProfileActivity.this).toString();

        Toast.makeText(ProfileActivity.this, userController.readUsername(ProfileActivity.this).toString(), Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onStart(){
        super.onStart();


        ElasticMoodController.GetUserMoods getUserMoods = new ElasticMoodController.GetUserMoods();
        getUserMoods.execute(username,String.valueOf(indexOfScroll));

        final ListView moodTimelineListView = (ListView) findViewById(R.id.test_list);

        try {
            moodArrayList= getUserMoods.get();
//               System.out.println("this is moodlist"+moodArrayList);

        }catch (Exception e){
            Log.i("error","failed to get the mood out of the async matched");
        }

        adapter = new MoodAdapter(this, R.layout.timeline_list, moodArrayList);
//        Toast.makeText(ProfileActivity.this, moodArrayList.get(1).getFeeling(), Toast.LENGTH_SHORT).show();

        moodTimelineListView.setAdapter(adapter);

        moodTimelineListView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){
                // 当不滚动时
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    Toast.makeText(getApplicationContext(), "Starting load new moody",Toast.LENGTH_SHORT).show();
                    indexOfScroll=indexOfScroll+6;
                    ElasticMoodController.GetUserMoods getUserMoodsAgain = new ElasticMoodController.GetUserMoods();
                    getUserMoodsAgain.execute(username,String.valueOf(indexOfScroll));
                    try {
                        templist= getUserMoodsAgain.get();

                    }catch (Exception e){
                        Log.i("error","failed to get the mood out of the async matched");
                    }
                    moodArrayList.addAll(templist);
                    adapter.notifyDataSetChanged();



                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                lastItem = firstVisibleItem + visibleItemCount - 1 ;
            }
        });
        moodTimelineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                Mood viewMood = moodArrayList.get(position);
//                System.out.println("this is er");
//                System.out.println("this is er"+viewMood.getLocation().getLongitude());
                Intent viewMoodIntent = new Intent(ProfileActivity.this, ViewMoodActivity.class);
                viewMoodIntent.putExtra("viewMood", viewMood);

                startActivity(viewMoodIntent);


            }


        });




    }

}


