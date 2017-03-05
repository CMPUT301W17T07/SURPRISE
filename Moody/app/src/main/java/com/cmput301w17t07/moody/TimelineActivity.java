package com.cmput301w17t07.moody;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;

public class TimelineActivity extends AppCompatActivity {
    ConnectivityManager manager;
    private EditText usernameText;

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

            usernameText = (EditText) findViewById(R.id.enterUsername);
            /**
             * On user's click of the register button
             */
            registerButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (checkNetworkState() == false) {
                        Toast.makeText(TimelineActivity.this, "Internet not available \n" +
                                "Plaese check internet", Toast.LENGTH_SHORT).show();
                    } else {
                        String username = usernameText.getText().toString();
                        User newUser = new User(username);
                        // Want to add something here like user.checkUsername that will check the database to see if username
                        // is unique. if(user.checkUsername == false) {return} else ....
                        ElasticSearchMoodyController.AddUser addUser = new ElasticSearchMoodyController.AddUser();
                        addUser.execute(newUser);
                        Toast.makeText(TimelineActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                        setContentView(R.layout.activity_timeline);
                        setUpMenuBar();
                    }
                }
            });
        } else {
            setContentView(R.layout.activity_timeline);
            setUpMenuBar();

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

    private void setUpMenuBar() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:

                                break;
                            //setContentView(R.layout.activity_timeline);
                            case R.id.action_search:

                                break;
                            //setContentView(R.layout.activity_search_filter_options);
                            case R.id.action_profile:

                                break;
                            //setContentView(R.layout.activity_profile);
                            case R.id.action_create:

                                break;
                            //setContentView(R.layout.activity_create_mood);
                        }
                        return false;
                    }
                });
    }



}

