package jsonobject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rutvik on 22-07-2016 at 02:14 PM.
 */

public class Response {

    String status, message, id;

    public Response(String response) throws JSONException {
        JSONObject obj = new JSONObject(response).getJSONObject("response");
        status = obj.getString("status");
        message = obj.getString("message");
        try {
            id = obj.getString("id");
        } catch (JSONException e) {
            id = "0";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStatusOk() {
        if (status.equals("1")) {
            return true;
        }
        return false;
    }


    public String getMessage() {
        return message;
    }

}
