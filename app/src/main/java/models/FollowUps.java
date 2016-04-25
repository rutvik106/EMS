package models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rutvik on 25-04-2016 at 03:33 PM.
 */
public class FollowUps {

    String id,followUpDate,discussion,name,product,extraDetails,phone,handledBy;

    public FollowUps(JSONObject obj) throws JSONException{
        id=obj.getString("id");
        followUpDate=obj.getString("follow_up_date");
        discussion=obj.getString("discussion");
        name=obj.getString("name");
        product=obj.getString("product");
        extraDetails=obj.getString("extra_details");
        phone=obj.getString("phone");
        handledBy=obj.getString("handle_by");
    }

}
