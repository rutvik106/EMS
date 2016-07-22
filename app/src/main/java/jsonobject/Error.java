package jsonobject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rutvik on 21-07-2016 at 07:43 PM.
 */

public class Error
{

    String error, message;

    public Error(String response) throws JSONException
    {
        JSONObject obj = new JSONObject(response).getJSONObject("error");
        error = obj.getString("error");
        message = obj.getString("message");
    }

    public String getMessage(){
        return message;
    }

}
