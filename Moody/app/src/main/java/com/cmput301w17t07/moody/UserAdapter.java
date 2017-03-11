package com.cmput301w17t07.moody;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mike on 2017-02-24.
 */

public class UserAdapter extends BaseAdapter{
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=View.inflate(context,R.layout.list_of_search,null);
        TextView name=(TextView) v.findViewById(R.id.singleSearchItem);

        name.setText(userList.get(position).getUsername());
        name.setTextSize(20);


        v.setTag(userList.get(position));
        return v;
    }

}
