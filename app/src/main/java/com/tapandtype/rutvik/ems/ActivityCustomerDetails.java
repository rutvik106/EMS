package com.tapandtype.rutvik.ems;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import adapters.SimpleFormAdapter;
import extras.AppUtils;
import extras.PostServiceHandler;
import fragments.SimpleFormFragment;
import models.FollowUpSeparator;

public class ActivityCustomerDetails extends AppCompatActivity
{

    private static final String TAG = AppUtils.APP_TAG + ActivityCustomerDetails.class.getSimpleName();

    SimpleFormAdapter simpleFormAdapter;
    SimpleFormFragment simpleFormFragment;

    SimpleFormAdapter adapterEnquiryDetails;
    SimpleFormFragment fragmentEnquiryDetails;

    String customerName, customerContact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Customer Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btn_addEnquiry).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(ActivityCustomerDetails.this, ActivityAddNewInquiry.class));
            }
        });


        final String customerId = getIntent().getStringExtra("customer_id");

        if (!customerId.isEmpty())
        {
            new GetCustomerDetailsAsync(customerId).execute();
        }

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

    public class GetCustomerDetailsAsync extends AsyncTask<Void, Void, Void>
    {

        String response = "";

        final String url;

        final String customerId;

        public GetCustomerDetailsAsync(String customerId)
        {
            this.customerId = customerId;
            url = PreferenceManager.getDefaultSharedPreferences(ActivityCustomerDetails.this).getString("host", "")
                    + AppUtils.URL_WEBSERVICE;

        }

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected Void doInBackground(Void... voids)
        {

            Map<String, String> postParam = new HashMap<>();
            postParam.put("method", "get_customer_details");
            postParam.put("customer_id", customerId);

            new PostServiceHandler(TAG, 2, 2000).doPostRequest(url, postParam, new PostServiceHandler.ResponseCallback()
            {
                @Override
                public void response(int status, String response)
                {
                    if (status == HttpURLConnection.HTTP_OK)
                    {
                        GetCustomerDetailsAsync.this.response = response;
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if (!response.isEmpty())
            {
                try
                {
                    JSONObject root = new JSONObject(response);
                    JSONObject customerDetails = root.getJSONObject("customer_details");
                    JSONArray customerEnquiries = root.getJSONArray("customer_enquiry_details");
                    setupAdapter(customerDetails, customerEnquiries);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }


        void setupAdapter(JSONObject customerDetails, JSONArray customerEnquiries)
        {

            try
            {
                populateCustomerDetails(customerDetails);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            try
            {
                populateEnquiryDetails(customerEnquiries);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

        }


        void populateCustomerDetails(JSONObject customerDetails) throws JSONException
        {
            int i = -1;

            simpleFormAdapter = new SimpleFormAdapter(ActivityCustomerDetails.this);

            simpleFormAdapter.addSimpleTextView(++i, "Customer Name: ", customerDetails.getString("customer_name"), null);

            customerName = customerDetails.getString("customer_name");

            simpleFormAdapter.addSimpleTextView(++i, "Email: ", customerDetails.getString("customer_email"), null);

            JSONArray contact = customerDetails.getJSONArray("customer_contact");

            for (int j = 0; j < contact.length(); j++)
            {
                final String contactNo = contact.getJSONObject(j).getString("customer_contact_no");
                customerContact = customerContact + ", " + contact;
                simpleFormAdapter.addSimpleTextView(++i, "Contact No: ", contactNo, new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (contactNo != null && contactNo != "")
                        {
                            Intent supportIntent = new Intent(Intent.ACTION_DIAL);
                            supportIntent.setData(Uri.parse("tel:" + contactNo));
                            startActivity(supportIntent);
                        }
                    }
                });
            }


            if (simpleFormFragment == null)
            {
                simpleFormFragment = new SimpleFormFragment();
            }

            simpleFormFragment.setData(simpleFormAdapter, "Customer Details");

            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.frag_simpleForm, simpleFormFragment, "CUSTOMER_DETAILS")
                    .commitAllowingStateLoss();

            simpleFormAdapter.notifyDataSetChanged();
        }

        void populateEnquiryDetails(JSONArray customerEnquiries) throws JSONException
        {
            int i = -1;
            adapterEnquiryDetails = new SimpleFormAdapter(ActivityCustomerDetails.this);

            for (int j = 0; j < customerEnquiries.length(); j++)
            {
                final String enquiryFormId = customerEnquiries.getJSONObject(j).getString("enquiry_form_id");
                adapterEnquiryDetails
                        .addFollowUpSeparator(++i, new FollowUpSeparator(enquiryFormId, customerName, customerContact, "Enquiry " + (j + 1)));

                final String enquiry_date = customerEnquiries.getJSONObject(j).getString("enquiry_date");
                adapterEnquiryDetails.addSimpleTextView(++i, "Enquiry Date: ", enquiry_date, null);

                final String enquiry_for = customerEnquiries.getJSONObject(j).getString("enquiry_for");
                adapterEnquiryDetails.addSimpleTextView(++i, "Enquiry For (Product): ", enquiry_for, null);

                final String enquiry_status = customerEnquiries.getJSONObject(j).getString("enquiry_status");
                adapterEnquiryDetails.addSimpleTextView(++i, "Enquiry Status: ", enquiry_status, null);

                final String enquiry_managed_by = customerEnquiries.getJSONObject(j).getString("enquiry_managed_by");
                adapterEnquiryDetails.addSimpleTextView(++i, "Enquiry Managed By: ", enquiry_managed_by, null);

            }


            if (fragmentEnquiryDetails == null)
            {
                fragmentEnquiryDetails = new SimpleFormFragment();
            }
            fragmentEnquiryDetails.setData(adapterEnquiryDetails, "Generated Enquiries");
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.frag_simpleForm, fragmentEnquiryDetails, "GENERATED_ENQUIRIES")
                    .commit();
            adapterEnquiryDetails.notifyDataSetChanged();

        }

    }

}
