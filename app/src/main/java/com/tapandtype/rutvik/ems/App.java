package com.tapandtype.rutvik.ems;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import org.json.JSONException;

import extras.AppUtils;
import extras.Log;
import jsonobject.User;

/**
 * Created by rutvik on 22-04-2016 at 02:38 PM.
 */
public class App extends Application
{

    private static final String TAG = AppUtils.APP_TAG + App.class.getSimpleName();

    private User user;

    private String host;

    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.i(TAG, "APPLICATION CREATED!!!");

    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void setUser(User user)
    {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit().putString("user", user.getResponseString()).apply();
        this.user = user;
    }

    public User getUser()
    {
        if (user == null)
        {
            final String userJsonResponse = PreferenceManager.getDefaultSharedPreferences(this).getString("user", "");
            if (!userJsonResponse.isEmpty())
            {
                try
                {
                    user = new User(userJsonResponse);
                    setUser(user);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        }

        if (user.getSession_id().isEmpty() || user.getSession_id() == null)
        {
            Toast.makeText(this, "Your Session was expired", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, InitialActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            return null;
        }
        return user;
    }

    public String getHost()
    {
        if (host == null || host.isEmpty())
        {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            host = sp.getString("host", "");
        }
        return host;
    }

}
