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
 * Created by mike on 2017-03-08.
 */

/**
 * TimelineAdapter class object for the Moody application. Used to display moodlists in the user's
 * profile page, and in the FilterResultsActivity.
 */

public class TimelineAdapter extends ArrayAdapter<Mood> {

    ArrayList<Mood> filteredMoodTimeline;
    Context context;
    int layout_timeline_list;

    /**
     * Used https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
     * as a reference for constructing TimelineAdapter
     * Referenced by: Michael Simion 2017/03/7
     *
     * @param context
     * @param layout_timeline_list
     * @param moodTimeline
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
     * on feeling, etc.
     * @param position
     * @param convertView
     * @param parent
     * @return
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

        //        ImageView testImage = (ImageView) convertView.findViewById(R.id.profilePicture);
        //        testImage.setImageBitmap(mood.getMoodImage());

        viewHolder.userName.setText(mood.getDisplayUsername());
        viewHolder.userName.setTextSize(20);
        viewHolder.userName.getPaint().setFakeBoldText(true);
        //        username.setTypeface(font);

        viewHolder.messageTest.setText(mood.getMoodMessage());
        viewHolder.messageTest.setTextSize(20);
        //        feelingText.setTypeface(font);

        viewHolder.dataText.setText(mood.getDate().toString());
        viewHolder.dataText.setTextSize(20);
        //        dateText.setTypeface(font);

        //ImageView emojiImage = (ImageView) convertView.findViewById(R.id.feelingEmoji);

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
                viewHolder.emojiImage.setImageResource(R.drawable.fear);
                break;
            case "happiness":
                viewHolder.emojiImage.setImageResource(R.drawable.happy);
                break;
            case "sadness":
                viewHolder.emojiImage.setImageResource(R.drawable.sad);
                break;
            case "shame":
                viewHolder.emojiImage.setImageResource(R.drawable.shame);
                break;
            case "surprise":
                viewHolder.emojiImage.setImageResource(R.drawable.surprise);
                break;
        }

        return view;
    }

    /**
     * Idea for MoodViewHolder implementation stuff is based on
     * https://developer.android.com/training/improving-layouts/smooth-scrolling.html#AsyncTask
     * Taken by: Qi Pang 2017/03/10
     */
    public class MoodViewHolder {
        public ImageView emojiImage;
        public TextView userName;
        public TextView messageTest;
        public TextView dataText;

    }

}
