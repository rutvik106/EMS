package apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by rutvik on 12/3/2016 at 1:32 PM.
 */

public class CustomLeadReports
{


    @SerializedName("custom_lead_reports")
    private List<CustomLeadReportsBean> customLeadReports;

    public List<CustomLeadReportsBean> getCustomLeadReports()
    {
        return customLeadReports;
    }

    public void setCustomLeadReports(List<CustomLeadReportsBean> customLeadReports)
    {
        this.customLeadReports = customLeadReports;
    }

    public static class CustomLeadReportsBean
    {
        /**
         * enquiry_form_id : 2168
         * date_added : 2016-11-23 12:15:36
         * enquiry_date : 2016-11-23 00:00:00
         * created_by : 29
         * current_lead_holder : 29
         * unique_enquiry_id : 231120160001
         * admin_name : Sanket Jasani
         * total_mrp : 0
         * customer_name : Rutvik Mehta
         * is_bought : 3
         * contact_no : 8765678987
         * customer_price : 0
         * customer_type_id : null
         * sub_cat_id : 410
         * sub_cat_name : Television
         * attribute_types_sub_cat_wise : No Details
         * next_follow_up_date : 2016-11-25 00:00:00
         * is_imp : 0
         */

        @SerializedName("enquiry_form_id")
        private String enquiryFormId;
        @SerializedName("date_added")
        private String dateAdded;
        @SerializedName("enquiry_date")
        private Date enquiryDate;
        @SerializedName("created_by")
        private String createdBy;
        @SerializedName("current_lead_holder")
        private String currentLeadHolder;
        @SerializedName("unique_enquiry_id")
        private String uniqueEnquiryId;
        @SerializedName("admin_name")
        private String adminName;
        @SerializedName("total_mrp")
        private String totalMrp;
        @SerializedName("customer_name")
        private String customerName;
        @SerializedName("is_bought")
        private String isBought;
        @SerializedName("contact_no")
        private String contactNo;
        @SerializedName("customer_price")
        private String customerPrice;
        @SerializedName("customer_type_id")
        private Object customerTypeId;
        @SerializedName("sub_cat_id")
        private String subCatId;
        @SerializedName("sub_cat_name")
        private String subCatName;
        @SerializedName("attribute_types_sub_cat_wise")
        private String attributeTypesSubCatWise;
        @SerializedName("next_follow_up_date")
        private Date nextFollowUpDate;
        @SerializedName("is_imp")
        private String isImp;

        public String getEnquiryFormId()
        {
            return enquiryFormId;
        }

        public void setEnquiryFormId(String enquiryFormId)
        {
            this.enquiryFormId = enquiryFormId;
        }

        public String getDateAdded()
        {
            return dateAdded;
        }

        public void setDateAdded(String dateAdded)
        {
            this.dateAdded = dateAdded;
        }

        public Date getEnquiryDate()
        {
            return enquiryDate;
        }

        public void setEnquiryDate(Date enquiryDate)
        {
            this.enquiryDate = enquiryDate;
        }

        public String getCreatedBy()
        {
            return createdBy;
        }

        public void setCreatedBy(String createdBy)
        {
            this.createdBy = createdBy;
        }

        public String getCurrentLeadHolder()
        {
            return currentLeadHolder;
        }

        public void setCurrentLeadHolder(String currentLeadHolder)
        {
            this.currentLeadHolder = currentLeadHolder;
        }

        public String getUniqueEnquiryId()
        {
            return uniqueEnquiryId;
        }

        public void setUniqueEnquiryId(String uniqueEnquiryId)
        {
            this.uniqueEnquiryId = uniqueEnquiryId;
        }

        public String getAdminName()
        {
            return adminName;
        }

        public void setAdminName(String adminName)
        {
            this.adminName = adminName;
        }

        public String getTotalMrp()
        {
            return totalMrp;
        }

        public void setTotalMrp(String totalMrp)
        {
            this.totalMrp = totalMrp;
        }

        public String getCustomerName()
        {
            return customerName;
        }

        public void setCustomerName(String customerName)
        {
            this.customerName = customerName;
        }

        public String getIsBought()
        {
            return isBought;
        }

        public void setIsBought(String isBought)
        {
            this.isBought = isBought;
        }

        public String getContactNo()
        {
            return contactNo;
        }

        public void setContactNo(String contactNo)
        {
            this.contactNo = contactNo;
        }

        public String getCustomerPrice()
        {
            return customerPrice;
        }

        public void setCustomerPrice(String customerPrice)
        {
            this.customerPrice = customerPrice;
        }

        public Object getCustomerTypeId()
        {
            return customerTypeId;
        }

        public void setCustomerTypeId(Object customerTypeId)
        {
            this.customerTypeId = customerTypeId;
        }

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

        public String getAttributeTypesSubCatWise()
        {
            return attributeTypesSubCatWise;
        }

        public void setAttributeTypesSubCatWise(String attributeTypesSubCatWise)
        {
            this.attributeTypesSubCatWise = attributeTypesSubCatWise;
        }

        public Date getNextFollowUpDate()
        {
            return nextFollowUpDate;
        }

        public void setNextFollowUpDate(Date nextFollowUpDate)
        {
            this.nextFollowUpDate = nextFollowUpDate;
        }

        public String getIsImp()
        {
            return isImp;
        }

        public void setIsImp(String isImp)
        {
            this.isImp = isImp;
        }
    }
}
