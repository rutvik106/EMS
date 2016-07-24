package ComponentFactory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.rutvik.ems.App;
import com.example.rutvik.ems.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import extras.AppUtils;
import extras.PostServiceHandler;

/**
 * Created by rutvik on 07-07-2016 at 02:14 PM.
 */

public class AppendableTextBox extends LinearLayout
{
    public static final String TAG = AppUtils.APP_TAG + AppendableTextBox.class.getSimpleName();

    Context context;

    ArrayList<AppendedTextBox> appendedTextBoxList = new ArrayList<>();

    LinearLayout llRootLayout;

    public EditText etRootTextView;

    FrameLayout flDuplicateError;

    TextView tvDuplicateErrorMsg;

    Button btnRootButton;

    Map values = new HashMap();

    String valueName;

    String triggerUrl = "";

    public void setValueName(String name)
    {
        valueName = name + "[]";
    }

    OnUrlTriggered urlTriggeredListener;

    private MyTextWatcher myTextWatcher;

/*    public void setTriggerUrl(String url)
    {
        triggerUrl = url;
    }*/

    public AppendableTextBox(final Context context, OnUrlTriggered urlTriggeredListener)
    {
        super(context);

        this.context = context;

        this.urlTriggeredListener = urlTriggeredListener;

        View v = LayoutInflater.from(context).inflate(R.layout.appendable_text_box, this, false);

        flDuplicateError = (FrameLayout) v.findViewById(R.id.fl_duplicateError);

        tvDuplicateErrorMsg = (TextView) v.findViewById(R.id.tv_duplicateErrorMsg);

        llRootLayout = (LinearLayout) v.findViewById(R.id.ll_appendableLayout);

        etRootTextView = (EditText) v.findViewById(R.id.tv_appendableTextView);

        final String url = PreferenceManager.getDefaultSharedPreferences(context).getString("host", "")
                + AppUtils.URL_EXACT_CONTACT_NO;

        triggerUrl = url;

        myTextWatcher = new MyTextWatcher(url,
                flDuplicateError, tvDuplicateErrorMsg,
                etRootTextView, urlTriggeredListener);

        etRootTextView.addTextChangedListener(myTextWatcher);

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

    public String getValues()
    {
        Log.i(TAG, "COLLECTING VALUES FOR APPENDABLE TEXT BOX");
        JSONArray values = new JSONArray();

        Log.i(TAG, "ROOT TEXT VALUE: " + etRootTextView.getText());
        values.put(etRootTextView.getText().toString());
        for (AppendedTextBox t : appendedTextBoxList)
        {
            Log.i(TAG, "APPENDED TEXT BOX VALUE: " + t.getText());
            values.put(t.getText());
        }
        return values.toString();


    }

    class AppendedTextBox
    {
        LinearLayout ll;

        FrameLayout fl;

        TextView tvErrorMsg;

        MyTextBox tv;

        Button b;

        MyTextWatcher myTextWatcher;

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
            tv.setHint(etRootTextView.getHint());
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
                    llRootLayout.removeView(fl);
                }
            });

            ll.addView(tv);
            ll.addView(b);

            llRootLayout.addView(ll);

            fl = new FrameLayout(context);
            fl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            tvErrorMsg = new TextView(context);
            tvErrorMsg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            tvErrorMsg.setTextColor(getResources().getColor(R.color.accent));
            fl.addView(tvErrorMsg);
            fl.setVisibility(GONE);

            llRootLayout.addView(fl);

            myTextWatcher = new MyTextWatcher(triggerUrl, fl, tvErrorMsg, tv.editText, urlTriggeredListener);

            tv.editText.addTextChangedListener(myTextWatcher);

        }

    }

    static class MyTextWatcher implements TextWatcher
    {

        String triggerUrl;

        EditText etContact;

        OnUrlTriggered urlTriggeredListener;

        FrameLayout flDuplicateError;

        TextView tvDuplicateErrorMsg;

        public MyTextWatcher(String triggerUrl, FrameLayout flDuplicateError, TextView tvDuplicateErrorMsg, EditText etContact, OnUrlTriggered urlTriggeredListener)
        {
            this.etContact = etContact;
            this.urlTriggeredListener = urlTriggeredListener;
            this.triggerUrl = triggerUrl;
            this.flDuplicateError = flDuplicateError;
            this.tvDuplicateErrorMsg = tvDuplicateErrorMsg;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            if (charSequence.length() > 9)
            {
                new CheckDuplicateContact(triggerUrl, flDuplicateError, tvDuplicateErrorMsg, etContact, urlTriggeredListener)
                        .execute();
            } else if (charSequence.length() == 9)
            {
                flDuplicateError.setVisibility(GONE);
                etContact.setTextColor(Color.BLACK);
            }
        }

        @Override
        public void afterTextChanged(Editable editable)
        {

        }
    }

    static class CheckDuplicateContact extends AsyncTask<Void, Void, String>
    {

        String triggerUrl;

        EditText etContact;

        OnUrlTriggered urlTriggeredListener;

        FrameLayout flDuplicateError;

        TextView tvDuplicateErrorMsg;

        public CheckDuplicateContact(String triggerUrl, FrameLayout flDuplicateError, TextView tvDuplicateErrorMsg, EditText etContact, OnUrlTriggered urlTriggeredListener)
        {
            this.etContact = etContact;
            this.urlTriggeredListener = urlTriggeredListener;
            this.triggerUrl = triggerUrl;
            this.flDuplicateError = flDuplicateError;
            this.tvDuplicateErrorMsg = tvDuplicateErrorMsg;
        }

        @Override
        protected String doInBackground(Void... voids)
        {
            String response = "";

            try
            {
                response = new PostServiceHandler(TAG, 2, 2000).doGet(triggerUrl + "?q=" + etContact.getText());
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return response;
        }


        @Override
        protected void onPostExecute(String s)
        {
            Log.i(TAG, "DUPLICATE CONTACT: " + s);
            if (!s.equals("0") && !s.isEmpty())
            {
                flDuplicateError.setVisibility(VISIBLE);
                urlTriggeredListener.urlTriggered(etContact, tvDuplicateErrorMsg, s);
            } else
            {
                flDuplicateError.setVisibility(GONE);
            }

        }

    }

    public static interface OnUrlTriggered
    {

        void urlTriggered(EditText etContact, TextView tvDuplicateErrorMsg, String response);

    }

}
