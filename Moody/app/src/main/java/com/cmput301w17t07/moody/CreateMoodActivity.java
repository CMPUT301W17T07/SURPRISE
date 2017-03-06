package com.cmput301w17t07.moody;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateMoodActivity extends AppCompatActivity {
    private ImageView mImageView;
    private EditText EmotionText;
    private EditText Description;

    private static final String iconPath = Environment.getExternalStorageDirectory()+"/Image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mood);
        EmotionText = (EditText) findViewById(R.id.Emotion);
        Description = (EditText) findViewById(R.id.Description);

        mImageView = (ImageView) findViewById(R.id.imageView);

        ImageButton chooseButton = (ImageButton) findViewById(R.id.Camera);


        chooseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                startActivityForResult(intent, 0);  }
        });

        chooseButton.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View view){
                try {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, 1);
                } catch(Exception e){
                    Intent intent =new Intent(getApplicationContext(), CreateMoodActivity.class);
                    startActivity(intent);
                } return true; }
        });

        Button submitButton = (Button) findViewById(R.id.button5);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String feeling_text = EmotionText.getText().toString();
                String username_text = Description.getText().toString();
                MoodController moodController = new MoodController();
                if (moodController.createMood(feeling_text, username_text, null, null, null, null) == false) {
                    Toast.makeText(CreateMoodActivity.this, "submit unsuccessful, try it again", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(CreateMoodActivity.this, TimelineActivity.class);
                    startActivity(intent);
                }

//                Mood mood = new Mood(feeling_text, Description_text);
//                MoodController.AddTweetsTask addTweetsTask = new ElasticsearchTweetController.AddTweetsTask();
//                addTweetsTask.execute(newTweet);


            }
        });

    }
    @Override
    //onActivityResult taken from: http://blog.csdn.net/AndroidStudioo/article/details/52077597
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null){
            return;   //no data return
        }
        Bitmap bitmap = null;
        if(requestCode==0){
            //get pic from local photo
            bitmap = data.getParcelableExtra("data");
            if(bitmap==null){//if pic is not so big use original one
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }else if(requestCode==1){
            bitmap = (Bitmap) data.getExtras().get("data");
            System.out.println("photosize = ");
            // saveToSDCard(bitmap);
        }
        else if (resultCode== Activity.RESULT_CANCELED)
        {
            Intent intent =new Intent(getApplicationContext(), CreateMoodActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        mImageView.setImageBitmap(bitmap);
    }
}
