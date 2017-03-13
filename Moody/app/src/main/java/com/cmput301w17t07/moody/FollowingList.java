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

import java.util.ArrayList;

/**
 * Created by mike on 2017-02-23.
 */

/**
 * The FollowingList model class for the Moody application. Will be utilized for implementation of
 * user following capabilities in part 5. Javadocs for methods will be included then, once
 * the model is implemented.
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

    public int countPending(){
        // method should not be used, as the number of pending requests a user sends out is not kept track of
        return pendingFollowing.size();
    }

    public User getPending(int index){
        return pendingFollowing.get(index);
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
