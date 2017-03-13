package com.cmput301w17t07.moody;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SearchFilterOptionsActivity extends BarMenuActivity {

    private Button searchUser;
    private TextView searchUsername;
    private Button searchMood;
    private TextView searchMoodmes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter_options);
        setUpMenuBar(this);

        searchUser=(Button) findViewById(R.id.searchUser);
        searchUsername=(TextView) findViewById(R.id.searchEditname);

        //searchMood=(Button) findViewById(R.id.searchMood);
        //searchMoodmes=(TextView) findViewById(R.id.searchMoodMes);


        searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUser = new Intent(SearchFilterOptionsActivity.this, SearchUserActivity.class);
                String username=searchUsername.getText().toString();
                intentUser.putExtra("editUsername", username);
                startActivity(intentUser);
                finish();
            }
        });

//        searchUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public  void onClick(View v){
//                Intent intentMood=new Intent(SearchFilterOptionsActivity.this,SearchUserActivity.class);
//                String moodfeel=searchMoodmes.getText().toString();
//                intentMood.putExtra("mood",moodfeel);
//                startActivity(intentMood);
//                finish();
//            }
//        });


    }


}
