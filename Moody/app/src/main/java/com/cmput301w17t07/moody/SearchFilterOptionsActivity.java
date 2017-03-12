package com.cmput301w17t07.moody;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;

public class SearchFilterOptionsActivity extends BarMenuActivity {

    private Button searchUser;
    private TextView searchUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter_options);
        setUpMenuBar(this);

        searchUser=(Button) findViewById(R.id.searchUser);
        searchUsername=(TextView) findViewById(R.id.searchEditname);
        //String username=searchUsername.getText().toString();
        //System.out.println("username is  "+username);

        searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchFilterOptionsActivity.this, SearchUserActivity.class);
                String username=searchUsername.getText().toString();
                intent.putExtra("editUsername", username);
                System.out.println("username is  "+username);
                //startActivityForResult(intent, 3);
                startActivity(intent);
                finish();
            }
        });
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        System.out.println("this is onstart");
//        ElasticSearchUserController.GetUser getUser = new ElasticSearchUserController.GetUser();
//        getUser.execute("xin");
//        final ListView oldUserList = (ListView) findViewById(R.id.listSearch);
//
//        try {
//            //userArrayList=new ArrayList<>();
//            userArrayList = getUser.get();
//            System.out.println("this is userlist " + userArrayList.get(0).getUsername());
//
//        } catch (Exception e) {
//            Log.i("error", "failed to get the User out of the async matched");
//        }
//        try {
//            userAdapter = new UserAdapter(this, userArrayList);
//            oldUserList.setAdapter(userAdapter);
//        }catch (Exception e){
//            System.out.println("this is error"+e);
//        }
//
//    }

}
