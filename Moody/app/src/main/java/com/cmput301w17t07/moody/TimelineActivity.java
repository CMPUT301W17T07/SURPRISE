package com.cmput301w17t07.moody;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActivityChooserView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.ArrayList;

/**
 *  The Timeline Activity handles the user interface logic for when the user has first installed
 *  our application. It allows them to create a unique username after that.
 *
 *  After the user's first time using the application, it takes them to the timeline activity page
 *  which will display the moods of the users they follow. This functionality is being added in
 *  project part 5.
 */

public class TimelineActivity extends BarMenuActivity {
    ConnectivityManager manager;
    private EditText usernameText;
    Integer createUserFlag = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("isFirstIn", Activity.MODE_PRIVATE);
        boolean isFirstIn = sp.getBoolean("isFirstIn", true);
        if (isFirstIn) {
            sp.edit().putBoolean("isFirstIn", false).apply();
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
                                "Please check internet", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String username = usernameText.getText().toString();

                        //Intent intent= new Intent(TimelineActivity.this,UserAdapter.class);
                        //intent.putExtra("userNameBegin",username);
                        //startActivity(intent);

                        //todo get image from user and createUser with image parameter
                        UserController userController = new UserController();
                        createUserFlag = userController.createUser(username);
                        if(createUserFlag.equals(1)){
                            Toast.makeText(TimelineActivity.this,
                                    "Username is already taken", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else if(createUserFlag.equals(2)){
                            Toast.makeText(TimelineActivity.this,
                                    "Sorry, but the profile picture you selected is too large",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //todo use this internet checking method instead of one inside the activity
//                        else if(createUserFlag.equals(3)){
//                            Toast.makeText(TimelineActivity.this,
//                                    "Please check your internet connection", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
                        userController.saveUsername(username.toLowerCase(), TimelineActivity.this);


                    }


                    Toast.makeText(TimelineActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                    timelineActivity();

                }
            });
        }else {
            timelineActivity();
        }


    }

    //todo remove this from activity and implement in a separate class/controller/etc.
    //Internet checker temp, maybe need change later

    /**
     * checkNetworkState method. This is a temporary method in project part 4 that checks if a user
     * is online before attempting to register a username. A similar type of function will be moved
     * into an appropriate controller class for project part 5 to reflect better separation of
     * user interface logic and backend logic.
     *
     * Method currently returns a boolean indicating whether the user is connected to the internet.
     * @return
     */
    private boolean checkNetworkState() {
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Current implementation of timelineActivity method. Just displays blank xml content screen
     * with menubar.
     */
    private void timelineActivity(){
        setContentView(R.layout.activity_timeline);
        setUpMenuBar(this);
    }

}