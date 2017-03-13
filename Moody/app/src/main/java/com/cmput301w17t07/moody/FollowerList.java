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
 * The FollowerList model class for the Moody application. Will be utilized for implementation of
 * user following capabilities in part 5. Javadocs for methods will be included then, once
 * the model is implemented.
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

    public int countPending(){
        return pendingFollowers.size();
    }

    public User getPending(int index){
        return pendingFollowers.get(index);
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

    public boolean hasFollower(User user){
        return followerList.contains(user);
    }

    public boolean hasPending(User user){
        return pendingFollowers.contains(user);
    }

}
