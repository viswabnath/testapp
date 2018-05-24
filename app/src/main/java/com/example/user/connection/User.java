package com.example.user.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaCas;

public class User {
    Context ctx;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public User(Context ctx){
        this.ctx=ctx;
        sharedPreferences=ctx.getSharedPreferences("myapp",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void setLoggedin(boolean loggedin){
        editor.putBoolean("loggedinmode",loggedin);
        editor.commit();
    }
public boolean loggedin(){
        return sharedPreferences.getBoolean("loggedinmode",false);
}



}
