package ComponentFactory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.rutvik.ems.App;
import com.example.rutvik.ems.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import extras.AppUtils;

/**
 * Created by rutvik on 07-07-2016 at 02:14 PM.
 */

public class AppendableTextBox extends LinearLayout
{
    public static final String TAG = AppUtils.APP_TAG + AppendableTextBox.class.getSimpleName();

    Context context;

    ArrayList<AppendedTextBox> appendedTextBoxList = new ArrayList<>();

    LinearLayout llRootLayout;

    public TextView tvRootTextView;

    Button btnRootButton;

    Map values = new HashMap();

    String valueName;

    public void setValueName(String name)
    {
        valueName = name + "[]";
    }

    public AppendableTextBox(final Context context)
    {
        super(context);

        this.context = context;

        View v = LayoutInflater.from(context).inflate(R.layout.appendable_text_box, null, false);

        llRootLayout = (LinearLayout) v.findViewById(R.id.ll_appendableLayout);

        tvRootTextView = (TextView) v.findViewById(R.id.tv_appendableTextView);

        btnRootButton = (Button) v.findViewById(R.id.btn_appendTextView);

        btnRootButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                appendedTextBoxList.add(new AppendedTextBox());
            }
        });

        this.setOrientation(VERTICAL);

        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        this.addView(v);


    }

    public List getValues()
    {
        Log.i(TAG, "COLLECTING VALUES FOR APPENDABLE TEXT BOX");
        List<String> values = new ArrayList<>();
        Log.i(TAG,"ROOT TEXT VALUE: "+tvRootTextView.getText());
        values.add(tvRootTextView.getText().toString());
        for (AppendedTextBox t : appendedTextBoxList)
        {
            Log.i(TAG,"APPENDED TEXT BOX VALUE: "+t.getText());
            values.add(t.getText());
        }
        return values;
    }

    class AppendedTextBox
    {
        LinearLayout ll;

        MyTextBox tv;

        Button b;

        public String getText()
        {
            return tv.editText.getText().toString();
        }

        public AppendedTextBox()
        {
            ll = new LinearLayout(context);

            ll.setOrientation(HORIZONTAL);

            ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            ll.setWeightSum(6);


            tv = new MyTextBox(context);
            tv.setHint(tvRootTextView.getHint());
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f));


            b = new Button(context);
            b.setText("-");
            b.getBackground().setColorFilter(0xFFFF9800, PorterDuff.Mode.MULTIPLY);
            b.setTextSize(18);
            b.setTextColor(Color.parseColor("#ffffff"));
            b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 5f));
            b.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    llRootLayout.removeView(ll);
                }
            });

            ll.addView(tv);
            ll.addView(b);

            llRootLayout.addView(ll);

        }

    }

}
