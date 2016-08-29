package com.example.rutvik.ems;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.games.GamesMetadata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import adapters.SimpleFormAdapter;
import extras.AppUtils;
import extras.PostServiceHandler;
import fragments.SimpleFormFragment;

public class ActivityCustomerDetails extends AppCompatActivity
{

    private static final String TAG = AppUtils.APP_TAG + ActivityCustomerDetails.class.getSimpleName();

    SimpleFormAdapter simpleFormAdapter;
    SimpleFormFragment simpleFormFragment;

    SimpleFormAdapter adapterEnquiryDetails;
    SimpleFormFragment fragmentEnquiryDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Customer Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


            simpleFormAdapter.addSimpleTextView(++i, "Customer Name: ", customerDetails.getString("customer_name"));

            simpleFormAdapter.addSimpleTextView(++i, "Email: ", customerDetails.getString("customer_email"));

            JSONArray contact = customerDetails.getJSONArray("customer_contact");
            for (int j = 0; j < contact.length(); j++)
            {
                simpleFormAdapter.addSimpleTextView(++i, "Contact No: ", contact.getJSONObject(j).getString("customer_contact_no"));
            }


            if (simpleFormFragment == null)
            {
                simpleFormFragment = new SimpleFormFragment();
            }

            simpleFormFragment.setData(simpleFormAdapter, "Customer Details");

            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.frag_simpleForm, simpleFormFragment, "CUSTOMER_DETAILS")
                    .commit();

            simpleFormAdapter.notifyDataSetChanged();
        }

        void populateEnquiryDetails(JSONArray customerEnquiries) throws JSONException
        {
            int i = -1;
            adapterEnquiryDetails = new SimpleFormAdapter(ActivityCustomerDetails.this);

            for (int j = 0; j < customerEnquiries.length(); j++)
            {
                adapterEnquiryDetails.addFollowUpSeparator(++i, "Enquiry " + (j + 1));

                final String enquiry_date = customerEnquiries.getJSONObject(j).getString("enquiry_date");
                adapterEnquiryDetails.addSimpleTextView(++i, "Enquiry Date: ", enquiry_date);

                final String enquiry_for = customerEnquiries.getJSONObject(j).getString("enquiry_for");
                adapterEnquiryDetails.addSimpleTextView(++i, "Enquiry For (Product): ", enquiry_for);

                final String enquiry_status = customerEnquiries.getJSONObject(j).getString("enquiry_status");
                adapterEnquiryDetails.addSimpleTextView(++i, "Enquiry Status: ", enquiry_status);

                final String enquiry_managed_by = customerEnquiries.getJSONObject(j).getString("enquiry_managed_by");
                adapterEnquiryDetails.addSimpleTextView(++i, "Enquiry Managed By: ", enquiry_managed_by);

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
