package viewholders;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rutvik.ems.R;

import adapters.NotificationListAdapter;
import component.NotificationListItemComp;
import models.NotificationHeader;

/**
 * Created by rutvik on 27-04-2016 at 03:15 PM.
 */
public class NotificationListHeaderVH extends RecyclerView.ViewHolder {

    TextView tvHeaderTitle, tvChildCount;

    final ImageButton ibExpand;

    final NotificationHeader.HeaderExpandCollapseListener listener;

    NotificationHeader header;

    final Handler mHandler=new Handler();

    public NotificationListHeaderVH(View itemView, NotificationHeader.HeaderExpandCollapseListener listener) {
        super(itemView);
        tvHeaderTitle = (TextView) itemView.findViewById(R.id.tv_elvTitle);
        tvChildCount = (TextView) itemView.findViewById(R.id.tv_elvGroupCount);
        ibExpand=(ImageButton) itemView.findViewById(R.id.ib_expand);
        this.listener=listener;
        ibExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(header.isExpand()){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ibExpand.setImageResource(R.mipmap.ic_chevron_right_white_48dp);
                        }
                    });
                }
                else{
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ibExpand.setImageResource(R.mipmap.ic_expand_more_white_48dp);
                        }
                    });
                }
                NotificationListHeaderVH.this.listener.onExpandOrCollapse(header);
            }
        });
    }

    public static NotificationListHeaderVH create(Context context, ViewGroup parent, NotificationHeader.HeaderExpandCollapseListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.expandable_list_group,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new NotificationListHeaderVH(view,listener);
    }

    public static void bind(NotificationListHeaderVH viewHolder, NotificationHeader header) {
        viewHolder.header=header;
        viewHolder.tvHeaderTitle.setText(viewHolder.header.getTitle());
        viewHolder.tvChildCount.setText(viewHolder.header.getChildCount());
    }

}
