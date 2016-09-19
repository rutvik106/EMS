package com.tapandtype.rutvik.ems;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import ComponentFactory.AppendableTextBox;
import adapters.SimpleFormAdapter;
import extras.AppUtils;
import extras.Log;
import extras.PostServiceHandler;
import fragments.SimpleFormFragment;
import jsonobject.Response;

public class ActivityAddNewCustomer extends AppCompatActivity
{

    public final static String TAG = AppUtils.APP_TAG + ActivityAddNewCustomer.class.getSimpleName();

    private Toolbar mToolbar;

    SimpleFormAdapter customerDetailsAdapter;
    SimpleFormFragment customerDetailsFragment;

    LinearLayout fragSimpleForm;

    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_customer);

        app = (App) getApplication();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null)
        {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Add New Customer");
            //getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            /** final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
             upArrow.setColorFilter(getResources().getColor(R.color.mdtp_white), PorterDuff.Mode.SRC_ATOP);
             getSupportActionBar().setHomeAsUpIndicator(upArrow);*/
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

        customerDetailsAdapter.addAppendableTextBox("Contact No*", "mobile_no", ++i,
                app.getHost() + AppUtils.URL_EXACT_CONTACT_NO,
                new ActivityAddNewCustomer.AppendableTextBoxUrlListener(),
                InputType.TYPE_CLASS_NUMBER);

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
        addCustomer.getBackground().setColorFilter(0xFF00695C, PorterDuff.Mode.MULTIPLY);
        addCustomer.setOnClickListener(new OnAddCustomer());
        fragSimpleForm.addView(addCustomer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class OnAddCustomer implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {

            final Map postParams = new HashMap();

            postParams.put("method", "add_customer");

            postParams.put("session_id", app.getUser().getSession_id());

            for (int i = 0; i < customerDetailsAdapter.getItemCount(); i++)
            {
                Log.i(TAG, "DATA: " + customerDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getValue());


                postParams.put(customerDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getName(),
                        customerDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getValue());

            }

            new AsyncTask<Void, Void, Void>()
            {

                String response = "";

                Response jsonResponse;

                @Override
                protected Void doInBackground(Void... voids)
                {

                    new PostServiceHandler(TAG, 2, 2000)
                            .doPostRequest(app.getHost() + AppUtils.URL_WEBSERVICE,
                                    postParams,
                                    new PostServiceHandler.ResponseCallback()
                                    {
                                        @Override
                                        public void response(int status, String r)
                                        {
                                            response = r;
                                        }
                                    });

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid)
                {
                    try
                    {
                        jsonResponse = new Response(response);
                        if (jsonResponse.isStatusOk())
                        {
                            Toast.makeText(ActivityAddNewCustomer.this,
                                    jsonResponse.getMessage(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                            ActivityAddNewCustomer.this.finish();
                        } else
                        {
                            Toast.makeText(ActivityAddNewCustomer.this,
                                    jsonResponse.getMessage(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (JSONException e)
                    {
                        Log.i(TAG, e.getMessage());
                        Toast.makeText(ActivityAddNewCustomer.this,
                                "Something went wrong, Please try again later",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }.execute();

        }
    }

    class AppendableTextBoxUrlListener implements AppendableTextBox.OnUrlTriggered
    {
        @Override
        public void urlTriggered(EditText etContact, TextView tvDuplicateErrorMsg, String response)
        {
            etContact.setTextColor(Color.RED);
            final String msg = "Contact already exist for id " + response;
            tvDuplicateErrorMsg.setText(msg);
        }
    }

}
