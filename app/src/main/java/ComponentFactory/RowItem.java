package ComponentFactory;

import android.support.v7.widget.RecyclerView;

/**
 * Created by rutvik on 06-07-2016 at 12:56 PM.
 */

public interface RowItem
{
    Object getValue();

    void setValue(Object object);

    RecyclerView.ViewHolder getView();

    int getComponentType();

    String getJSONName();

    String getJSONKey();

    String getJSONValue();

    String getLabel();

    String getName();
}
