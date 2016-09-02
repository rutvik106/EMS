package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tapandtype.rutvik.ems.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import extras.AppUtils;
import extras.Log;

/**
 * Created by rutvik on 28-07-2016 at 04:18 PM.
 */

public class DropdownProductAdapter extends BaseAdapter implements Filterable
{

    Context context;

    private Map<String, AutoCompleteDropDownItem> dropdownItemMap = new HashMap<>();

    private List<String> suggestions = new ArrayList<>();

    private Filter filter;

    private static final String TAG = AppUtils.APP_TAG + DropdownProductAdapter.class.getSimpleName();

    public DropdownProductAdapter(Context context)
    {
        this.context = context;
    }

    public void addDropdownListProduct(AutoCompleteDropDownItem dp)
    {
        dropdownItemMap.put(dp.getValue(), dp);
    }



    @Override
    public int getCount()
    {
        return suggestions.size();
    }

    @Override
    public Object getItem(int i)
    {
        return suggestions.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return Long.valueOf(dropdownItemMap.get(suggestions.get(i)).getKey());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (context == null)
        {
            Log.i(TAG, "===============CONTEXT IS NULL");
        }

        LayoutInflater inflater = LayoutInflater.from(context);

        ViewHolder holder;

        if (view == null)
        {
            view = inflater.inflate(R.layout.single_product_dropdown_row,
                    viewGroup,
                    false);
            holder = new ViewHolder();
            holder.autoText = (TextView) view.findViewById(R.id.tv_productDropdownValue);
            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }

        holder.autoText.setText(suggestions.get(i));

        return view;
    }

    @Override
    public Filter getFilter()
    {
        filter = new CustomFilter();
        return filter;
    }

    public class CustomFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {

            Log.i(TAG, "performFiltering: FILTERING TEXT: " + constraint);

            suggestions.clear();

            if (dropdownItemMap != null && constraint != null)
            { // Check if the Original List and Constraint aren't null.

                //Collection<AutoCompleteDropDownItem> dpCollection = dropdownItemMap.values();
                Iterator<AutoCompleteDropDownItem> dpIterator = dropdownItemMap.values().iterator();

                while (dpIterator.hasNext())
                {
                    AutoCompleteDropDownItem dp = dpIterator.next();
                    if (dropdownItemMap.get(dp.getValue()).getValue().toLowerCase().contains(constraint))
                    { // Compare item in original list if it contains constraints.
                        suggestions.add(dp.getValue()); // If TRUE add item in Suggestions.
                    }
                }
            }
            FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            if (results.count > 0)
            {
                notifyDataSetChanged();
            } else
            {
                notifyDataSetInvalidated();
            }
        }
    }

    private static class ViewHolder
    {
        TextView autoText;
    }

    public interface AutoCompleteDropDownItem
    {
        String getValue();

        int getKey();
    }

}
