package models;

/**
 * Created by rutvik on 28-08-2016 at 09:57 AM.
 */

public class SearchResultItem
{

    final String id, customerName, contact;

    public SearchResultItem(final String id, final String customerName, final String contact)
    {
        this.id = id;
        this.contact = contact;
        this.customerName = customerName;
    }

    public String getId()
    {
        return id;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public String getCustomerContact()
    {
        return contact;
    }

}
