package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import apimodels.CustomLeadReports;
import viewholders.SingleReportItemVH;

/**
 * Created by rutvik on 12/3/2016 at 6:32 PM.
 */

public class CustomLeadReportViewAdapter extends RecyclerView.Adapter
{

    final Context context;

    final List<CustomLeadReports.CustomLeadReportsBean> reportsBeanList;

    public CustomLeadReportViewAdapter(final Context context)
    {
        this.context = context;
        reportsBeanList = new LinkedList<>();
    }

    public void addReportListItem(CustomLeadReports.CustomLeadReportsBean reportsBean)
    {
        reportsBeanList.add(reportsBean);
        notifyItemInserted(reportsBeanList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return SingleReportItemVH.create(context, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        SingleReportItemVH.bind((SingleReportItemVH) holder, reportsBeanList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return reportsBeanList.size();
    }
}
