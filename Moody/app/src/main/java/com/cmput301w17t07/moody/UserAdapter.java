package com.cmput301w17t07.moody;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
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

public class UserAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;


    /**
     * creat a new peopleadapter
     * @param context
     * @param userList
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
        //private ImageView userImag;
        public TextView userName;
        public Button addBtn;



    }

}
