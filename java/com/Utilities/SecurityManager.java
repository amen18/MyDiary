package com.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.navigation.R;

/**
 * Created by Ed on 02.05.2016.
 */
public class SecurityManager {
    //sshared preferances

    private static final String MYPREFERENCES = "passwordPref";
    public static final String PASSWORD = "passKey";
    public static final String START = "startKey";
    public static final String COLOR = "colorKey";
    public static final String TOOLBARCOLOR = "toolbarColorKey";
    private  static SharedPreferences sharedPreferences;



    public static void setToolbarColorPref(Context context,int color){
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TOOLBARCOLOR,color);
        editor.commit();
    }

    public static int getToolbarColor(Context context){
        int defValue = context.getResources().getColor(R.color.colorPrimary);
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        int colorTool = sharedPreferences.getInt(TOOLBARCOLOR, 0);
        if(colorTool == 0){
            return defValue;
        }else
            return colorTool;
    }



    public static void setBackgroundColorPref(Context context,int color){
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COLOR,color);
        editor.commit();
    }

    public static int getBackgroundColor(Context context){
//        int defValue = context.getResources().getColor(R.color.colorPrimary);
        int defValue = R.drawable.bc00;
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        int color = sharedPreferences.getInt(COLOR, 0);
        if(color == 0){
            return defValue;
        }else
            return color;
    }

    public static void setStartPref(Context context)
    {
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(START,"pass");
        editor.commit();

    }

    public static Boolean isFirstLoad(Context context){
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        String pass = sharedPreferences.getString(START, null);
        return pass == null;
    }

    public static void setPassword(String password, Context context) {
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        setPasswordPreferances(password, context);
    }

    public static void changePassword(String password, Context context,String currentPassword) {
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        String oldPassword = sharedPreferences.getString(PASSWORD, null);
        if (currentPassword != null && oldPassword != null) {
            if (currentPassword.equals(oldPassword)) {
                  setPasswordPreferances(password, context);
            }
        }
    }

    public static String getPassword(Context context){
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        String pass = sharedPreferences.getString(PASSWORD, null);
        if(pass == null){
            return "";
        }else
        return pass;
    }

    private static void setPasswordPreferances(String password, Context context){
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD,password);
        editor.commit();
    }

    public static Boolean isPasswordCorrect(String password, Context context){
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        String currentPassword = sharedPreferences.getString(PASSWORD, null);
        if(currentPassword != null)
        {
            return password.equals(currentPassword);
        }
        else
        {
            return false;
        }
    }
}
