package viewholders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.tapandtype.rutvik.ems.ActivityView;
import com.tapandtype.rutvik.ems.R;
import com.tapandtype.rutvik.ems.TakeFollowUp;

import apimodels.CustomFollowUpReport;
import extras.CommonUtils;

/**
 * Created by rutvik on 12/5/2016 at 9:05 AM.
 */

public class CustomFollowUpVH
        extends GenericViewHolder<CustomFollowUpReport.CustomFollowUpReportsBean>
        implements View.OnClickListener
{

    public TextView
            tv_followUpRepDateAdded,
            tv_followUpRepContact,
            tv_followUpRepCustomerPrice,
            tv_followUpRepEnqDate,
            tv_followUpRepIsBought,
            tv_followUpRepName,
            tv_followUpRepNextFollowUp,
            tv_followUpRepProduct,
            tv_followUpRepTotalMrp;
    CustomFollowUpReport.CustomFollowUpReportsBean model;


    public CustomFollowUpVH(Context context, View itemView)
    {
        super(context, itemView);

        tv_followUpRepDateAdded = (TextView) itemView.findViewById(R.id.tv_followUpRepDateAdded);
        tv_followUpRepContact = (TextView) itemView.findViewById(R.id.tv_followUpRepContact);
        tv_followUpRepCustomerPrice = (TextView) itemView.findViewById(R.id.tv_followUpRepCustomerPrice);
        tv_followUpRepEnqDate = (TextView) itemView.findViewById(R.id.tv_followUpRepEnqDate);
        tv_followUpRepIsBought = (TextView) itemView.findViewById(R.id.tv_followUpRepIsBought);
        tv_followUpRepName = (TextView) itemView.findViewById(R.id.tv_followUpRepName);
        tv_followUpRepNextFollowUp = (TextView) itemView.findViewById(R.id.tv_followUpRepNextFollowUp);
        tv_followUpRepProduct = (TextView) itemView.findViewById(R.id.tv_followUpRepProduct);
        tv_followUpRepTotalMrp = (TextView) itemView.findViewById(R.id.tv_followUpRepTotalMrp);

        itemView.findViewById(R.id.btn_reportTakeFollowUp).setOnClickListener(this);
        itemView.findViewById(R.id.btn_reportViewFollowUpDetails).setOnClickListener(this);

    }

    @Override
    public void bind(GenericViewHolder vh, CustomFollowUpReport.CustomFollowUpReportsBean model)
    {
        CustomFollowUpVH _vh = (CustomFollowUpVH) vh;
        _vh.model = model;
        _vh.tv_followUpRepDateAdded.setText(model.getDateAdded());
        _vh.tv_followUpRepContact.setText(model.getContactNo());
        _vh.tv_followUpRepCustomerPrice.setText(model.getCustomerPrice());
        _vh.tv_followUpRepEnqDate.setText(CommonUtils.convertDateToDDMMYYYY(model.getEnquiryDate()));
        _vh.tv_followUpRepIsBought.setText(model.getIsBought());
        _vh.tv_followUpRepName.setText(model.getCustomerName());
        _vh.tv_followUpRepNextFollowUp.setText(CommonUtils.convertDateToDDMMYYYY(model.getNextFollowUpDate()));
        _vh.tv_followUpRepProduct.setText(model.getSubCatName());
        _vh.tv_followUpRepTotalMrp.setText(model.getTotalMrp());
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_reportTakeFollowUp:
                Intent i = new Intent(context, TakeFollowUp.class);
                i.putExtra("follow_up_customer_name", model.getCustomerName());
                i.putExtra("follow_up_customer_contact", model.getContactNo());
                i.putExtra("enquiry_id", model.getEnquiryFormId());
                context.startActivity(i);
                break;

            case R.id.btn_reportViewFollowUpDetails:

                Intent intent = new Intent(context, ActivityView.class);
                intent.putExtra("follow_up_customer_name", model.getCustomerName());
                intent.putExtra("follow_up_customer_contact", model.getContactNo());
                intent.putExtra("enquiry_id", model.getEnquiryFormId());
                context.startActivity(intent);
                break;


        }
    }
}
