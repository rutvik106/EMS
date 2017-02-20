package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tapandtype.rutvik.ems.R;

import models.NotificationHeader;

/**
 * Created by rutvik on 27-04-2016 at 03:15 PM.
 */
public class NotificationListHeaderVH extends RecyclerView.ViewHolder
{

    final ImageButton ibExpand;
    final NotificationHeader.HeaderExpandCollapseListener listener;
    final Context context;
    TextView tvHeaderTitle, tvChildCount;
    NotificationHeader header;

    public NotificationListHeaderVH(final Context context, View itemView, NotificationHeader.HeaderExpandCollapseListener listener)
    {
        super(itemView);
        this.context = context;
        tvHeaderTitle = (TextView) itemView.findViewById(R.id.tv_elvTitle);
        tvChildCount = (TextView) itemView.findViewById(R.id.tv_elvGroupCount);
        ibExpand = (ImageButton) itemView.findViewById(R.id.ib_expand);
        this.listener = listener;
        ibExpand.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (header.isExpand())
                {

                    Glide.with(context).load(R.mipmap.ic_chevron_right_white_48dp)
                            .into(ibExpand);

                } else
                {

                    Glide.with(context).load(R.mipmap.ic_expand_more_white_48dp)
                            .into(ibExpand);

                }
                NotificationListHeaderVH.this.listener.onExpandOrCollapse(header);
            }
        });
    }

    public static NotificationListHeaderVH create(Context context, ViewGroup parent, NotificationHeader.HeaderExpandCollapseListener listener)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.expandable_list_group, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new NotificationListHeaderVH(context, view, listener);
    }

    public static void bind(final NotificationListHeaderVH viewHolder, final NotificationHeader header)
    {
        viewHolder.header = header;
        viewHolder.tvHeaderTitle.setText(viewHolder.header.getTitle());
        viewHolder.tvChildCount.setText(viewHolder.header.getChildCount());
        if (header.isExpand())
        {

            Glide.with(viewHolder.context).load(R.mipmap.ic_chevron_right_white_48dp)
                    .into(viewHolder.ibExpand);

        } else
        {

            Glide.with(viewHolder.context).load(R.mipmap.ic_expand_more_white_48dp)
                    .into(viewHolder.ibExpand);

        }
    }

}
