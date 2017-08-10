package com.example.egi_fcb.jsonparsingwithokhttpexample.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.egi_fcb.jsonparsingwithokhttpexample.Model.MyDataModel;
import com.example.egi_fcb.jsonparsingwithokhttpexample.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by egi_fcb on 8/9/17.
 */

public class MyArrayAdapter extends ArrayAdapter<MyDataModel> {

    List<MyDataModel> modelList;
    Context context;
    private LayoutInflater mInflater;

    public MyArrayAdapter(Context context,List<MyDataModel> objects) {
        super(context, 0 , objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModel getItem(int position){
        return modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder vh;
        if (convertView == null){
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout)view);
            view.setTag(vh);
        }else {
            vh = (ViewHolder)convertView.getTag();
        }

        MyDataModel item = getItem(position);

        vh.textViewName.setText(item.getName());
        vh.textViewEmail.setText(item.getEmail());
        vh.textViewPhone.setText(item.getPhone());
        Picasso.with(context).load(item.getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);
        return vh.rootView;
    }

    private static class ViewHolder{
        public RelativeLayout rootView = null;
        public ImageView imageView = null;
        public TextView textViewName = null;
        public TextView textViewEmail = null ;
        public TextView textViewPhone = null ;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName, TextView textViewEmail, TextView textViewPhone){
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewName = textViewName;
            this.textViewEmail = textViewEmail;
            this.textViewPhone = textViewPhone;
        }

        public static ViewHolder create(RelativeLayout rootView){
            ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView);
            TextView textViewName = (TextView)rootView.findViewById(R.id.textViewName);
            TextView textViewEmail = (TextView)rootView.findViewById(R.id.textViewEmail);
            TextView textViewPhone = (TextView)rootView.findViewById(R.id.textViewPhone);
            return new ViewHolder(rootView, imageView, textViewName, textViewEmail, textViewPhone);
        }
    }
}
