package com.cmput301w17t07.moody;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class CreateMoodActivity extends AppCompatActivity {
    private EditText bodyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mood);

//        bodyText = (EditText) findViewById(R.id.body);
//        Button submitButton = (Button) findViewById(R.id.submit);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                setResult(RESULT_OK);
//                String text = bodyText.getText().toString();
//                Mood newMood = new Mood(text);
//                //tweetList.add(newTweet);
//                //adapter.notifyDataSetChanged();
//                //saveInFile(); // TODO replace this with elastic search
//                MoodController.AddTweetsTask addTweetsTask = new ElasticsearchMoodyController.AddTweetsTask();
//                addTweetsTask.execute(newMood);
//
//            }
//        });
    }
}
