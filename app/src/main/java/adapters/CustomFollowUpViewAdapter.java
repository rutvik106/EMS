package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tapandtype.rutvik.ems.R;

import apimodels.CustomFollowUpReport;
import viewholders.CustomFollowUpVH;
import viewholders.GenericViewHolder;

/**
 * Created by rutvik on 12/5/2016 at 9:00 AM.
 */

public class CustomFollowUpViewAdapter extends GenericArrayAdapter<CustomFollowUpReport.CustomFollowUpReportsBean, CustomFollowUpVH>
{
    public CustomFollowUpViewAdapter(Context context)
    {
        super(context);
    }

    @Override
    public void addNewModel(CustomFollowUpReport.CustomFollowUpReportsBean model)
    {
        super.addNewModel(model);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context)
                .inflate(R.layout.single_followup_report_item,parent,false);

        return new CustomFollowUpVH(context,view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        ((GenericViewHolder) holder).bind((GenericViewHolder) holder, modelList.get(position));
    }


}
