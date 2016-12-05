package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import viewholders.GenericViewHolder;

/**
 * Created by rutvik on 12/5/2016 at 8:41 AM.
 */

abstract class GenericArrayAdapter<E, T extends GenericViewHolder<E>> extends RecyclerView.Adapter
{

    final Context context;

    final List<E> modelList;

    public GenericArrayAdapter(final Context context)
    {
        this.context = context;
        modelList = new ArrayList<>();
    }

    public void addNewModel(E model)
    {
        modelList.add(model);
        notifyItemInserted(modelList.size());
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount()
    {
        return modelList.size();
    }
}
