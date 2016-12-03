package apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rutvik on 12/3/2016 at 1:09 PM.
 */

public class FiltersForCustomLeadReports
{


    @SerializedName("product_list")
    private List<ProductListBean> productList;
    @SerializedName("user_list")
    private List<UserListBean> userList;
    @SerializedName("enquiry_type")
    private List<EnquiryTypeBean> enquiryType;

    public List<ProductListBean> getProductList()
    {
        return productList;
    }

    public void setProductList(List<ProductListBean> productList)
    {
        this.productList = productList;
    }

    public List<UserListBean> getUserList()
    {
        return userList;
    }

    public void setUserList(List<UserListBean> userList)
    {
        this.userList = userList;
    }

    public List<EnquiryTypeBean> getEnquiryType()
    {
        return enquiryType;
    }

    public void setEnquiryType(List<EnquiryTypeBean> enquiryType)
    {
        this.enquiryType = enquiryType;
    }

    public static class ProductListBean
    {
        /**
         * sub_cat_id : 410
         * sub_cat_name : Television
         * subCategory_price : 0
         * cat_id : 1
         * super_cat_id : null
         */

        @SerializedName("sub_cat_id")
        private String subCatId;
        @SerializedName("sub_cat_name")
        private String subCatName;
        @SerializedName("subCategory_price")
        private String subCategoryPrice;
        @SerializedName("cat_id")
        private String catId;
        @SerializedName("super_cat_id")
        private Object superCatId;

        public String getSubCatId()
        {
            return subCatId;
        }

        public void setSubCatId(String subCatId)
        {
            this.subCatId = subCatId;
        }

        public String getSubCatName()
        {
            return subCatName;
        }

        public void setSubCatName(String subCatName)
        {
            this.subCatName = subCatName;
        }

        public String getSubCategoryPrice()
        {
            return subCategoryPrice;
        }

        public void setSubCategoryPrice(String subCategoryPrice)
        {
            this.subCategoryPrice = subCategoryPrice;
        }

        public String getCatId()
        {
            return catId;
        }

        public void setCatId(String catId)
        {
            this.catId = catId;
        }

        public Object getSuperCatId()
        {
            return superCatId;
        }

        public void setSuperCatId(Object superCatId)
        {
            this.superCatId = superCatId;
        }
    }

    public static class UserListBean
    {
        /**
         * admin_id : 29
         * admin_email : sanket@tapandtype.com
         * admin_phone : 9978812644
         * admin_name : Sanket Jasani
         * admin_username : sanket
         * admin_password : 2amnt8ZYJY9RY
         * last_login : 2016-12-03 12:38:40
         * date_added : 2015-10-01 11:52:37
         * date_modified : 2015-10-01 11:52:37
         * admin_hash : 2a10c82bd675181116e985d276
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
        @SerializedName("admin_username")
        private String adminUsername;
        @SerializedName("admin_password")
        private String adminPassword;
        @SerializedName("last_login")
        private String lastLogin;
        @SerializedName("date_added")
        private String dateAdded;
        @SerializedName("date_modified")
        private String dateModified;
        @SerializedName("admin_hash")
        private String adminHash;
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

        public String getAdminUsername()
        {
            return adminUsername;
        }

        public void setAdminUsername(String adminUsername)
        {
            this.adminUsername = adminUsername;
        }

        public String getAdminPassword()
        {
            return adminPassword;
        }

        public void setAdminPassword(String adminPassword)
        {
            this.adminPassword = adminPassword;
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

        public String getAdminHash()
        {
            return adminHash;
        }

        public void setAdminHash(String adminHash)
        {
            this.adminHash = adminHash;
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

    public static class EnquiryTypeBean
    {
        /**
         * customer_type_id : 1
         * customer_type : Walk In
         * reference : 0
         */

        @SerializedName("customer_type_id")
        private String customerTypeId;
        @SerializedName("customer_type")
        private String customerType;
        @SerializedName("reference")
        private String reference;

        public String getCustomerTypeId()
        {
            return customerTypeId;
        }

        public void setCustomerTypeId(String customerTypeId)
        {
            this.customerTypeId = customerTypeId;
        }

        public String getCustomerType()
        {
            return customerType;
        }

        public void setCustomerType(String customerType)
        {
            this.customerType = customerType;
        }

        public String getReference()
        {
            return reference;
        }

        public void setReference(String reference)
        {
            this.reference = reference;
        }
    }
}
