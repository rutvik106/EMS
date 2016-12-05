package apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rutvik on 12/5/2016 at 8:09 AM.
 */

public class CustomFollowUpReport
{


    @SerializedName("custom_follow_up_reports")
    private List<CustomFollowUpReportsBean> customFollowUpReports;

    public List<CustomFollowUpReportsBean> getCustomFollowUpReports()
    {
        return customFollowUpReports;
    }

    public void setCustomFollowUpReports(List<CustomFollowUpReportsBean> customFollowUpReports)
    {
        this.customFollowUpReports = customFollowUpReports;
    }

    public static class CustomFollowUpReportsBean
    {
        /**
         * enquiry_form_id : 1960
         * date_added : 2016-11-26 12:45:52
         * enquiry_date : 2016-11-26 00:00:00
         * created_by : 23
         * total_mrp : 0
         * customer_name : Vijay H Gandhi
         * is_bought : 3
         * current_lead_holder : 23
         * contact_no : 9426396999
         * customer_price : 0
         * customer_type_id : 5
         * sub_cat_name : America Canada Alaska
         * attribute_types_sub_cat_wise : Tour Type : Git Adult : 3 Month : February Year : 2017
         * next_follow_up_date : 2016-12-01 08:00:00 ^ They have USA Visa and wants to travel in Feb or after April. Will contact them once we have new package launch after 29th Nov # Maulish Pathak
         * visit_date : null
         * is_imp : 0
         */

        @SerializedName("enquiry_form_id")
        private String enquiryFormId;
        @SerializedName("date_added")
        private String dateAdded;
        @SerializedName("enquiry_date")
        private String enquiryDate;
        @SerializedName("created_by")
        private String createdBy;
        @SerializedName("total_mrp")
        private String totalMrp;
        @SerializedName("customer_name")
        private String customerName;
        @SerializedName("is_bought")
        private String isBought;
        @SerializedName("current_lead_holder")
        private String currentLeadHolder;
        @SerializedName("contact_no")
        private String contactNo;
        @SerializedName("customer_price")
        private String customerPrice;
        @SerializedName("customer_type_id")
        private String customerTypeId;
        @SerializedName("sub_cat_name")
        private String subCatName;
        @SerializedName("attribute_types_sub_cat_wise")
        private String attributeTypesSubCatWise;
        @SerializedName("next_follow_up_date")
        private String nextFollowUpDate;
        @SerializedName("visit_date")
        private Object visitDate;
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

        public String getEnquiryDate()
        {
            return enquiryDate;
        }

        public void setEnquiryDate(String enquiryDate)
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

        public String getCurrentLeadHolder()
        {
            return currentLeadHolder;
        }

        public void setCurrentLeadHolder(String currentLeadHolder)
        {
            this.currentLeadHolder = currentLeadHolder;
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

        public String getCustomerTypeId()
        {
            return customerTypeId;
        }

        public void setCustomerTypeId(String customerTypeId)
        {
            this.customerTypeId = customerTypeId;
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

        public String getNextFollowUpDate()
        {
            return nextFollowUpDate;
        }

        public void setNextFollowUpDate(String nextFollowUpDate)
        {
            this.nextFollowUpDate = nextFollowUpDate;
        }

        public Object getVisitDate()
        {
            return visitDate;
        }

        public void setVisitDate(Object visitDate)
        {
            this.visitDate = visitDate;
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
