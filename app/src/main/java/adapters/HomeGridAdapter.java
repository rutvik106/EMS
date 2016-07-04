package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rutvik.ems.R;

import java.util.List;

import models.GridItem;
import viewholders.GridItemVH;

/**
 * Created by rutvik on 25-04-2016 at 02:54 PM.
 */
public class HomeGridAdapter extends RecyclerView.Adapter<GridItemVH> {

    private Context context;

    private List<GridItem> itemList;
    public HomeGridAdapter(Context context,List<GridItem> itemList) {
        this.context=context;
        this.itemList = itemList;
    }


    @Override
    public GridItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_home_grid_item, null);
        return new GridItemVH(context,layoutView);
    }
    @Override
    public void onBindViewHolder(GridItemVH holder, int position) {
        holder.tvLabel.setText(itemList.get(position).getLabel());
        holder.ivIcon.setImageResource(itemList.get(position).getImage());
    }
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


}
