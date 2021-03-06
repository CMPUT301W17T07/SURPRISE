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

import io.searchbox.annotations.JestId;

/**
 * The FollowerList model class for the Moody application. Is utilized by the implementation of
 * user following capabilities.
 */

public class FollowerList {
    String username;
    public ArrayList<String> pendingFollowers = new ArrayList<String>();
    public ArrayList<String> followerList = new ArrayList<String>();

    @JestId

    private String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public FollowerList(String username) {
        this.username = username;
    }

    public void addPending(String username){
        // When another user requests to follow the user
        pendingFollowers.add(username);
    }

    public void deletePending(String username){
        // When a user rejects another user's follow request
        pendingFollowers.remove(username);
    }

    public int countPending(){
        return pendingFollowers.size();
    }

    public void addFollower(String username){
        this.deletePending(username);
        followerList.add(username);
    }

    public void deleteFollower(String username){
        // Current app design does not allow a user to delete a follower
        followerList.remove(username);
    }

    public int countFollowers(){
        return followerList.size();
    }

    public String getFollower(int index){
        return followerList.get(index);
    }

    public boolean hasFollower(String username){
        return followerList.contains(username);
    }
    //
    public boolean hasPending(String username){
        return pendingFollowers.contains(username);
    }


    public ArrayList<String> getPendingFollowers() {
        return pendingFollowers;
    }

    public ArrayList<String> getFollowerList() {
        return followerList;
    }
}