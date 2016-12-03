package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tapandtype.rutvik.ems.R;

import apimodels.CustomLeadReports;

/**
 * Created by rutvik on 12/3/2016 at 6:37 PM.
 */

public class SingleReportItemVH extends RecyclerView.ViewHolder
{

    final Context context;

    private TextView tvReportEnquiryDate, tvReportFollowUpDate, tvReportCustomerName,
            tvReportCustomerContact, tvReportEnquiryStatus, tvReportProductName,
            tvReportExtraDetails, tvReportHandledBy;

    private SingleReportItemVH(Context context, View itemView)
    {
        super(itemView);
        this.context = context;
        tvReportEnquiryDate = (TextView) itemView.findViewById(R.id.tv_reportEnquiryDate);
        tvReportFollowUpDate = (TextView) itemView.findViewById(R.id.tv_reportFollowUpDate);
        tvReportCustomerName = (TextView) itemView.findViewById(R.id.tv_reportCustomerName);
        tvReportCustomerContact = (TextView) itemView.findViewById(R.id.tv_reportCustomerContact);
        tvReportEnquiryStatus = (TextView) itemView.findViewById(R.id.tv_reportEnquiryStatus);
        tvReportProductName = (TextView) itemView.findViewById(R.id.tv_reportProductName);
        tvReportExtraDetails = (TextView) itemView.findViewById(R.id.tv_reportExtraDetails);
        tvReportHandledBy = (TextView) itemView.findViewById(R.id.tv_reportHandledBy);
    }

    public static SingleReportItemVH create(final Context context, final ViewGroup parent)
    {
        return new SingleReportItemVH(context, LayoutInflater.from(context)
                .inflate(R.layout.single_report_row, parent, false));
    }

    public static void bind(final SingleReportItemVH vh, CustomLeadReports.CustomLeadReportsBean model)
    {
        vh.tvReportCustomerContact.setText(model.getContactNo());
        vh.tvReportCustomerName.setText(model.getCustomerName());
        vh.tvReportEnquiryDate.setText(model.getEnquiryDate());
        vh.tvReportEnquiryStatus.setText(model.getIsBought());
        vh.tvReportExtraDetails.setText(model.getAttributeTypesSubCatWise());
        vh.tvReportFollowUpDate.setText(model.getNextFollowUpDate());
        vh.tvReportProductName.setText(model.getSubCatName());
        vh.tvReportHandledBy.setText(model.getAdminName());
    }

    /**@Override public void onClick(View view)
    {
    switch (view.getId())
    {
    case R.id.btn_takeFollowUp:
    Intent i = new Intent(context, TakeFollowUp.class);
    i.putExtra("follow_up_customer_name", followUp.getName());
    i.putExtra("follow_up_customer_contact", followUp.getPhone());
    i.putExtra("enquiry_id", followUp.getId());
    context.startActivity(i);
    break;

    case R.id.btn_viewFollowUpDetails:

    Intent intent = new Intent(context, ActivityView.class);
    intent.putExtra("follow_up_customer_name", followUp.getName());
    intent.putExtra("follow_up_customer_contact", followUp.getPhone());
    intent.putExtra("enquiry_id", followUp.getId());
    context.startActivity(intent);
    break;


    }
    }*/

}
