package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Map;

import ComponentFactory.Component;
import ComponentFactory.InquiryProductDetails;
import ComponentFactory.RowItem;

import static ComponentFactory.Component.INQUIRY_PRODUCT_DETAILS;

/**
 * Created by rutvik on 09-07-2016 at 04:53 PM.
 */

public class InquiryProductDetailsVH extends RecyclerView.ViewHolder implements RowItem
{

    InquiryProductDetails inquiryProductDetails;

    Map map;

    public InquiryProductDetailsVH(InquiryProductDetails inquiryProductDetails)
    {
        super(inquiryProductDetails);
        this.inquiryProductDetails = inquiryProductDetails;
    }

    public static InquiryProductDetailsVH create(Context context)
    {
        InquiryProductDetailsVH vh=new InquiryProductDetailsVH(new InquiryProductDetails(context));
        return vh;
    }

    public static void bind(InquiryProductDetailsVH vh, Map map, Component component)
    {
        vh.map=map;
        component.setRowItem(vh);
    }

    @Override
    public Object getValue()
    {
        return null;
    }

    @Override
    public void setValue(Object object)
    {

    }

    @Override
    public RecyclerView.ViewHolder getView()
    {
        return this;
    }

    @Override
    public int getComponentType()
    {
        return INQUIRY_PRODUCT_DETAILS;
    }

    @Override
    public String getJSONName()
    {
        return null;
    }

    @Override
    public String getJSONKey()
    {
        return null;
    }

    @Override
    public String getJSONValue()
    {
        return null;
    }

    @Override
    public String getLabel()
    {
        return null;
    }

    @Override
    public String getName()
    {
        return null;
    }
}
