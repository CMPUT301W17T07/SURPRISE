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
 * MoodAdapter is used to display the different mood posts in our ListView
 */

public class MoodAdapter extends ArrayAdapter<Mood> {

    ArrayList<Mood> filteredMoodTimeline;
    Context context;
    int layout_timeline_list;

    /**
     * Used https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
     * as a reference <br>
     * @param context <br>
     * @param layout_timeline_list <br>
     * @param moodTimeline <br>
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
            viewHolder.locationImage=(ImageView) view.findViewById(R.id.haslocation);
            viewHolder.imageIc=(ImageView) view.findViewById(R.id.hasimage);
            viewHolder.withP=(ImageView) view.findViewById(R.id.withpeopleTV);

            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(MoodViewHolder) view.getTag();
        }

        viewHolder.userName.setText(mood.getDisplayUsername());
        viewHolder.userName.setTextSize(20);
        viewHolder.userName.getPaint().setFakeBoldText(true);
        viewHolder.messageTest.setText(mood.getMoodMessage());
        viewHolder.messageTest.setTextSize(20);

        // http://stackoverflow.com/questions/5683728/convert-java-util-date-to-string
        // Date: answered Apr 16 '11 at 1:02
        // Author: Charlie Salts
        // Taken by: Michael Simion 2017/03/5
        Format formatter = new SimpleDateFormat("hh:mm a dd/MM/yyyy");
        String testDate = formatter.format(mood.getDate());

        viewHolder.dataText.setText(testDate);
        viewHolder.dataText.setTextSize(15);

        if (mood.getLongitude()!=0.0 && mood.getLatitude()!=0.0){
            viewHolder.locationImage.setImageResource(R.drawable.ic_location);

        }else {
            viewHolder.locationImage.setImageResource(0);
        }

        if (mood.encodedImage !=null){
            viewHolder.imageIc.setImageResource(R.drawable.ic_camera);
        }else {
            viewHolder.imageIc.setImageResource(0);
        }

        if(mood.getSocialSituation() != ""){
            viewHolder.withP.setImageResource(R.drawable.ic_social);
        }else{
            viewHolder.withP.setImageResource(0);
        }

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


    public class MoodViewHolder {
        public ImageView emojiImage;
        public TextView userName;
        public TextView messageTest;
        public TextView dataText;
        public ImageView locationImage;
        public ImageView imageIc;
        public ImageView withP;

    }

}
