package com.cmput301w17t07.moody;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchUserActivity extends BarMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        setUpMenuBar(this);

        Button searchButton = (Button) findViewById(R.id.submitFind);

        searchButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                EditText userNameText = (EditText) findViewById(R.id.findUser);
                String userName = userNameText.getText().toString();

                ElasticSearchMoodyController.GetUsers getUsers = new ElasticSearchMoodyController.GetUsers();
                getUsers.execute(userName);
            }
        });
    }
}
