package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ComponentFactory.AppendableTextBox;
import ComponentFactory.Component;
import ComponentFactory.RowItem;
import extras.AppUtils;
import extras.Log;

/**
 * Created by rutvik on 07-07-2016 at 04:37 PM.
 */

public class AppendableTextBoxVH extends RecyclerView.ViewHolder implements RowItem
{
    public static final String TAG = AppUtils.APP_TAG + AppendableTextBoxVH.class.getSimpleName();

    AppendableTextBox appendableTextBox;

    Map map;

    public AppendableTextBoxVH(AppendableTextBox appendableTextBox)
    {
        super(appendableTextBox);
        this.appendableTextBox = appendableTextBox;
    }

    public static AppendableTextBoxVH create(Context context)
    {
        AppendableTextBoxVH vh = new AppendableTextBoxVH(new AppendableTextBox(context));
        return vh;
    }

    public static void bind(AppendableTextBoxVH vh, Map map, Component component)
    {
        vh.map=map;
        vh.appendableTextBox.tvRootTextView.setHint(map.get("label").toString());
        vh.appendableTextBox.setValueName(map.get("name").toString());
        component.setRowItem(vh);
    }

    @Override
    public Object getValue()
    {
        Log.i(TAG,"RETURNING VALUE OF APPENDABLE TEXT BOX");
        return appendableTextBox.getValues().toString();
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
        return Component.APPENDABLE_TEXT_BOX;
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
        return map.get("name").toString();
    }
}
