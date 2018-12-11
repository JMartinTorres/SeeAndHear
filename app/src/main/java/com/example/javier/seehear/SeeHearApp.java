package com.example.javier.seehear;

import android.app.Application;

public class SeeHearApp extends Application {

    public static String imageUrl = "";


    public static String getImageUrl() {
        return imageUrl;
    }

    public static void setImageUrl(String imageUrl) {
        SeeHearApp.imageUrl = imageUrl;
    }
}
