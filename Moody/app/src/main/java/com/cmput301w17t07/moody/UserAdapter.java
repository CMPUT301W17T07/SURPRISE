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


    /**
     * Constructor for the UserAdapter
     * @param context       Context for the adapter
     * @param userList      The list of users
     */
    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
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
        SearchViewHolder viewHolder;


        if (convertView == null) {
            viewHolder =new SearchViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.single_search_list,null);
            viewHolder.userName=(TextView) view.findViewById(R.id.singleSearchItemName);
            viewHolder.addBtn=(Button) view.findViewById(R.id.searchAdd);
            //viewHolder.declineBtn=(Button) view.findViewById(R.id.searchDecline);

            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(SearchViewHolder) view.getTag();
        }


        viewHolder.userName.setText(userList.get(position).getUsername());
        viewHolder.userName.setTextSize(30);

        viewHolder.addBtn.setTag(position);
        viewHolder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v){
                Toast.makeText(context,"list view add button was clicked by Panchy in position "
                        +position+" username is "+userList.get(position).getUsername(),Toast.LENGTH_SHORT).show();

                //Intent intent=((Activity) context).getIntent();
                //final String username= intent.getStringExtra("userNameBegin");



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
        public Button addBtn;

    }

}
