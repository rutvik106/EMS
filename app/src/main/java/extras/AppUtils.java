package extras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rutvik on 22-04-2016 at 02:36 PM.
 */
public final class AppUtils
{

    public static final String APP_TAG = "EMS ";

    public static final String SESSION_ID = "SESSION_ID";

    public static final String URL_WEBSERVICE = "/webservice/webservice.php";

    public static final String URL_EXACT_CONTACT_NO = "/json/exact_mobile_no.php";

    public static final String URL_GET_CUSTOMER_NAME = "/json/customer_name.php";
    public static final String URL_GET_CUSTOMER_EMAIL = "/json/email.php";
    public static final String URL_GET_CUSTOMER_CONTACT = "/json/mobile_no.php";
    public static final String URL_GET_ENQUIRY_ID = "/json/enquiry_id.php";

    public static final Map<String, String> followUpTypeMap = new HashMap<>();

    public static final Map<String, String> sendSmsOptionMap = new HashMap<>();

    static
    {
        followUpTypeMap.put("-- Select The Follow Up Type --", "-1");
        followUpTypeMap.put("Phone Call", "1");
        followUpTypeMap.put("Visit", "2");
        followUpTypeMap.put("SMS", "3");
        followUpTypeMap.put("Email", "4");
        followUpTypeMap.put("Other", "6");

        sendSmsOptionMap.put("No", "0");
        sendSmsOptionMap.put("Yes", "1");
    }

    public static final List<String> followUpTypeList = new ArrayList<>();

    public static final List<String> sendSmsList = new ArrayList<>();

    static
    {
        followUpTypeList.add("-- Select The Follow Up Type --");
        followUpTypeList.add("Phone Call");
        followUpTypeList.add("Visit");
        followUpTypeList.add("SMS");
        followUpTypeList.add("Email");
        followUpTypeList.add("Other");

        sendSmsList.add("Yes");
        sendSmsList.add("No");
    }


    public static String dateToDMY(int year, int monthOfYear, int dayOfMonth)
    {
        String day = String.valueOf(dayOfMonth);
        String month = String.valueOf(monthOfYear + 1);
        if (month.length() == 1)
        {
            month = "0" + month;
        }
        if (day.length() == 1)
        {
            day = "0" + day;
        }
        return day + "/" + month + "/" + year;
    }

    public static String TimeToHMS(int hourOfDay, int minuteOfDay, int secondOfDay)
    {
        String hour = String.valueOf(hourOfDay);
        String minute = String.valueOf(minuteOfDay);
        String second = String.valueOf(secondOfDay);
        if (hour.length() == 1)
        {
            hour = "0" + hour;
        }
        if (minute.length() == 1)
        {
            minute = "0" + minute;
        }
        if (second.length() == 1)
        {
            second = "0" + second;
        }
        return hour + ":" + minute + ":" + second;
    }


}
