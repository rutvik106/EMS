package models;

/**
 * Created by rutvik on 10/2/2016 at 8:40 AM.
 */

public class FollowUpSeparator
{

    private final String enquiryId;
    private final String customerName;
    private final String customerContact;

    private final String count;

    public FollowUpSeparator(final String enquiryId, final String customerName,
                             final String customerContact, final String count)
    {
        this.enquiryId = enquiryId;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.count = count;
    }

    public String getEnquiryId()
    {
        return enquiryId;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public String getCustomerContact()
    {
        return customerContact;
    }

    public String getCount()
    {
        return count;
    }
}
