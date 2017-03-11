package com.cmput301w17t07.moody;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class SearchUserActivity extends AppCompatActivity {

    private UserAdapter adapter;
    private List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
    }

    @Override
    protected void onStart(){
        super.onStart();

    }
}
