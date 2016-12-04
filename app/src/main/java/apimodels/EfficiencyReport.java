package apimodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rutvik on 12/4/2016 at 10:21 PM.
 */

public class EfficiencyReport
{


    /**
     * total_enquiry : 5
     * successful_enquiries : 1
     * unsuccessful_enquiries : 0
     * ongoing_enquiries : 4
     * new_enquiries : 0
     */

    @SerializedName("total_enquiry")
    private String totalEnquiry;
    @SerializedName("successful_enquiries")
    private String successfulEnquiries;
    @SerializedName("unsuccessful_enquiries")
    private String unsuccessfulEnquiries;
    @SerializedName("ongoing_enquiries")
    private String ongoingEnquiries;
    @SerializedName("new_enquiries")
    private String newEnquiries;

    public String getTotalEnquiry()
    {
        return totalEnquiry;
    }

    public void setTotalEnquiry(String totalEnquiry)
    {
        this.totalEnquiry = totalEnquiry;
    }

    public String getSuccessfulEnquiries()
    {
        return successfulEnquiries;
    }

    public void setSuccessfulEnquiries(String successfulEnquiries)
    {
        this.successfulEnquiries = successfulEnquiries;
    }

    public String getUnsuccessfulEnquiries()
    {
        return unsuccessfulEnquiries;
    }

    public void setUnsuccessfulEnquiries(String unsuccessfulEnquiries)
    {
        this.unsuccessfulEnquiries = unsuccessfulEnquiries;
    }

    public String getOngoingEnquiries()
    {
        return ongoingEnquiries;
    }

    public void setOngoingEnquiries(String ongoingEnquiries)
    {
        this.ongoingEnquiries = ongoingEnquiries;
    }

    public String getNewEnquiries()
    {
        return newEnquiries;
    }

    public void setNewEnquiries(String newEnquiries)
    {
        this.newEnquiries = newEnquiries;
    }
}
