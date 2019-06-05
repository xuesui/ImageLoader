package com.example.imageloader.utils;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageService {

    @GET("data/%E7%A6%8F%E5%88%A9/0/0")
    Call<ImageEntity> getMessage();
}
