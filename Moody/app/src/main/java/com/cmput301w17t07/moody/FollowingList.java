package com.cmput301w17t07.moody;

import java.util.ArrayList;

/**
 * Created by mike on 2017-02-23.
 */

public class FollowingList {
    public ArrayList<User> followingList = new ArrayList<User>();

    public void addFolloer(User user){
        followingList.add(user);
    }

    public void deleteFolloer(User user){
        followingList.remove(user);
    }

    public int countFolloer(){
        return followingList.size();
    }

    public User getFolloer(int index){
        return followingList.get(index);
    }
}
