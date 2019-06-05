package com.example.imageloader;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.imageloader.Imageloadertool.ImageLoader;
import com.example.imageloader.utils.ImageAdapter;
import com.example.imageloader.utils.ImageEntity;
import com.example.imageloader.utils.ImageService;
import com.example.imageloader.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ImageLoader imageLoader=ImageLoader.build(this);
    private static final String BASE_URL="http://gank.io/api/";
    private List<String> imageUrl=new ArrayList<>();
    private List<Bitmap> imageBitmap=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ImageService imageService=retrofit.create(ImageService.class);
                Call<ImageEntity> call=imageService.getMessage();
                call.enqueue(new Callback<ImageEntity>() {
                    @Override
                    public void onResponse(Call<ImageEntity> call, Response<ImageEntity> response) {
                        ImageEntity imageEntity=response.body();
                        for (ImageEntity.ResultsBean resultsBean:
                                imageEntity.getResults()) {
                            imageUrl.add(resultsBean.getUrl());
                        }
                        for (int i=0;i<imageUrl.size();i++){
                            imageBitmap.add(imageLoader.loadBitmap(imageUrl.get(i),200,200));
                        }
                        Log.e("Retrofit", "onResponse: "+imageUrl.get(0));

                    }

                    @Override
                    public void onFailure(Call<ImageEntity> call, Throwable t) {
                        Log.e("TAG", "Throwable : " + t);
                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
                        GridLayoutManager gridLayoutManager=new GridLayoutManager(MainActivity.this,4);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        ImageAdapter imageAdapter=new ImageAdapter(imageUrl,imageBitmap);
                        recyclerView.setAdapter(imageAdapter);
                    }
                });
            }
        }).start();
    }


}

