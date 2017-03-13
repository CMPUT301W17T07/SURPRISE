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

public class TimelineAdapter extends ArrayAdapter<Mood> {
    ArrayList<Mood> moodTimeline;
    ArrayList<Mood> filteredMoodTimeline;
    Context context;
    int layout_timeline_list;

    /**
     * Used https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
     * as a reference
     * @param context
     * @param layout_timeline_list
     * @param moodTimeline
     */
    public TimelineAdapter(Context context, int layout_timeline_list, ArrayList<Mood> moodTimeline){
        super(context, layout_timeline_list, moodTimeline);
        this.filteredMoodTimeline = moodTimeline; //current 'filter' is whatever is passed in.
        this.context = context;
        this.layout_timeline_list = layout_timeline_list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        Mood mood = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.timeline_list, parent, false);
        }

//        ImageView testImage = (ImageView) convertView.findViewById(R.id.profilePicture);
//        testImage.setImageBitmap(mood.getMoodImage());

        TextView username = (TextView) convertView.findViewById(R.id.usernameTV);
        username.setText(mood.getDisplayUsername());
//        username.setTypeface(font);

        TextView feelingText = (TextView) convertView.findViewById(R.id.messageTV);
        feelingText.setText(mood.getMoodMessage());
//        feelingText.setTypeface(font);

        TextView dateText = (TextView) convertView.findViewById(R.id.dateTV);
        dateText.setText(mood.getDate().toString());
//        dateText.setTypeface(font);

        //todo get appropriate emoji images in the application and then can check mood.getFeeling() for proper emojis
        ImageView emojiImage = (ImageView) convertView.findViewById(R.id.feelingEmoji);
        //emojiImage.setImageResource(R.drawable.disgust);
        switch (mood.getFeeling()) {
            case "anger":
                emojiImage.setImageResource(R.drawable.angry);
                break;
            case "confusion":
                emojiImage.setImageResource(R.drawable.confused);
                break;
            case "disgust":
                emojiImage.setImageResource(R.drawable.disgust);
                break;
            case "fear":
                emojiImage.setImageResource(R.drawable.fear);
                break;
            case "happiness":
                emojiImage.setImageResource(R.drawable.happy);
                break;
            case "sadness":
                emojiImage.setImageResource(R.drawable.sad);
                break;
            case "shame":
                emojiImage.setImageResource(R.drawable.shame);
                break;
            case "surprise":
                emojiImage.setImageResource(R.drawable.surprise);
                break;
        }

        return convertView;
    }








}
