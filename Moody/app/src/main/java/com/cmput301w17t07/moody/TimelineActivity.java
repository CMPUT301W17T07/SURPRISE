package com.cmput301w17t07.moody;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TimelineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("isFirstIn", Activity.MODE_PRIVATE);
        boolean isFirstIn = sp.getBoolean("isFirstIn", true);
        if(isFirstIn) {
            sp.edit().putBoolean("isFirstIn", false).commit();
            setContentView(R.layout.activity_create_user);
            Toast.makeText(TimelineActivity.this, " Welcome to Moody! ", Toast.LENGTH_SHORT).show();
            Button registerButton = (Button) findViewById(R.id.register);
            registerButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(TimelineActivity.this, "Registtration successful!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(TimelineActivity.this, TimelineActivity.class);
//                    startActivity(intent);
                    setContentView(R.layout.activity_timeline);
                }
            });
        }

        else{
            setContentView(R.layout.activity_timeline);
        }

    }
}
