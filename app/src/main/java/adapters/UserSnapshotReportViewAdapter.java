package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import apimodels.UserSnapshotReport;
import viewholders.UserSnapshotReportVH;

/**
 * Created by rutvik on 12/4/2016 at 11:00 PM.
 */

public class UserSnapshotReportViewAdapter extends RecyclerView.Adapter
{

    final Context context;

    final List<UserSnapshotReport> userSnapshotReportList;

    public UserSnapshotReportViewAdapter(Context context)
    {
        this.context = context;
        userSnapshotReportList = new ArrayList<>();
    }

    public void addUserSnapshot(UserSnapshotReport userSnapshot)
    {
        userSnapshotReportList.add(userSnapshot);
        notifyItemInserted(userSnapshotReportList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return UserSnapshotReportVH.create(context, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        UserSnapshotReportVH.bind((UserSnapshotReportVH) holder, userSnapshotReportList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return userSnapshotReportList.size();
    }
}
