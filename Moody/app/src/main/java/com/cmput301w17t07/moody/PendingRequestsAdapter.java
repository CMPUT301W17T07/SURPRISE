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

/**
 * Created by mike on 2017-03-25.
 */

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
 * Implementation of part of this class was partially inspired by the Android developer's web
 * page on ListView Scrolling Smoothly:
 * https://developer.android.com/training/improving-layouts/smooth-scrolling.html#AsyncTask
 * Taken By: Qi Pang 2017/03/10
 */

public class PendingRequestsAdapter extends BaseAdapter {
    private Context context;
    private List<String> userList;
    private String username;


    /**
     * Constructor for the UserAdapter
     * @param context       Context for the adapter
     * @param userList      The list of users
     */
    public PendingRequestsAdapter(Context context, List<String> userList, String username) {
        this.context = context;
        this.userList = userList;
        this.username = username;
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
            view = LayoutInflater.from(context).inflate(R.layout.single_pending_request,null);
            viewHolder.userName=(TextView) view.findViewById(R.id.pendingRequestUsername);
            viewHolder.acceptRequestButton=(Button) view.findViewById(R.id.acceptRequest);
            viewHolder.declineRequestButton=(Button) view.findViewById(R.id.declineRequest);

            //viewHolder.declineBtn=(Button) view.findViewById(R.id.searchDecline);

            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(SearchViewHolder) view.getTag();
        }

        // Initializing the FollowController
        final FollowController followController = new FollowController();

        viewHolder.userName.setText(userList.get(position));
        viewHolder.userName.setTextSize(30);

        //-------------------------------- USER ACCEPTS REQUEST ------------------------------------
        viewHolder.acceptRequestButton.setTag(position);
        viewHolder.acceptRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v){
                Toast.makeText(context, userList.get(position), Toast.LENGTH_SHORT).show();

               if(followController.acceptFollowRequest(username, userList.get(position))) {
                   // if true.. connected to the internet
                   //todo change color to whatever background color is
                   viewHolder.acceptRequestButton.setText("ACCEPTED");
                   //                viewHolder.acceptRequestButton.setWidth(100);
                   //                viewHolder.acceptRequestButton.setBackgroundColor(context.getResources().getColor(R.color.blueTheme));
                   viewHolder.acceptRequestButton.setEnabled(false);
                   //                viewHolder.declineRequestButton.setBackgroundColor(context.getResources().getColor(R.color.blueTheme));
                   viewHolder.declineRequestButton.setEnabled(false);

               }
               else{
                   Toast.makeText(context, "please check network connection", Toast.LENGTH_SHORT).show();

               }


            }
        });

        //-------------------------------- USER DECLINES REQUEST ------------------------------------
        viewHolder.declineRequestButton.setTag(position);
        viewHolder.declineRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v){
                //todo implement decline follow request
            }
        });


        return view;
    }

    public class SearchViewHolder {

        public TextView userName;
        public Button acceptRequestButton;
        public Button declineRequestButton;

    }

}
