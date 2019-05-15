package com.example.imageloader;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;

import com.example.imageloader.imageloader.ImageLoader;
import com.example.imageloader.images.ImageAdapter;

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
    public static final String URL="http://gank.io/api/data/%E7%A6%8F%E5%88%A9/0/0";
    private static final String TAG="JSON";
    private ImageLoader imageLoader;
    List<String> photoList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread=new Thread(new Runnable() {
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
        });
        thread.start();

        ImageAdapter imageAdapter=new ImageAdapter(MainActivity.this,photoList);
        ListView listView=(ListView)findViewById(R.id.gridview);
        listView.setAdapter(imageAdapter);
    }

    private void parseJSONWithJSONObject(String jsonData){
        try {
            JSONObject rootObject=new JSONObject(jsonData);
            String result=rootObject.getString("results");
            JSONArray jsonArray=new JSONArray(result);
            for (int i=0;i<jsonArray.length();i++){
                Log.d(TAG, "parseJSONWithJSONObject: "+i);
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                photoList.add(jsonObject.getString("url"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

