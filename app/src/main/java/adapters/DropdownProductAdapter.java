package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.rutvik.ems.App;
import com.example.rutvik.ems.R;

import java.util.ArrayList;
import java.util.List;

import extras.AppUtils;
import extras.Log;
import jsonobject.DropdownProduct;

/**
 * Created by rutvik on 28-07-2016 at 04:18 PM.
 */

public class DropdownProductAdapter extends BaseAdapter implements Filterable
{

    Context context;

    private List<DropdownProduct> dropdownProductList = new ArrayList<>();

    private List<String> suggestions = new ArrayList<>();

    private Filter filter = new CustomFilter();

    private static final String TAG = AppUtils.APP_TAG + DropdownProductAdapter.class.getSimpleName();

    public DropdownProductAdapter(Context context)
    {
        this.context = context;
    }

    public void addDropdownListProduct(DropdownProduct dp)
    {
        dropdownProductList.add(dp);
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
        return Long.valueOf(dropdownProductList.get(i).getSub_cat_id());
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
        return filter;
    }

    private class CustomFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            suggestions.clear();

            if (dropdownProductList != null && constraint != null)
            { // Check if the Original List and Constraint aren't null.
                for (int i = 0; i < dropdownProductList.size(); i++)
                {
                    if (dropdownProductList.get(i).getSub_cat_name().toLowerCase().contains(constraint))
                    { // Compare item in original list if it contains constraints.
                        suggestions.add(dropdownProductList.get(i).getSub_cat_name()); // If TRUE add item in Suggestions.
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

}
