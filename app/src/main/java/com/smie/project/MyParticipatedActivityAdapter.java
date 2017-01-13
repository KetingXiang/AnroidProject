package com.smie.project;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by dell1150 on 2017/1/14.
 */


import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * Created by zackzhao on 2016/12/11.
 */

public class MyParticipatedActivityAdapter extends BaseAdapter{
    private Context context;
    private List<MenuPersonItem> list;

    public MyParticipatedActivityAdapter(Context context , List<MenuPersonItem>list ){
        this.context = context;
        this.list = list;

    }
    @Override
    public int getCount() {
        if (list == null){
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list == null){
            return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View convertView;
        MyParticipatedActivityAdapter.ViewHolder viewHolder;
        if (view == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_menu_main_item,null);
            viewHolder = new MyParticipatedActivityAdapter.ViewHolder();
            viewHolder.menu_personItemId = (ImageView) convertView.findViewById(R.id.menu_head_sculpture);
            viewHolder.menu_location_icon = (ImageView) convertView.findViewById(R.id.menu_main_item_location_icon);
            viewHolder.menu_personName = (TextView)convertView.findViewById(R.id.menu_person_name);



            viewHolder.menu_personAddress = (TextView)convertView.findViewById(R.id.menu_person_address);
            viewHolder.menu_evaluate = (TextView)convertView.findViewById(R.id.menu_evaluate);
            viewHolder.menu_evaluta_bar = (RatingBar)convertView.findViewById(R.id.menu_evaluate_bar);
            viewHolder.menu_personDescription = (TextView)convertView.findViewById(R.id.menu_brief_description);
            viewHolder.menu_go_to_connect = (TextView)convertView.findViewById(R.id.menu_go_to_connect);
            convertView.setTag(viewHolder);
        }else{
            convertView = view;
            viewHolder = (MyParticipatedActivityAdapter.ViewHolder)convertView.getTag();
        }


        /*
        viewHolder.menu_personItemId.setImageResource(list.get(position).getMenu_personItemId());
        viewHolder.menu_location_icon.setImageResource(list.get(position).getMenu_location_icon());
        */



        viewHolder.menu_personAddress.setText(list.get(position).getMenu_personAddress());
//        viewHolder.menu_evaluate.setText(list.get(position).getMenu_evaluate());
        viewHolder.menu_evaluta_bar.setRating(list.get(position).getMenu_evaluta_bar());
        viewHolder.menu_personDescription.setText(list.get(position).getMenu_personDescription());
        viewHolder.menu_go_to_connect.setText(list.get(position).getMenu_go_to_connect());
        return convertView;
    }
    private class ViewHolder{
        private ImageView menu_personItemId;
        private ImageView menu_location_icon;
        private TextView menu_personName;
        private TextView menu_personAddress;
        private TextView menu_evaluate;
        private RatingBar menu_evaluta_bar;
        private TextView menu_personDescription;
        private TextView menu_go_to_connect;
    }
    /**
     *
     */
}


//    public class MyParticipatedActivityAdapter extends BaseAdapter {
//        private Context context;
//        private List<MenuPersonItem> list;
//
//        public MyParticipatedActivityAdapter(Context context , List<MenuPersonItem>list ){
//            this.context = context;
//            this.list = list;
//        }
//        @Override
//        public int getCount() {
//            if (list == null){
//                return 0;
//            }
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            if (list == null){
//                return null;
//            }
//            return list.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View view, ViewGroup parent) {
//            View convertView;
//            com.smie.project.MyParticipatedActivityAdapter.ViewHolder viewHolder;
//            if (view == null){
//                convertView = LayoutInflater.from(context).inflate(R.layout.activity_menu_main_item,null);
//                viewHolder = new com.smie.project.MyParticipatedActivityAdapter.ViewHolder();
//                viewHolder.MyParticipatedActivityName = (TextView) convertView.findViewById(R.id.myParticipatedPlace);
//
//                convertView.setTag(viewHolder);
//            }else{
//                convertView = view;
//                viewHolder = (com.smie.project.MyParticipatedActivityAdapter.ViewHolder)convertView.getTag();
//            }
//
//            viewHolder.MyParticipatedActivityName.setText(list.get(position).getMyParticipatedActivityName());
//            return convertView;
//        }
//        private class ViewHolder{
//            private TextView MyParticipatedActivityName;
//
//        }
//        /**
//         *
//         */
//    }
