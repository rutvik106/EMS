package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rutvik on 12/5/2016 at 8:46 AM.
 */

public abstract class GenericViewHolder<E> extends RecyclerView.ViewHolder
{

    final Context context;

    public GenericViewHolder(Context context, View itemView)
    {
        super(itemView);
        this.context = context;
    }

    public abstract void bind(GenericViewHolder vh, E model);

}
