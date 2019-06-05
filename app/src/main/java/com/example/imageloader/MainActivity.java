package com.example.imageloader;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.imageloader.imageloader.ImageLoader;
import com.example.imageloader.images.ImageAdapter;
import com.example.imageloader.images.Images;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private List<Images> imagesList=new ArrayList<>();
    public static final String URL="http://gank.io/api/data/%E7%A6%8F%E5%88%A9/0/0";
    private static final String TAG="JSON";
    private ImageLoader imageLoader;
    String[] imageUrl;
    ExecutorService fisxedThreadPool= Executors.newFixedThreadPool(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Runnable runnable1=new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url(URL).build();
                String responseData = null;
                try {
                    Response response=client.newCall(request).execute();
                    responseData=response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (responseData!=null){
                    parseJSONWithJSONObject(responseData);

                }else {
                    Log.w(TAG, "json返回数据为null");
                }
            }
        };
        fisxedThreadPool.execute(runnable1);
        initImages();
        ImageAdapter imageAdapter=new ImageAdapter(this,R.layout.image_item,imagesList);
        GridView gridView=(GridView)findViewById(R.id.gridview);
        gridView.setAdapter(imageAdapter);
    }

    private void parseJSONWithJSONObject(String jsonData){
        try {
            JSONObject rootObject=new JSONObject(jsonData);
            JSONObject paramzObject=rootObject.getJSONObject("paramz");
            JSONArray jsonArray=paramzObject.getJSONArray("results");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                imageUrl[i]=jsonObject.getString("url");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initImages(){
        Runnable runnable2=new Runnable() {
            @Override
            public void run() {
                Bitmap[] bitmap;
                for (int i=0;i<imageUrl.length;i++){
                    bitmap=new Bitmap[imageUrl.length];
                    bitmap[i]=imageLoader.loadBitmap(imageUrl[i],200,200);
                    Images images=new Images(bitmap[i]);
                    imagesList.add(images);
                }
            }
        };
        fisxedThreadPool.execute(runnable2);
    }
}

