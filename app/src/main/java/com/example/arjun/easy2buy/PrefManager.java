package com.example.arjun.easy2buy;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class for Shared Preference
 */
public class PrefManager
{
    private Context context;

    public PrefManager(Context context)
    {
        this.context = context;
    }





    public void saveLoginDetails(String email, String password)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.apply();
    }

    public String getEmail()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }

    public boolean isUserLoggedOut()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();

        return isEmailEmpty || isPasswordEmpty;
    }

    public void logout()
    {
        SharedPreferences preferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        //for single value remove
              preferences.edit().remove("Email").apply();
             preferences.edit().remove("Password").apply();

       // preferences.edit().clear();
       // return  true;
    }

}