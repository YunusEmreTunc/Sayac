package com.example.sayac;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {
    int upperLimit, lowerLimit, currentValue;
    boolean upperVib, lowerVib, lowerSound, upperSound;
    static Settings settings = null;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private Settings(Context context){
        sharedPreferences = context.getSharedPreferences("settings", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public static Settings getSettings(Context context){
        if(settings == null){
            settings = new Settings(context);
        }
        return settings;
    }
    public void SaveValues(){
        editor.putInt("upperLimit",upperLimit);
        editor.putInt("lowerLimit",lowerLimit);
        editor.putInt("currentValue",currentValue);
        editor.putBoolean("upperVib", upperVib);
        editor.putBoolean("upperSound", upperSound);
        editor.putBoolean("lowerVib", lowerVib);
        editor.putBoolean("lowerSound", lowerSound);
    }
    public void Load(){
        upperLimit = sharedPreferences.getInt("upperLimit",0);
        lowerLimit = sharedPreferences.getInt("lowerLimit",0);
        currentValue = sharedPreferences.getInt("currentValue",0);
        upperVib = sharedPreferences.getBoolean("upperVib",false);
        lowerVib = sharedPreferences.getBoolean("lowerVib",false);
        upperSound = sharedPreferences.getBoolean("upperSound",false);
        lowerSound = sharedPreferences.getBoolean("lowerSound",false);
    }
}
