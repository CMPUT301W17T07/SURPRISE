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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * TimelineAdapter class object for the Moody application. Used to display moodLists in the user's
 * profile page, and in the FilterResultsActivity.
 */

public class TimelineAdapter extends ArrayAdapter<Mood> {

    ArrayList<Mood> filteredMoodTimeline;
    Context context;
    int layout_timeline_list;

    /**
     * Used https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
     * as a reference for constructing TimelineAdapter
     * Referenced by: Michael Simion 2017/03/7 <br>
     *
     * @param context <br>
     * @param layout_timeline_list <br>
     * @param moodTimeline <br>
     */
    public TimelineAdapter(Context context, int layout_timeline_list, ArrayList<Mood> moodTimeline) {
        super(context, layout_timeline_list, moodTimeline);
        this.filteredMoodTimeline = moodTimeline; //current 'filter' is whatever is passed in.
        this.context = context;
        this.layout_timeline_list = layout_timeline_list;

    }

    /**
     * Override of the getView function in ArrayAdapter that allows us to display the appropriate
     * properties on the listview of our current view. i.e. moodMessage, proper emoji based
     * on feeling, etc. <br>
     * @param position <br>
     * @param convertView <br>
     * @param parent <br>
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        MoodViewHolder viewHolder;

        Mood mood = getItem(position);

        if (convertView == null) {
            viewHolder = new MoodViewHolder();
            view = LayoutInflater.from(getContext()).inflate(R.layout.timeline_list, parent, false);
            viewHolder.userName = (TextView) view.findViewById(R.id.usernameTV);
            viewHolder.messageTest = (TextView) view.findViewById(R.id.messageTV);
            viewHolder.dataText = (TextView) view.findViewById(R.id.dateTV);
            viewHolder.emojiImage = (ImageView) view.findViewById(R.id.feelingEmoji);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (MoodViewHolder) view.getTag();
        }

        viewHolder.userName.setText(mood.getDisplayUsername());
        viewHolder.userName.setTextSize(20);
        viewHolder.userName.getPaint().setFakeBoldText(true);

        viewHolder.messageTest.setText(mood.getMoodMessage());
        viewHolder.messageTest.setTextSize(20);

        viewHolder.dataText.setText(mood.getDate().toString());
        viewHolder.dataText.setTextSize(20);

        switch (mood.getFeeling()) {
            case "anger":
                viewHolder.emojiImage.setImageResource(R.drawable.angry);
                break;
            case "confusion":
                viewHolder.emojiImage.setImageResource(R.drawable.confused);
                break;
            case "disgust":
                viewHolder.emojiImage.setImageResource(R.drawable.disgust);
                break;
            case "fear":
                viewHolder.emojiImage.setImageResource(R.drawable.fear3);
                break;
            case "happiness":
                viewHolder.emojiImage.setImageResource(R.drawable.happy);
                break;
            case "sadness":
                viewHolder.emojiImage.setImageResource(R.drawable.sad);
                break;
            case "shame":
                viewHolder.emojiImage.setImageResource(R.drawable.shame2);
                break;
            case "surprise":
                viewHolder.emojiImage.setImageResource(R.drawable.surprise2);
                break;
        }

        return view;
    }

    /**
     * Idea for MoodViewHolder implementation stuff is based on <br>
     * https://developer.android.com/training/improving-layouts/smooth-scrolling.html#AsyncTask <br>
     * Taken by: Qi Pang 2017/03/10 <br>
     */
    public class MoodViewHolder {
        public ImageView emojiImage;
        public TextView userName;
        public TextView messageTest;
        public TextView dataText;

    }

}
