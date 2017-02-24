package com.cmput301w17t07.moody;

import java.util.ArrayList;

/**
 * Created by mike on 2017-02-23.
 */

public class FollowerList {
    public ArrayList<User> pendingFollowers = new ArrayList<User>();
    public ArrayList<User> followerList = new ArrayList<User>();

    public void addPending(User user){
        // When another user requests to follow the user
        pendingFollowers.add(user);
    }

    public void deletePending(User user){
        // When a user rejects another user's follow request
        pendingFollowers.remove(user);
    }

    public void countPending(){
        pendingFollowers.size();
    }

    public void getPending(int index){
        pendingFollowers.get(index);
    }

    public void addFollower(User user){
        followerList.add(user);
    }

    public void deleteFollower(User user){
        followerList.remove(user);
    }

    public int countFollower(){
        return followerList.size();
    }

    public User getFollower(int index){
        return followerList.get(index);
    }

}
