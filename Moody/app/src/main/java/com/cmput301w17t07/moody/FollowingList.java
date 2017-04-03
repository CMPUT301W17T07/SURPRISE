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
 * Created by mike on 2017-02-23.
 */

/**
 * The FollowingList model class for the Moody application. Will be utilized for implementation of
 * user following capabilities in part 5. Javadocs for methods will be included then, once
 * the model is implemented.
 */

public class FollowingList {
    String username;
    public ArrayList<String> followingList = new ArrayList<String>();

    @JestId

    private String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getFollowingList() {
        return followingList;
    }


    public FollowingList(String username) {
        this.username = username;
    }

    public void addFollowing(String username){
        // add a user to the user's following list
        followingList.add(username);

    }

    public void deleteFollowing(String username){
        // remove a user from the user's following list. Current app design does not allow a user
        // to remove another user from their following list
        followingList.remove(username);
    }

    public int countFollowing(){
        return followingList.size();
    }

    public String getFollowing(int index){
        // get specific user from following list
        return followingList.get(index);
    }

    public boolean hasFollowing(String username){
        return followingList.contains(username);
    }


}
