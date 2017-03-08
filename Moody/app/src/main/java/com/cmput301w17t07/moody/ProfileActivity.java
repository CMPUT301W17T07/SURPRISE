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

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class ProfileActivity extends AppCompatActivity {

    private ListView oldMoodList;
    private hopefuladapter adapter;
    private ArrayList<Mood> moodArrayList = new ArrayList<Mood>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

    }

        @Override
        protected void onStart(){
            super.onStart();
            ElasticMoodController.GetUserMoods getUserMoods = new ElasticMoodController.GetUserMoods();
            getUserMoods.execute("xin");

            try {
                moodArrayList= getUserMoods.get();
                System.out.println("this is moodlist"+moodArrayList);

            }catch (Exception e){
                Log.i("error","failed to get the mood out of the async matched");
            }
            adapter = new hopefuladapter(this, moodArrayList);
//                @Override
//                public View getView(int position, View convertView, ViewGroup parent) {
//                    View view = super.getView(position, convertView, parent);
//                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
//                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
//                /* Setting the text that will appear on the two lines of the list entry */
//                    text1.setText(moodArrayList.get(position).getUsername());
//                /* Bolding the name entry */
//                    text1.setTypeface(null, Typeface.BOLD);
//
//                    text2.setText("Feeling: " + moodArrayList.get(position).getFeeling() );
//                    return view;
//                }



            oldMoodList.setAdapter(adapter);
            }
}


