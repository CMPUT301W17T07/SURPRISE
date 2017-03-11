package com.cmput301w17t07.moody;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

public class SearchUserActivity extends AppCompatActivity {

    private UserAdapter adapter;
    private List<User> userList;
    private ListView olduserlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
    }

    @Override
    protected void onStart(){
        super.onStart();
        ElasticSearchUserController.GetUser getUser=new ElasticSearchUserController.GetUser();
        getUser.execute("");

        olduserlist=(ListView) findViewById(R.id.listOfSearch);
        try{
            userList=getUser.get();
            System.out.println("this is userlist"+
                    userList);
        }catch (Exception e){
            Log.i("error","failed to get the user out of the async matched");
        }

        adapter=new UserAdapter(this,userList);
        olduserlist.setAdapter(adapter);



    }
}
