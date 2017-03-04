package com.cmput301w17t07.moody;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TimelineActivity extends AppCompatActivity {
    ConnectivityManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("isFirstIn", Activity.MODE_PRIVATE);
        boolean isFirstIn = sp.getBoolean("isFirstIn", true);
        if (isFirstIn) {
            sp.edit().putBoolean("isFirstIn", false).commit();
            setContentView(R.layout.activity_create_user);
            Toast.makeText(TimelineActivity.this, " Welcome to Moody! ", Toast.LENGTH_SHORT).show();
            Button registerButton = (Button) findViewById(R.id.register);
            registerButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if(checkNetworkState() == false) {
                        Toast.makeText(TimelineActivity.this, "Internet unavailable! \n " +
                                "Please check Internet", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(TimelineActivity.this, "Registtration successful!", Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.activity_timeline);
                    }
                }
            });
        } else {
            setContentView(R.layout.activity_timeline);
        }
    }

    //Internet checker temp, maybe need change later
    private boolean checkNetworkState() {
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            return false;
        } else {
            return true;
        }
    }
}
