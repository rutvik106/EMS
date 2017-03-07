package apimodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rutvik on 3/7/2017 at 11:07 PM.
 */

public class CustomerSearchResult
{


    /**
     * label : Sumeet Bhandari
     */

    @SerializedName("label")
    private String label;

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }
}
