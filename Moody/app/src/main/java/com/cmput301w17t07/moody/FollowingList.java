package com.cmput301w17t07.moody;

import java.util.ArrayList;

/**
 * Created by mike on 2017-02-23.
 */

public class FollowingList {
    public ArrayList<User> followingList = new ArrayList<User>();
    Mood mood=new Mood("happy");


    public void addFollower(User user){
        followingList.add(user);
        
    }

    public void deleteFollower(User user){
        followingList.remove(user);
    }

    public int countFollower(){
        return followingList.size();
    }

    public User getFollower(int index){
        return followingList.get(index);
    }


}
