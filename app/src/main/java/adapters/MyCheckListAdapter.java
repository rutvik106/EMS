package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import java.util.ArrayList;

import ComponentFactory.MyCheckListSpinner;
import extras.AppUtils;
import models.SingleCheckListItem;

/**
 * Created by ACER on 04-Feb-16.
 */
public class MyCheckListAdapter extends BaseAdapter
{

    private static final String TAG = AppUtils.APP_TAG + MyCheckListAdapter.class.getSimpleName();

    ArrayList<SingleCheckListItem> checkTexts;

    Context context;

    public MyCheckListAdapter(Context context, ArrayList<SingleCheckListItem> checkTexts)
    {
        Log.i(TAG, "CREATING CHECK LIST ADAPTER");
        this.checkTexts = checkTexts;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return checkTexts.size();
    }

    @Override
    public SingleCheckListItem getItem(int i)
    {
        return checkTexts.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return checkTexts.get(i).hashCode();
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        View v = view;

        if (v == null)
        {
            Log.i(TAG, "CHECK ITEM IS NULL CREATING VH");
            v = new CheckBox(context);
            ViewHolder holder;
            holder = new ViewHolder();
            holder.checkBox = (CheckBox) v;
            v.setTag(holder);
        } else
        {
            Log.i(TAG, "CHECK ITEM NOT NULL");
        }

        ViewHolder holder = (ViewHolder) v.getTag();
        Log.i(TAG, "Setting check text: " + checkTexts.get(i));
        Log.i(TAG, "HOLDER.isChecked = " + holder.checkBox.isChecked());
        holder.checkBox.setText(checkTexts.get(i).getName());
        holder.checkBox.setChecked(checkTexts.get(i).isChecked());
        return v;
    }

    public static class ViewHolder
    {

        public CheckBox checkBox;

        public ViewHolder()
        {

            Log.i(TAG, "CREATING CHECK ITEM VH");

        }


    }

}
