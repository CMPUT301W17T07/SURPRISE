/*
 * Copyright 2017 CMPUT301W17T07
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cmput301w17t07.moody;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



/**
 * Created by Panchy on 2017-03-12.
 */

/**
 * The UserAdapter class object of the Moody application. This class is used to properly display
 * the list of user after searching for user in SearchFilterOptionsActivity. It is used in the
 * SearchUserActivity class.
 *
 * Implementation of part of this class was partially inspired by the Android developer's web
 * page on ListView Scrolling Smoothly:
 * https://developer.android.com/training/improving-layouts/smooth-scrolling.html#AsyncTask
 * Taken By: Qi Pang 2017/03/10
 */

public class UserAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;
    private String username;
    private String searchUsername;


    /**
     * Constructor for the UserAdapter
     * @param context       Context for the adapter
     * @param userList      The list of users
     */
    public UserAdapter(Context context, List<User> userList, String username, String searchUsername) {
        this.context = context;
        this.userList = userList;
        this.username = username;
        this.searchUsername = searchUsername;
    }

    /**
     * get the size of peoplelist
     * @return size
     */
    @Override
    public int getCount() {
        return userList.size();
    }

    /**
     * get the item in the position of peoplelist
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    /**
     * get the position
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * set the people information in main activity
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        final SearchViewHolder viewHolder;


        if (convertView == null) {
            viewHolder =new SearchViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.single_search_list,null);
            viewHolder.userName=(TextView) view.findViewById(R.id.singleSearchItemName);
            viewHolder.requestButton=(Button) view.findViewById(R.id.searchAdd);
            //viewHolder.declineBtn=(Button) view.findViewById(R.id.searchDecline);

            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(SearchViewHolder) view.getTag();
        }


        viewHolder.userName.setText(userList.get(position).getUsername());
        viewHolder.userName.setTextSize(30);

        viewHolder.requestButton.setTag(position);
        viewHolder.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v){
                //todo implement ability to show send request button or following text depending on if user is already following
                FollowController followController = new FollowController();
                if(followController.sendPendingRequest(username, searchUsername)){
                    // if it returns true....


                    // change button displayed
                    //todo need to implement check for what button to display on this screen
                    viewHolder.requestButton.setBackgroundColor(context.getResources().getColor(R.color.blueTheme));
                    viewHolder.requestButton.setText("REQUEST SENT");
                    viewHolder.requestButton.setEnabled(false);
                    return;
                }
                else{
                    // display message to warn user that they are not connected to the internet
                    Toast.makeText(context, "Please check your internet connection",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

//        viewHolder.declineBtn.setTag(position+1);
//        viewHolder.declineBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public  void onClick(View v){
//                Toast.makeText(context,"list view decline was clicked by Panchy in position "
//                        +position+" username is "+userList.get(position).getUsername(),Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }

    public class SearchViewHolder {

        public TextView userName;
        public Button requestButton;

    }

}
