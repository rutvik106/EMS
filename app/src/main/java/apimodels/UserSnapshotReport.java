package apimodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rutvik on 12/4/2016 at 10:15 PM.
 */

public class UserSnapshotReport
{


    /**
     * admin_name : Maulish Pathak
     * total_enquiry_handled_by_user : 3
     * total_enquiry_generated_by_user : 3
     * successful_enquiry_generated_by_user : 0
     * unsuccessful_enquiry_generated_by_user : 0
     * done_follow_ups_by_user : 6
     */

    @SerializedName("admin_name")
    private String adminName;
    @SerializedName("total_enquiry_handled_by_user")
    private String totalEnquiryHandledByUser;
    @SerializedName("total_enquiry_generated_by_user")
    private String totalEnquiryGeneratedByUser;
    @SerializedName("successful_enquiry_generated_by_user")
    private String successfulEnquiryGeneratedByUser;
    @SerializedName("unsuccessful_enquiry_generated_by_user")
    private String unsuccessfulEnquiryGeneratedByUser;
    @SerializedName("done_follow_ups_by_user")
    private String doneFollowUpsByUser;

    public String getAdminName()
    {
        return adminName;
    }

    public void setAdminName(String adminName)
    {
        this.adminName = adminName;
    }

    public String getTotalEnquiryHandledByUser()
    {
        return totalEnquiryHandledByUser;
    }

    public void setTotalEnquiryHandledByUser(String totalEnquiryHandledByUser)
    {
        this.totalEnquiryHandledByUser = totalEnquiryHandledByUser;
    }

    public String getTotalEnquiryGeneratedByUser()
    {
        return totalEnquiryGeneratedByUser;
    }

    public void setTotalEnquiryGeneratedByUser(String totalEnquiryGeneratedByUser)
    {
        this.totalEnquiryGeneratedByUser = totalEnquiryGeneratedByUser;
    }

    public String getSuccessfulEnquiryGeneratedByUser()
    {
        return successfulEnquiryGeneratedByUser;
    }

    public void setSuccessfulEnquiryGeneratedByUser(String successfulEnquiryGeneratedByUser)
    {
        this.successfulEnquiryGeneratedByUser = successfulEnquiryGeneratedByUser;
    }

    public String getUnsuccessfulEnquiryGeneratedByUser()
    {
        return unsuccessfulEnquiryGeneratedByUser;
    }

    public void setUnsuccessfulEnquiryGeneratedByUser(String unsuccessfulEnquiryGeneratedByUser)
    {
        this.unsuccessfulEnquiryGeneratedByUser = unsuccessfulEnquiryGeneratedByUser;
    }

    public String getDoneFollowUpsByUser()
    {
        return doneFollowUpsByUser;
    }

    public void setDoneFollowUpsByUser(String doneFollowUpsByUser)
    {
        this.doneFollowUpsByUser = doneFollowUpsByUser;
    }
}
