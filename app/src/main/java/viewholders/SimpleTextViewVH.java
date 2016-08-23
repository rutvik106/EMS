package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rutvik.ems.R;

import java.util.HashMap;
import java.util.Map;

import ComponentFactory.Component;

/**
 * Created by rutvik on 10-08-2016 at 02:17 PM.
 */

public class SimpleTextViewVH extends RecyclerView.ViewHolder
{

    TextView tvLabel, tvValue;

    Map model=new HashMap();

    public SimpleTextViewVH(View itemView)
    {
        super(itemView);
        tvLabel = (TextView) itemView.findViewById(R.id.tv_label);
        tvValue = (TextView) itemView.findViewById(R.id.tv_value);
    }

    public static SimpleTextViewVH create(Context context, ViewGroup parent)
    {
        SimpleTextViewVH vh = new SimpleTextViewVH(LayoutInflater
                .from(context)
                .inflate(R.layout.single_simple_text_view_row, parent, false));

        return vh;
    }

    public static void bind(SimpleTextViewVH vh, Component component)
    {
        vh.model=(Map) component.getObject();
        vh.tvLabel.setText(vh.model.get("label").toString());
        vh.tvValue.setText(vh.model.get("value").toString());
    }

}
