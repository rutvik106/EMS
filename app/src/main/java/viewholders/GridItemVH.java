package viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rutvik.ems.R;

/**
 * Created by rutvik on 25-04-2016 at 02:50 PM.
 */
public class GridItemVH extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvLabel;
    public ImageView ivIcon;

    public GridItemVH(View v){
        super(v);
        v.setOnClickListener(this);
        tvLabel = (TextView)itemView.findViewById(R.id.tv_gridItemLabel);
        ivIcon = (ImageView)itemView.findViewById(R.id.iv_gridItemIcon);
    }

    @Override
    public void onClick(View v) {

        switch (getPosition()){



        }

    }
}
