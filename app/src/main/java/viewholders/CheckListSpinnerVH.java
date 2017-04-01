package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ComponentFactory.CheckListDialog;
import ComponentFactory.Component;
import ComponentFactory.MyTextBox;
import ComponentFactory.RowItem;
import extras.AppUtils;
import models.SingleCheckListItem;

/**
 * Created by ACER on 05-Feb-16.
 */
public class CheckListSpinnerVH extends RecyclerView.ViewHolder implements RowItem, View.OnClickListener, View.OnFocusChangeListener, CheckListDialog.CheckListDialogListener
{

    private static final String TAG = AppUtils.APP_TAG + CheckListSpinnerVH.class.getSimpleName();

    MyTextBox myCheckListSpinner;

    ArrayList<SingleCheckListItem> singleItemList = new ArrayList<>();

    Context context;

    CheckListDialog dialog;

    Map map;

    public CheckListSpinnerVH(MyTextBox myCheckListSpinner)
    {
        super(myCheckListSpinner);
        this.myCheckListSpinner = myCheckListSpinner;
    }

    public static CheckListSpinnerVH create(final Context context)
    {

        CheckListSpinnerVH vh = new CheckListSpinnerVH(new MyTextBox(context));
        vh.context = context;
        vh.dialog = new CheckListDialog(context, vh.singleItemList, vh);
        vh.myCheckListSpinner.editText.setTextIsSelectable(false);
        vh.myCheckListSpinner.editText.setInputType(InputType.TYPE_NULL);
        vh.myCheckListSpinner.editText.setOnFocusChangeListener(vh);
        vh.myCheckListSpinner.editText.setOnClickListener(vh);
        vh.myCheckListSpinner.editText.setText("None");
        return vh;
    }

    public static void bind(CheckListSpinnerVH vh, Map map, Component component)
    {
        Log.i("STS", "inside check list bind method...");

        vh.map = map;

        if (vh.singleItemList.isEmpty())
        {

            Set mapSet = ((Map<String, String>) vh.map.get("component_data")).entrySet();

            Iterator i = mapSet.iterator();

            while (i.hasNext())
            {
                Map.Entry mapEntry = (Map.Entry) i.next();
                vh.singleItemList.add(new SingleCheckListItem(mapEntry.getKey().toString(),
                        mapEntry.getValue().toString()));
            }

        }

        //vh.myCheckListSpinner.setAdapterArray(vh.singleItemList);
        vh.myCheckListSpinner.setHint(vh.map.get("label").toString());

        component.setRowItem(vh);
    }

    @Override
    public Object getValue()
    {
        return map.get("value").toString();
    }

    @Override
    public void setValue(Object object)
    {

    }

    @Override
    public CheckListSpinnerVH getView()
    {
        return this;
    }

    @Override
    public int getComponentType()
    {
        return Component.CHECKLIST;
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
        return map.get("label").toString();
    }

    @Override
    public String getName()
    {
        return map.get("name").toString();
    }

    @Override
    public void onClick(View v)
    {
        Log.i(TAG, "CLICKED!!!");
        dialog.show(map.get("label").toString());
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        if (hasFocus)
        {
            Log.i(TAG, "CLICKED!!!");
            dialog.show(map.get("label").toString());
        }
    }

    @Override
    public void onResult(String data)
    {

        Log.i(TAG, "RESULT: " + data);
        map.put("value", data);
        int count = data.length() - data.replace(",", "").length() + 1;
        myCheckListSpinner.editText.setText(count + " Selected");

    }

    @Override
    public void onNothingSelected()
    {
        myCheckListSpinner.editText.setText("NONE");
        map.put("value", "");
    }

}
