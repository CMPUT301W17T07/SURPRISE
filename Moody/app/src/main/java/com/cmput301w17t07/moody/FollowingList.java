package com.cmput301w17t07.moody;

import java.util.ArrayList;

/**
 * Created by mike on 2017-02-23.
 */

public class FollowingList {
    public ArrayList<User> pendingFollowing = new ArrayList<User>();
    public ArrayList<User> followingList = new ArrayList<User>();

    public void addPending(User user){
        // When a user sends a follow request to another user
        pendingFollowing.add(user);
    }

    public void deletePending(User user){
        // When a user's follow request is rejected by another user
        pendingFollowing.remove(user);
    }

    public void countPending(){
        pendingFollowing.size();
    }

    public void getPending(int index){
        pendingFollowing.get(index);
    }

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
