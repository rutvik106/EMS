package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tapandtype.rutvik.ems.R;

import apimodels.UserSnapshotReport;

/**
 * Created by rutvik on 12/4/2016 at 11:12 PM.
 */

public class UserSnapshotReportVH extends RecyclerView.ViewHolder
{

    final Context context;

    private TextView
            tvUserSnapshotName,
            tvUserSnapshotDoneFollowUps,
            tvUserSnapshotSuccessfulEnquiry,
            tvUserSnapshotTotalEnquiryGenerated,
            tvUserSnapshotTotalEnquiryHandled,
            tvUserSnapshotUnsuccessfulEnquiry;

    private UserSnapshotReportVH(Context context, View itemView)
    {
        super(itemView);
        this.context = context;

        tvUserSnapshotName = (TextView) itemView.findViewById(R.id.tv_userSnapshotName);
        tvUserSnapshotDoneFollowUps = (TextView) itemView.findViewById(R.id.tv_userSnapshotDoneFollowUps);
        tvUserSnapshotSuccessfulEnquiry = (TextView) itemView.findViewById(R.id.tv_userSnapshotSuccessfulEnquiry);
        tvUserSnapshotTotalEnquiryGenerated = (TextView) itemView.findViewById(R.id.tv_userSnapshotTotalEnquiryGenerated);
        tvUserSnapshotTotalEnquiryHandled = (TextView) itemView.findViewById(R.id.tv_userSnapshotTotalEnquiryHandled);
        tvUserSnapshotUnsuccessfulEnquiry = (TextView) itemView.findViewById(R.id.tv_userSnapshotUnsuccessfulEnquiry);

    }

    public static UserSnapshotReportVH create(final Context context, final ViewGroup parent)
    {
        return new UserSnapshotReportVH(context, LayoutInflater.from(context)
                .inflate(R.layout.single_user_snapshot_item, parent, false));
    }

    public static void bind(final UserSnapshotReportVH vh, UserSnapshotReport userSnapshotReport)
    {
        vh.tvUserSnapshotName.setText(userSnapshotReport.getAdminName());
        vh.tvUserSnapshotDoneFollowUps.setText(userSnapshotReport.getDoneFollowUpsByUser());
        vh.tvUserSnapshotSuccessfulEnquiry.setText(userSnapshotReport.getSuccessfulEnquiryGeneratedByUser());
        vh.tvUserSnapshotTotalEnquiryGenerated.setText(userSnapshotReport.getTotalEnquiryGeneratedByUser());
        vh.tvUserSnapshotTotalEnquiryHandled.setText(userSnapshotReport.getTotalEnquiryHandledByUser());
        vh.tvUserSnapshotUnsuccessfulEnquiry.setText(userSnapshotReport.getUnsuccessfulEnquiryGeneratedByUser());
    }


}
