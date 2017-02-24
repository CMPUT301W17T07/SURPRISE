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

    public void addFollowing(User user){
        // add a user to the user's following list
        if(pendingFollowing.contains(user) && !(followingList.contains(user))){
            // only adds user
            this.deletePending(user);
            followingList.add(user);
        }
        else{
            // throw an exception if the user being added to the list is not contained in the pending list
            // or if the user is already in the followinglist
            throw new IllegalArgumentException();
        }

    }

    public void deleteFollowing(User user){
        // remove a user from the user's following list. Current app design does not allow a user
        // to remove another user from their following list
        followingList.remove(user);
    }

    public int countFollowing(){
        return followingList.size();
    }

    public User getFollowing(int index){
        // get specific user from following list
        return followingList.get(index);
    }

    public boolean hasFollowing(User user){
        return followingList.contains(user);
    }

    public boolean hasPending(User user){
        return pendingFollowing.contains(user);
    }

}
