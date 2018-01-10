package com.caizilong.netesase.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/1/10.
 */

public class SharePrenceUtil {

     private static final String XML_FILE_NAME = "cache";

    public static void saveString(Context context, String title, String content){

        SharedPreferences sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.putString(title, content);

        edit.apply();
    }

    public static String getString(Context context,String title){

        String content;
        SharedPreferences sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE);

        content = sharedPreferences.getString(title, "");

        return content;

    }


    public static void saveInt(Context context, String title, int content){

        SharedPreferences sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.putInt(title, content);

        edit.apply();
    }

    public static int getInt(Context context,String title){

        int content;
        SharedPreferences sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE);

        content = sharedPreferences.getInt(title, 0);

        return content;

    }

    public static void saveLong(Context context, String title, long content){

        SharedPreferences sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.putLong(title, content);

        edit.apply();
    }

    public static long getLong(Context context,String title){

        long content;
        SharedPreferences sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE);

        content = sharedPreferences.getLong(title, 0);

        return content;

    }

}
