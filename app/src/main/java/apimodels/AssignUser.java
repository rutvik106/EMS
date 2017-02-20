package apimodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rutvik on 2/20/2017 at 8:44 AM.
 */

public class AssignUser
{


    /**
     * admin_id : 20
     * admin_email : vikas@gmail.com
     * admin_phone : 9978812644
     * admin_name : Mr. Vikas Upadhyay
     * admin_username : vikas
     * admin_password : 2apA.mltFYXe6
     * last_login : 2015-11-17 08:03:48
     * date_added : 2014-10-28 02:48:30
     * date_modified : 2014-10-28 02:48:30
     * admin_hash : 2a1064848a7d26a75d9fd60e74
     * is_active : 1
     */

    @SerializedName("admin_id")
    private String adminId;
    @SerializedName("admin_email")
    private String adminEmail;
    @SerializedName("admin_phone")
    private String adminPhone;
    @SerializedName("admin_name")
    private String adminName;
    @SerializedName("last_login")
    private String lastLogin;
    @SerializedName("date_added")
    private String dateAdded;
    @SerializedName("date_modified")
    private String dateModified;
    @SerializedName("is_active")
    private String isActive;

    public String getAdminId()
    {
        return adminId;
    }

    public void setAdminId(String adminId)
    {
        this.adminId = adminId;
    }

    public String getAdminEmail()
    {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail)
    {
        this.adminEmail = adminEmail;
    }

    public String getAdminPhone()
    {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone)
    {
        this.adminPhone = adminPhone;
    }

    public String getAdminName()
    {
        return adminName;
    }

    public void setAdminName(String adminName)
    {
        this.adminName = adminName;
    }

    public String getLastLogin()
    {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin)
    {
        this.lastLogin = lastLogin;
    }

    public String getDateAdded()
    {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded)
    {
        this.dateAdded = dateAdded;
    }

    public String getDateModified()
    {
        return dateModified;
    }

    public void setDateModified(String dateModified)
    {
        this.dateModified = dateModified;
    }

    public String getIsActive()
    {
        return isActive;
    }

    public void setIsActive(String isActive)
    {
        this.isActive = isActive;
    }
}
