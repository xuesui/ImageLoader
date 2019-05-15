package com.example.imageloader.images;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.imageloader.MainActivity;
import com.example.imageloader.R;
import com.example.imageloader.imageloader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageAdapter extends BaseAdapter{
    private List<String> UrlList=new ArrayList<>();
    private Context context;
    LayoutInflater layoutInflater;
    ImageLoader imageLoader;

    public ImageAdapter(Context context,List<String>urlList){
        super();
        this.context=context;
        this.UrlList=urlList;
    }

    @Override
    public int getCount() {
        return UrlList.size();
    }

    @Override
    public String getItem(int position) {
        return UrlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.image_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        ImageView imageView=holder.imageView;
        final String url=getItem(position);
         imageView.setTag(url);
         imageLoader.bindBitmap(url,imageView,100,100);
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        public ViewHolder(View convertView){
            imageView=(ImageView)convertView.findViewById(R.id.images);
        }
    }
}
