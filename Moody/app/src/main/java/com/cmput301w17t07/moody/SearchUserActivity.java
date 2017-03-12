package com.cmput301w17t07.moody;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ListView;
import java.util.ArrayList;


public class SearchUserActivity extends AppCompatActivity {

    private ArrayList<User> userArrayList=new ArrayList<>();
    private ListView oldUserList;
    private UserAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ElasticSearchUserController.GetUser getUser = new ElasticSearchUserController.GetUser();
        oldUserList = (ListView) findViewById(R.id.listSearch);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("editUsername");
        System.out.printf("this is xin in after " + username);
        getUser.execute(username);

        try {

            userArrayList = getUser.get();
            //System.out.println("this is userlist " + userArrayList.get(0).getUsername());

        } catch (Exception e) {
            Log.i("error", "failed to get the User out of the async matched");
        }
        userAdapter = new UserAdapter(this, userArrayList);
        oldUserList.setAdapter(userAdapter);
        //System.out.println("this is error"+e);



    }


 }
