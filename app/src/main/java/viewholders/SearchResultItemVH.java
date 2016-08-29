package viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rutvik.ems.ActivityCustomerDetails;
import com.example.rutvik.ems.R;

import extras.AppUtils;
import models.SearchResultItem;

/**
 * Created by rutvik on 28-08-2016 at 09:54 AM.
 */

public class SearchResultItemVH extends RecyclerView.ViewHolder
{
    private static final String TAG = AppUtils.APP_TAG + SearchResultItemVH.class.getSimpleName();

    TextView tvCustomerName, tvCustomerContact;

    SearchResultItem model;

    private SearchResultItemVH(final Context context, View itemView)
    {
        super(itemView);

        tvCustomerContact = (TextView) itemView.findViewById(R.id.tv_customerContact);
        tvCustomerName = (TextView) itemView.findViewById(R.id.tv_customerName);

        itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(context, ActivityCustomerDetails.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("customer_id", model.getId());
                context.startActivity(i);
            }
        });
    }

    public static SearchResultItemVH create(Context context, ViewGroup parent)
    {
        if (context == null)
        {
            Log.i(TAG, "create: context is null");
        }
        return new SearchResultItemVH(context, LayoutInflater.from(context)
                .inflate(R.layout.single_search_result_row_item, parent, false));

    }

    public static void bind(SearchResultItemVH vh, SearchResultItem model)
    {

        vh.model = model;
        vh.tvCustomerContact.setText(model.getCustomerContact());
        vh.tvCustomerName.setText(model.getCustomerName());

    }

}
