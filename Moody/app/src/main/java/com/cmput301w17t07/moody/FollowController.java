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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static com.cmput301w17t07.moody.ApplicationMoody.FILENAME;
import static com.cmput301w17t07.moody.ApplicationMoody.FOLLOWERS;
import static com.cmput301w17t07.moody.ApplicationMoody.FOLLOWING;
import static com.cmput301w17t07.moody.ApplicationMoody.PENDING;

/**
 * Created by mike on 2017-03-25.
 */

public class FollowController {

    private static ConnectivityManager manager;

    public static void createFollowLists(String username){
        // method is only invoked when user first registers his/her account

        // creating list class objects
        FollowerList followerList = new FollowerList(username);
        FollowingList followingList = new FollowingList(username);

        // Creating the elasticSearch controllers for adding the lists to the server
        ElasticSearchFollowController.AddFollowerList addFollowerList =
                new ElasticSearchFollowController.AddFollowerList();
        ElasticSearchFollowController.AddFollowingList addFollowingList =
                new ElasticSearchFollowController.AddFollowingList();

        // adding lists to server
        addFollowerList.execute(followerList);
        addFollowingList.execute(followingList);

    }

    public static boolean sendPendingRequest(String userSendingRequest, String userReceivingRequest){
        if(checkNetwork()){
            // if connection is present....

            // Get the FollowerList of the userReceiving the request from the server
            FollowerList followerList = null;

            followerList = getFollowerList(userReceivingRequest);

            // adding the pending username to the followerlist
            followerList.addPending(userSendingRequest);

            //----------------------- NOW UPDATING THE SERVER --------------------------------------

            ElasticSearchFollowController.DeleteFollowerList deleteFollowerList =
                    new ElasticSearchFollowController.DeleteFollowerList();
            ElasticSearchFollowController.AddFollowerList addFollowerList =
                    new ElasticSearchFollowController.AddFollowerList();

            // deleting old followerlist
            deleteFollowerList.execute(userReceivingRequest);
            // adding updated one
            addFollowerList.execute(followerList);

            // DONE
            return true;
        }
        else{
            // user will be notified that he/she needs to connect to the internet
            return false;
        }
    }

    public static boolean canRequestBeSent(String userSendingRequest, String userReceivingRequest){
        //method determines if a request can be sent between userSendingRequest and userReceivingRequest
        // can modify method to return int values if we want context specific responses for each situation

        if(userSendingRequest.equals(userReceivingRequest)){
            return false;
        }

        FollowerList followerList = null;
        // getting the follower list of the user who will be receiving the request
        followerList = getFollowerList(userReceivingRequest);

        if(followerList.hasPending(userSendingRequest)){
            return false;
        }

        if(followerList.hasFollower(userSendingRequest)){
            return false;
        }

        return true;

    }


    public static boolean acceptFollowRequest(String userAcceptingRequest, String userThatSentRequest){
        if(checkNetwork()){
            // if connection is present....

            //-------------------- UPDATING FOLLOWER LIST FOR ACCEPTING USER ----------------------


            FollowerList followerList = getFollowerList(userAcceptingRequest);

            // adding follower to the accepting user's follower list

            //todo try catch this block
            followerList.addFollower(userThatSentRequest);

            //----------------------- NOW UPDATING THE SERVER --------------------------------------

            ElasticSearchFollowController.DeleteFollowerList deleteFollowerList =
                    new ElasticSearchFollowController.DeleteFollowerList();
            ElasticSearchFollowController.AddFollowerList addFollowerList =
                    new ElasticSearchFollowController.AddFollowerList();

            // deleting old followerlist
            deleteFollowerList.execute(userAcceptingRequest);
            // adding updated one
            addFollowerList.execute(followerList);


            //--------------- UPDATING FOLLOWING LIST FOR USER WHO SENT REQUEST --------------------

            FollowingList followingList = getFollowingList(userThatSentRequest);

            // todo try catch this block
            followingList.addFollowing(userAcceptingRequest);

            //----------------------- NOW UPDATING THE SERVER --------------------------------------

            ElasticSearchFollowController.DeleteFollowingList deleteFollowingList =
                    new ElasticSearchFollowController.DeleteFollowingList();
            ElasticSearchFollowController.AddFollowingList addFollowingList =
                    new ElasticSearchFollowController.AddFollowingList();

            // deleting old following list
            deleteFollowingList.execute(userThatSentRequest);
            // adding update one
            addFollowingList.execute(followingList);

            //------------------------------ DONE --------------------------------------------------
            return true;
        }
        else{
            // todo notify user that they are not connected to the internet!
            return false;
        }
    }

    public static boolean declineFollowRequest(String userDecliningRequest, String userThatSentRequest){

        if(checkNetwork()){

            //----------------------- REMOVING PENDING USER --------------------------------------

            FollowerList followerList = getFollowerList(userDecliningRequest);

            // removing the requester from the user's pending lust
            followerList.deletePending(userThatSentRequest);


            //----------------------- NOW UPDATING THE SERVER --------------------------------------

            ElasticSearchFollowController.DeleteFollowerList deleteFollowerList =
                    new ElasticSearchFollowController.DeleteFollowerList();
            ElasticSearchFollowController.AddFollowerList addFollowerList =
                    new ElasticSearchFollowController.AddFollowerList();

            // deleting old followerlist
            deleteFollowerList.execute(userDecliningRequest);
            // adding updated one
            addFollowerList.execute(followerList);

            //------------------------------ DONE --------------------------------------------------

            return true;
        }
        else{
            //todo notify the user that he or she is not connected to the internet
            return false;
        }

    }


    public static ArrayList<String> getPendingRequests(String username){

        // todo need to check for internet here

        FollowerList followerList = null;
        ArrayList<String> userArrayList=new ArrayList<>();


        // ElasticController to retrieve pending followers list
        ElasticSearchFollowController.GetFollowerList getFollowerList =
                new ElasticSearchFollowController.GetFollowerList();

        getFollowerList.execute(username);

        try {

            followerList = getFollowerList.get();
            userArrayList = followerList.getPendingFollowers();

        } catch (Exception e) {
            Log.i("error", "failed to get the FollowerList out of the async matched");
        }

        return userArrayList;
    }

    public static String getNumberOfRequests(String username, Context context){
        String pendingCount;
        if(checkNetwork()) {
            ArrayList<String> userArrayList = getPendingRequests(username);
            pendingCount = String.valueOf(userArrayList.size());
//            savePendingInfo(pendingCount, context);
        }
        else{
            pendingCount = readPendingInfo(context).toString();
        }
        return pendingCount;
    }


    public static FollowerList getFollowerList(String username){
        FollowerList followerList = null;

        ElasticSearchFollowController.GetFollowerList getFollowerList =
                new ElasticSearchFollowController.GetFollowerList();
        getFollowerList.execute(username);

        // trying to retrieve the follower list
        try {
            followerList = getFollowerList.get();;
        } catch (Exception E){
            Log.i("Error", "Was unable to retrieve follower list during getFollowerList()");
        }

        return followerList;
    }

    public static FollowingList getFollowingList(String username){

        FollowingList followingList = null;

        ElasticSearchFollowController.GetFollowingList getFollowingList =
                new ElasticSearchFollowController.GetFollowingList();
        getFollowingList.execute(username);

        // trying to retrieve following list
        try {
            followingList = getFollowingList.get();
        } catch (Exception E){
            Log.i("Error", "Was unable to retrieve following list during getFollowingList()");
        }

        return followingList;
    }

    public static String getNumberOfFollowers(String username, Context context){
        String followerCount;
        if(checkNetwork()) {
            FollowerList followerList = getFollowerList(username);
            followerCount = String.valueOf(followerList.countFollowers());
//            saveFollowerInfo(followerCount, context);
        }
        else{
            followerCount = readFollowerInfo(context).toString();
        }
        return followerCount;
    }

    public static String getNumberOfFollowing(String username, Context context){
        String followingCount;
        if(checkNetwork()) {
            FollowingList followingList = getFollowingList(username);
            followingCount = String.valueOf(followingList.countFollowing());
//            saveFollowingInfo(followingCount, context);
        }
        else{
            followingCount = readFollowingInfo(context).toString();

        }
        return followingCount;
    }

    private static boolean checkNetwork(){
//        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = manager.getActiveNetworkInfo();
//        if (info == null) {
//            return false;
//        } else {
//            return true;
//        }
        return true;
    }


    //todo make two generic functions because having 6 functions is a dumb way to go
    public static void saveFollowerInfo(String followers, Context ctx) {
        FileOutputStream outputFollowerStream;
        try {
            // saving data to files
            outputFollowerStream = ctx.openFileOutput(FOLLOWERS, Context.MODE_PRIVATE);
            outputFollowerStream.write(followers.getBytes());
            outputFollowerStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static StringBuffer readFollowerInfo(Context ctx) {
        FileInputStream inputFollowerStream;
        StringBuffer fileFollowerContent = new StringBuffer("");
        int n;
        byte[] buffer = new byte[1024];
        try {
            inputFollowerStream = ctx.openFileInput(FOLLOWERS);
            while ((n = inputFollowerStream.read(buffer)) != -1)
            {
                fileFollowerContent.append(new String(buffer, 0, n));
            }
            inputFollowerStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileFollowerContent;
    }

    public static void saveFollowingInfo(String following, Context ctx) {
        FileOutputStream outputFollowingStream;
        try {
            // saving data to files
            outputFollowingStream = ctx.openFileOutput(FOLLOWING, Context.MODE_PRIVATE);
            outputFollowingStream.write(following.getBytes());
            outputFollowingStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static StringBuffer readFollowingInfo(Context ctx) {
        FileInputStream inputFollowingStream;
        StringBuffer fileFollowingContent = new StringBuffer("");
        int n;
        byte[] buffer = new byte[1024];
        try {
            inputFollowingStream = ctx.openFileInput(FOLLOWING);
            while ((n = inputFollowingStream.read(buffer)) != -1)
            {
                fileFollowingContent.append(new String(buffer, 0, n));
            }
            inputFollowingStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileFollowingContent;
    }

    public static void savePendingInfo(String pending, Context ctx) {
        FileOutputStream outputPendingStream;
        try {
            // saving data to file
            outputPendingStream = ctx.openFileOutput(PENDING, Context.MODE_PRIVATE);
            outputPendingStream.write(pending.getBytes());
            outputPendingStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static StringBuffer readPendingInfo(Context ctx) {
        FileInputStream inputPendingStream;
        StringBuffer filePendingContent = new StringBuffer("");
        int n;
        byte[] buffer = new byte[1024];
        try {
            inputPendingStream = ctx.openFileInput(PENDING);
            while ((n = inputPendingStream.read(buffer)) != -1)
            {
                filePendingContent.append(new String(buffer, 0, n));
            }
            inputPendingStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePendingContent;
    }

}
