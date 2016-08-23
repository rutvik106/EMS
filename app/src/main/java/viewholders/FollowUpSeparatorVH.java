package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rutvik.ems.R;

/**
 * Created by rutvik on 23-08-2016 at 05:38 PM.
 */

public class FollowUpSeparatorVH extends RecyclerView.ViewHolder
{

    TextView tvFollowUpCount;

    public FollowUpSeparatorVH(View itemView)
    {
        super(itemView);
        tvFollowUpCount = (TextView) itemView.findViewById(R.id.tv_followUpCount);
    }

    public static FollowUpSeparatorVH create(final Context context, final ViewGroup parent)
    {

        return new FollowUpSeparatorVH(LayoutInflater.from(context).inflate(R.layout.single_follow_up_separator, parent, false));

    }

    public static void bind(FollowUpSeparatorVH vh, String count)
    {
        vh.tvFollowUpCount.setText(count);
    }

}
