package jsonobject;

import org.json.JSONException;
import org.json.JSONObject;

import adapters.DropdownProductAdapter;

/**
 * Created by rutvik on 28-07-2016 at 03:45 PM.
 */

public class DropdownProduct implements DropdownProductAdapter.AutoCompleteDropDownItem
{

    String sub_cat_id,sub_cat_name,subCategory_price,cat_id,super_cat_id;

    public DropdownProduct(JSONObject obj) throws JSONException
    {
        sub_cat_name=obj.getString("sub_cat_name");
        sub_cat_id=obj.getString("sub_cat_id");
        subCategory_price=obj.getString("subCategory_price");
        cat_id=obj.getString("cat_id");
        super_cat_id=obj.getString("super_cat_id");
    }

    public String getSub_cat_id()
    {
        return sub_cat_id;
    }

    public String getSub_cat_name()
    {
        return sub_cat_name;
    }

    public String getSubCategory_price()
    {
        return subCategory_price;
    }

    public String getCat_id()
    {
        return cat_id;
    }

    public String getSuper_cat_id()
    {
        return super_cat_id;
    }

    @Override
    public String getValue()
    {
        return getSub_cat_name();
    }

    @Override
    public int getKey()
    {
        return Integer.valueOf(getSub_cat_id());
    }
}
