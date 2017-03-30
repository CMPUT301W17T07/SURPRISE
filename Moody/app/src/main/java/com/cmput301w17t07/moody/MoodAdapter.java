package com.cmput301w17t07.moody;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by mike on 2017-03-08.
 */

public class MoodAdapter extends ArrayAdapter<Mood> {

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
    public MoodAdapter(Context context, int layout_timeline_list, ArrayList<Mood> moodTimeline){
        super(context, layout_timeline_list, moodTimeline);
        this.filteredMoodTimeline = moodTimeline; //current 'filter' is whatever is passed in.
        this.context = context;
        this.layout_timeline_list = layout_timeline_list;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View view;
        MoodViewHolder viewHolder;

        Mood mood = getItem(position);

        if (convertView == null) {
            viewHolder=new MoodViewHolder();
            view=LayoutInflater.from(getContext()).inflate(R.layout.timeline_list, parent, false);
            viewHolder.userName=(TextView) view.findViewById(R.id.usernameTV);
            viewHolder.messageTest=(TextView) view.findViewById(R.id.messageTV);
            viewHolder.dataText=(TextView) view.findViewById(R.id.dateTV);
            viewHolder.emojiImage=(ImageView) view.findViewById(R.id.feelingEmoji);

            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(MoodViewHolder) view.getTag();
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

        //http://stackoverflow.com/questions/5683728/convert-java-util-date-to-string
        // Date: answered Apr 16 '11 at 1:02
        // Author: Charlie Salts
        Format formatter = new SimpleDateFormat("hh:mm a dd/MM/yyyy");
        String testDate = formatter.format(mood.getDate());

        viewHolder.dataText.setText(testDate);
        viewHolder.dataText.setTextSize(20);
        //        dateText.setTypeface(font);

        //todo get appropriate emoji images in the application and then can check mood.getFeeling() for proper emojis
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
                viewHolder.emojiImage.setImageResource(R.drawable.fear3);
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
                viewHolder.emojiImage.setImageResource(R.drawable.surprise2);
                break;
        }

        return view;
    }


    public class MoodViewHolder {
        public ImageView emojiImage;
        public TextView userName;
        public TextView messageTest;
        public TextView dataText;

    }





}
