package com.cmput301w17t07.moody;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SearchUserActivity extends AppCompatActivity {

    //private EditText usernameText;
    private ArrayList<User> userArrayList=new ArrayList<>();
    //private List<User> userList;

    private ListView oldUserList;
    private UserAdapter userAdapter;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("this is onstart");
        ElasticSearchUserController.GetUser getUser = new ElasticSearchUserController.GetUser();
        //getUser.execute("");
        final ListView oldUserList = (ListView) findViewById(R.id.listSearch);
        Intent intent=getIntent();
        String username=intent.getStringExtra("editUsername");
        System.out.printf("this is xin in after " + username);
        getUser.execute(username);

        try {
            //userArrayList=new ArrayList<>();
            userArrayList = getUser.get();
            //System.out.println("this is userlist " + userArrayList.get(0).getUsername());

        } catch (Exception e) {
            Log.i("error", "failed to get the User out of the async matched");
        }
        try {
            userAdapter = new UserAdapter(this, userArrayList);
            oldUserList.setAdapter(userAdapter);
        }catch (Exception e){
            System.out.println("this is error"+e);
        }

    }
 }
