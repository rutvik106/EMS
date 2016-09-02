package ComponentFactory;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tapandtype.rutvik.ems.R;

import java.util.ArrayList;

import adapters.MyCheckListAdapter;
import models.SingleCheckListItem;

/**
 * Created by ACER on 04-Feb-16.
 */
public class MyCheckListSpinner extends LinearLayout
{

    TextView textView;

    public Spinner spinner;

    Context context;

    public MyCheckListAdapter adapter;


    public MyCheckListSpinner(Context context)
    {

        super(context);

        this.context = context;

        this.setOrientation(VERTICAL);

        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        textView = new TextView(context);

        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        textView.setTextColor(getResources().getColor(R.color.primary));


        spinner = new Spinner(context);

        spinner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        int paddingPixel = 4;
        float density = context.getResources().getDisplayMetrics().density;
        int paddingDp = (int) (paddingPixel * density);


        textView.setPadding(paddingDp, 0, 0, 0);

        addView(textView);
        addView(spinner);

    }

    public void setTitle(String title)
    {
        textView.setText(title);
    }

    public void setAdapterArray(ArrayList<SingleCheckListItem> items)
    {
        if (adapter == null)
        {
            adapter = new MyCheckListAdapter(context, items);
            spinner.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }


}
