package com.example.learning.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.example.learning.BuildConfig;

import java.io.File;

public class Util {
    public static String getFileName(String name){
        String result = "";
        String [] tab = name.split("\\.");
        for(int i = 0; i< tab.length-1; i++){
            if(i > 0){
                result = result + "."+ tab[i];
            }else{
                result = result + tab[i];
            }
        }
        return result;
    }

    public static Uri getImageURI(String image){
        Log.println(Log.VERBOSE, "IMAGE", "NAME==="+image);
        image = getFileName(image);
        Log.println(Log.VERBOSE, "IMAGE", "PATH==="+image);
        Uri pathUri = Uri.parse("android.resource://"+ BuildConfig.APPLICATION_ID+"/drawable/"+image);
        Log.println(Log.VERBOSE, "IMAGE", "URIPATH==="+pathUri);
        return pathUri;
    }

    public static Uri getAudioURI(String image){
        image = getFileName(image);
        Uri pathUri = Uri.parse("android.resource://"+ BuildConfig.APPLICATION_ID+"/raw/"+image);
        return pathUri;
    }

    public static int getRandNumber(int min, int max){
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }
}
