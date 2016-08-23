package viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rutvik.ems.ActivityAddNewCustomer;
import com.example.rutvik.ems.ActivityAddNewInquiry;
import com.example.rutvik.ems.ActivitySearchCustomer;
import com.example.rutvik.ems.R;
import com.example.rutvik.ems.TakeFollowUp;

/**
 * Created by rutvik on 25-04-2016 at 02:50 PM.
 */
public class GridItemVH extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView tvLabel;
    public ImageView ivIcon;
    private Context context;

    public GridItemVH(Context context, View v)
    {
        super(v);
        this.context = context;
        v.setOnClickListener(this);
        tvLabel = (TextView) itemView.findViewById(R.id.tv_gridItemLabel);
        ivIcon = (ImageView) itemView.findViewById(R.id.iv_gridItemIcon);
    }

    @Override
    public void onClick(View v)
    {
        switch (getAdapterPosition())
        {
            case 0:
                context.startActivity(new Intent(context, ActivityAddNewInquiry.class));
                break;
            case 1:
                context.startActivity(new Intent(context, ActivitySearchCustomer.class));
                break;
            case 2:
                context.startActivity(new Intent(context, ActivityAddNewCustomer.class));
                break;
        }
    }
}
