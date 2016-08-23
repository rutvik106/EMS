package viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rutvik.ems.ActivityView;
import com.example.rutvik.ems.R;
import com.example.rutvik.ems.TakeFollowUp;

import component.NotificationListItemComp;
import models.FollowUp;

/**
 * Created by rutvik on 27-04-2016 at 03:16 PM.
 */
public class NotificationListChildVH extends RecyclerView.ViewHolder implements View.OnClickListener
{

    TextView tvTitle, tvName, tvDiscussionsContent, tvProductName, tvCustomerContact, tvHandledBy, tvExtraDetails;

    Button btnTakeFollowUp, btnViewFollowUpDetails;

    CardView cv;

    FollowUp followUp;

    Context context;

    public NotificationListChildVH(View itemView)
    {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvDiscussionsContent = (TextView) itemView.findViewById(R.id.tv_discussionsContent);
        tvProductName = (TextView) itemView.findViewById(R.id.tv_productName);
        tvCustomerContact = (TextView) itemView.findViewById(R.id.tv_customerContact);
        tvHandledBy = (TextView) itemView.findViewById(R.id.tv_handledBy);
        tvExtraDetails = (TextView) itemView.findViewById(R.id.tv_extraDetails);
        btnTakeFollowUp = (Button) itemView.findViewById(R.id.btn_takeFollowUp);
        btnTakeFollowUp.setOnClickListener(this);
        btnViewFollowUpDetails = (Button) itemView.findViewById(R.id.btn_viewFollowUpDetails);
        btnViewFollowUpDetails.setOnClickListener(this);
        cv = (CardView) itemView.findViewById(R.id.cv_notificationItem);
    }

    public static NotificationListChildVH create(final Context context, ViewGroup parent)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.expandable_list_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        NotificationListChildVH vh = new NotificationListChildVH(view);
        vh.context = context;
        return vh;
    }


    public static void bind(NotificationListChildVH viewHolder, FollowUp followUp)
    {
        viewHolder.followUp = followUp;
        viewHolder.tvTitle.setText(followUp.getFollowUpDate().toString());
        viewHolder.tvName.setText(followUp.getName());
        viewHolder.tvDiscussionsContent.setText(followUp.getDiscussion());

        viewHolder.tvExtraDetails.setText(followUp.getExtraDetails());
        viewHolder.tvHandledBy.setText(followUp.getHandledBy());
        viewHolder.tvCustomerContact.setText(followUp.getPhone());
        viewHolder.tvProductName.setText(followUp.getProduct());

        if (followUp.isRender())
        {
            viewHolder.cv.setVisibility(View.VISIBLE);
        } else
        {
            viewHolder.cv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view)
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
                intent.putExtra("enquiry_id", followUp.getId());
                context.startActivity(intent);
                break;


        }
    }

}
