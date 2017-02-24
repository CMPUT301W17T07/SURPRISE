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
        if(pendingFollowers.contains(user)) {
            this.deletePending(user);
            followerList.add(user);
        }
        else{
            // if attempting to add a follower who is not in the user's pending requests
            throw new IllegalArgumentException();
        }
    }

    public void deleteFollower(User user){
        // Current app design does not allow a user to delete a follower
        followerList.remove(user);
    }

    public int countFollowers(){
        return followerList.size();
    }

    public User getFollower(int index){
        return followerList.get(index);
    }

}
