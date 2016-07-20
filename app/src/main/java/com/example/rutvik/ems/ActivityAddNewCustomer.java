package com.example.rutvik.ems;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import adapters.SimpleFormAdapter;
import extras.AppUtils;
import extras.Log;
import extras.PostServiceHandler;
import fragments.SimpleFormFragment;

public class ActivityAddNewCustomer extends AppCompatActivity
{

    public final static String TAG = AppUtils.APP_TAG + ActivityAddNewCustomer.class.getSimpleName();

    private Toolbar mToolbar;

    SimpleFormAdapter customerDetailsAdapter;
    SimpleFormFragment customerDetailsFragment;

    LinearLayout fragSimpleForm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_customer);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null)
        {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Add New Customer");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fragSimpleForm = (LinearLayout) findViewById(R.id.frag_simpleForm);

        if (savedInstanceState != null)
        {
            Log.i(TAG, "RESTORING SAVED INSTANCE");
            customerDetailsFragment = (SimpleFormFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ADD_NEW_INQUIRY");
        }

        populateCustomerDetails();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        try
        {
            getSupportFragmentManager().putFragment(outState, "ADD_NEW_CUSTOMER", customerDetailsFragment);
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }

    }

    private void populateCustomerDetails()
    {

        int i = -1;

        customerDetailsAdapter = new SimpleFormAdapter(this);

        Map<String, String> customerPrefixMap = new HashMap<>();
        customerPrefixMap.put("1", "Mr.");
        customerPrefixMap.put("3", "Ms.");
        customerDetailsAdapter.addSpinner("Customer Prefix", "prefix_id", customerPrefixMap, ++i);

        customerDetailsAdapter.addTextBox("Customer Name*", "customer_name", ++i, InputType.TYPE_CLASS_TEXT, true, "");

        customerDetailsAdapter.addAppendableTextBox("Contact No*", "mobile_no", ++i);

        customerDetailsAdapter.addTextBox("Email Address", "email_id", ++i, InputType.TYPE_CLASS_TEXT, true, "");

        if (customerDetailsFragment == null)
        {
            customerDetailsFragment = new SimpleFormFragment();
        }

        customerDetailsFragment.setData(customerDetailsAdapter, "Customer Details");

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag_simpleForm, customerDetailsFragment, "ADD_NEW_CUSTOMER")
                .commit();

        customerDetailsAdapter.notifyDataSetChanged();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                setAddCustomerButton();
            }
        }, 10);

    }

    public void setAddCustomerButton()
    {

        Button addCustomer = new Button(this);
        ViewGroup.LayoutParams lParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        addCustomer.setLayoutParams(lParams);
        addCustomer.setText("Add Customer");
        addCustomer.setTextColor(Color.WHITE);
        addCustomer.getBackground().setColorFilter(0xFFFF9800, PorterDuff.Mode.MULTIPLY);
        addCustomer.setOnClickListener(new OnAddCustomer());
        fragSimpleForm.addView(addCustomer);

    }

    class OnAddCustomer implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {

            final Map postParams = new HashMap();

            for (int i = 0; i < customerDetailsAdapter.getItemCount(); i++)
            {
                Log.i(TAG, "DATA: " + customerDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getValue());

                postParams.put(customerDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getName(),
                        customerDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getValue());

            }

            new AsyncTask<Void, Void, Void>()
            {

                @Override
                protected Void doInBackground(Void... voids)
                {

                    new PostServiceHandler(TAG, 2, 2000)
                            .doPostRequest("http://192.168.0.100/ems/admin/directCustomer/index.php?action=add",
                                    postParams,
                                    new PostServiceHandler.ResponseCallback()
                                    {
                                        @Override
                                        public void response(int status, String response)
                                        {
                                            Log.i(TAG, "HTTP STATUS: " + status);
                                            Log.i(TAG, "HTTP RESPONSE: " + response);
                                        }
                                    });

                    return null;
                }
            }.execute();

        }
    }

}
