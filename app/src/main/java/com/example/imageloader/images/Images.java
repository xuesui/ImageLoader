package com.example.imageloader.images;

import android.graphics.Bitmap;

public class Images {
    Bitmap bitmap;

    public Images(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }
}
