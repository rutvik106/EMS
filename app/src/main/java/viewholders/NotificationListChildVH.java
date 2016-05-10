package viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rutvik.ems.R;

import component.NotificationListItemComp;
import models.FollowUp;

/**
 * Created by rutvik on 27-04-2016 at 03:16 PM.
 */
public class NotificationListChildVH extends RecyclerView.ViewHolder {

    TextView tvTitle, tvName, tvDiscussionsContent;

    CardView cv;

    public NotificationListChildVH(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvDiscussionsContent = (TextView) itemView.findViewById(R.id.tv_discussionsContent);
        cv = (CardView) itemView.findViewById(R.id.cv_notificationItem);
    }

    public static NotificationListChildVH create(Context context,ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.expandable_list_item, null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new NotificationListChildVH(view);
    }


    public static void bind(NotificationListChildVH viewHolder, FollowUp followUp) {
        viewHolder.tvTitle.setText(followUp.getFollowUpDate().toString());
        viewHolder.tvName.setText(followUp.getName());
        viewHolder.tvDiscussionsContent.setText(followUp.getDiscussion());
        if(followUp.isRender()){
            viewHolder.cv.setVisibility(View.VISIBLE);
        }else{
            viewHolder.cv.setVisibility(View.GONE);
        }
    }

}
