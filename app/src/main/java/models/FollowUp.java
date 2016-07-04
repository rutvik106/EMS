package models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    String id;

    public void setFollowUpDate(String followUpDate) {
        Log.i(TAG,"FOLLOW UP DATE: "+followUpDate);
        String[] data=followUpDate.split("#");
        Log.i(TAG,"DATA 0: "+data[0]);
        Log.i(TAG,"DATA 1: "+data[1]);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            this.followUpDate = simpleDateFormat.format(simpleDateFormat.parse(data[0].trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        handledBy=data[1].trim();
    }

    String followUpDate;
    String discussion;
    String name;
    String product;
    String extraDetails;
    String phone;
    String handledBy;

    public FollowUp(JSONObject obj) throws JSONException {
        Log.i(TAG, "date: " + obj.getString("next_follow_up_date"));

        id = obj.getString("enquiry_form_id");
        setFollowUpDate(obj.getString("next_follow_up_date"));
        try {
            discussion = obj.getString("discussion");
        }
        catch (JSONException e){
            discussion="N/A";
        }
        name = obj.getString("customer_name");
        product = obj.getString("sub_cat_name");
        try {
            extraDetails = obj.getString("extra_details");
        }
        catch (JSONException e){
            extraDetails="N/A";
        }
        phone = obj.getString("contact_no");
    }

    @Override
    public int getComponentType() {
        return Type.CHILD;
    }
}
