package viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tapandtype.rutvik.ems.ActivityView;
import com.tapandtype.rutvik.ems.R;

import models.FollowUpSeparator;

/**
 * Created by rutvik on 23-08-2016 at 05:38 PM.
 */

public class FollowUpSeparatorVH extends RecyclerView.ViewHolder
{

    TextView tvFollowUpCount;

    Button btnViewFollowUp;

    String customerName, enquiryId, customerContact;

    FollowUpSeparator model;

    public FollowUpSeparatorVH(final Context context, View itemView)
    {
        super(itemView);
        tvFollowUpCount = (TextView) itemView.findViewById(R.id.tv_followUpCount);
        btnViewFollowUp = (Button) itemView.findViewById(R.id.btn_viewFollowUp);
        btnViewFollowUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(context, ActivityView.class);
                i.putExtra("follow_up_customer_contact", model.getCustomerContact());
                i.putExtra("follow_up_customer_name", model.getCustomerName());
                i.putExtra("enquiry_id", model.getEnquiryId());
                context.startActivity(i);
            }
        });
    }

    public static FollowUpSeparatorVH create(final Context context, final ViewGroup parent)
    {

        return new FollowUpSeparatorVH(context, LayoutInflater.from(context).inflate(R.layout.single_follow_up_separator, parent, false));

    }

    public static void bind(FollowUpSeparatorVH vh, final FollowUpSeparator model)
    {
        vh.model = model;
        vh.tvFollowUpCount.setText(model.getCount());

        if (model.getEnquiryId().isEmpty())
        {
            vh.btnViewFollowUp.setVisibility(View.GONE);
        }
    }

}
