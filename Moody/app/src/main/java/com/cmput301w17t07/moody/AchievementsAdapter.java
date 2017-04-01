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


/**
 * Created by anicn on 2017-03-31.
 */


package com.cmput301w17t07.moody;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

/**
 * Created by mike on 2017-03-08.
 */

public class AchievementsAdapter extends ArrayAdapter<String> {
    ArrayList<String> achievementTimeline;
    //ArrayList<String> achievementDescription;
    Context context;
    int layout_achievement_list;


    public AchievementsAdapter(Context context, int layout_achievement_list, ArrayList<String> achievementTimeline){
        super(context, layout_achievement_list, achievementTimeline);
        this.achievementTimeline = achievementTimeline;
        this.context = context;
        this.layout_achievement_list = layout_achievement_list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //achievements = AchievementController.getAchievements();


        // Do I need to check the view?

        String achievement = getItem(position); //do I need to override the getView position?

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.achievement_list, parent, false);
        }

        TextView achievementTitle = (TextView) convertView.findViewById(R.id.achievement_title);
        achievementTitle.setText(achievementTimeline.get(position));

//        try {
//            TextView descriptionTitle = (TextView) convertView.findViewById(R.id.achievement_short_description);
//            descriptionTitle.setText(achievementDescription.get(position));
//        }catch (Exception e) {
//            System.out.println("this is error "+e);
//        }
        ImageView achievementImage = (ImageView) convertView.findViewById(R.id.achievement_image);
        achievementImage.setImageResource(R.drawable.achievement2);


//        TextView achievementDescription = (TextView) convertView.findViewById(R.id.achievement_short_description);
//        achievementDescription.setText(mood.getFeeling());



        return convertView;
    }


}
