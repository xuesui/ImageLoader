package com.example.imageloader.images;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.imageloader.R;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<Images> {
    private int resourceId;

    public ImageAdapter(Context context, int resource,List<Images> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        Images images=getItem(position);
        View view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView imageView=(ImageView)view.findViewById(R.id.images);
        imageView.setImageBitmap(images.getBitmap());
        return view;
    }


}
