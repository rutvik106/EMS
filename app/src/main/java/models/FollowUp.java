package models;

import org.json.JSONException;
import org.json.JSONObject;

import component.NotificationListItemComp;
import extras.AppUtils;
import extras.Log;

/**
 * Created by rutvik on 25-04-2016 at 03:33 PM.
 */
public class FollowUp implements NotificationListItemComp {

    private static final String TAG = AppUtils.APP_TAG + FollowUp.class.getSimpleName();

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public boolean render=true;

    public String getKey() {
        return key;
    }

    private String key;

    public String getId() {
        return id;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }

    public String getDiscussion() {
        return discussion;
    }

    public String getName() {
        return name;
    }

    public String getProduct() {
        return product;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public String getPhone() {
        return phone;
    }

    public String getHandledBy() {
        return handledBy;
    }

    String id, followUpDate, discussion, name, product, extraDetails, phone, handledBy;

    public FollowUp(JSONObject obj, String key) throws JSONException {
        Log.i(TAG, "date: " + obj.getString("follow_up_date"));
        this.key=key;
        id = obj.getString("id");
        followUpDate = obj.getString("follow_up_date");
        discussion = obj.getString("discussion");
        name = obj.getString("name");
        product = obj.getString("product");
        extraDetails = obj.getString("extra_details");
        phone = obj.getString("phone");
        handledBy = obj.getString("handle_by");
    }

    @Override
    public int getComponentType() {
        return Type.CHILD;
    }
}
