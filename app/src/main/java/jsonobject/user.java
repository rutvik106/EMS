package jsonobject;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rutvik on 21-07-2016 at 07:40 PM.
 */

public class User
{

    String admin_name, admin_id, session_id;

    boolean admin_logged_in;

    List<String> admin_rights = new ArrayList<>();

    private String userJsonResponseString;

    public User(final String response) throws JSONException
    {
        this.userJsonResponseString = response;
        JSONObject obj = new JSONObject(response).getJSONObject("user");
        admin_name = obj.getString("admin_name");
        admin_id = obj.getString("admin_id");
        admin_logged_in = obj.getBoolean("admin_logged_in");
        session_id = obj.getString("session_id");
        JSONArray arr = obj.getJSONArray("admin_rights");
        for (int i = 0; i < arr.length(); i++)
        {
            admin_rights.add(arr.getString(i));
        }
    }

    public String getSession_id()
    {
        return session_id;
    }

    public String getAdmin_id()
    {
        return admin_id;
    }

    public String getResponseString()
    {
        return userJsonResponseString;
    }

}
