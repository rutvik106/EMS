package ComponentFactory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rutvik.ems.R;

import java.util.ArrayList;

/**
 * Created by ACER on 04-Feb-16.
 */
public class MySpinner extends LinearLayout
{

    public ArrayAdapter getAdapter()
    {
        return adapter;
    }

    ArrayAdapter adapter;

    TextView textView;

    public Spinner spinner;

    Context context;

    public MySpinner(Context context)
    {

        super(context);

        this.context = context;

        View v = LayoutInflater.from(context).inflate(R.layout.my_spinner, null, false);

        this.setOrientation(VERTICAL);

        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        textView = (TextView) v.findViewById(R.id.tv_spnLabel);

        spinner = (Spinner) v.findViewById(R.id.spn_mySpinner);


        this.addView(v);


    }

    public void setTitle(String title)
    {
        textView.setText(title);
    }

    public String getTitle()
    {
        return textView.getText().toString();
    }

    public void setAdapterArray(ArrayList<String> itemList)
    {
        adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, itemList);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
