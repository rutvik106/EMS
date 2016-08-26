package com.example.rutvik.ems;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ComponentFactory.InquiryProductDetails;
import adapters.DropdownProductAdapter;
import extras.AppUtils;
import extras.PostServiceHandler;
import jsonobject.DropdownProduct;

public class ActivitySearchCustomer extends AppCompatActivity
{
    private static final String TAG = AppUtils.APP_TAG + ActivitySearchCustomer.class.getSimpleName();

    AutoCompleteTextView actEnquiryId, actContact, actName, actEmail;

    DropdownProductAdapter adapterName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_customer);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        getSupportActionBar().setTitle("Search Customer");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actEmail = (AutoCompleteTextView) findViewById(R.id.act_customerEmail);
        actEnquiryId = (AutoCompleteTextView) findViewById(R.id.act_enquiryId);
        actContact = (AutoCompleteTextView) findViewById(R.id.act_customerContact);
        actName = (AutoCompleteTextView) findViewById(R.id.act_customerName);

        adapterName = new DropdownProductAdapter(ActivitySearchCustomer.this)
        {

            @Override
            public Filter getFilter()
            {
                return new DropdownProductAdapter.CustomFilter()
                {

                    @Override
                    protected FilterResults performFiltering(CharSequence constraint)
                    {
                        new GetNameAsync(constraint.toString()).execute();
                        return super.performFiltering(constraint);
                    }

                };
            }
        };
        actName.setAdapter(adapterName);
        actName.setThreshold(1);

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


    class GetNameAsync extends AsyncTask<Void, Void, String>
    {
        final String host = PreferenceManager.getDefaultSharedPreferences(ActivitySearchCustomer.this).getString("host", "");

        final String url = host + AppUtils.URL_GET_CUSTOMER_NAME;


        String resp = "";

        final String term;

        class Name implements DropdownProductAdapter.AutoCompleteDropDownItem
        {
            String term;
            int id;

            public Name(String term, int id)
            {
                this.term = term;
                this.id = id;
            }

            @Override
            public String getValue()
            {
                return term;
            }

            @Override
            public int getKey()
            {
                return id;
            }
        }

        public GetNameAsync(String term)
        {
            this.term = term;
        }

        @Override
        protected String doInBackground(Void... voids)
        {

            final Map<String, String> postParam = new HashMap<>();
            postParam.put("term", term);
            new PostServiceHandler(TAG, 2, 2000).doPostRequest(url, postParam, new PostServiceHandler.ResponseCallback()
            {
                @Override
                public void response(int status, String response)
                {
                    if (status == HttpURLConnection.HTTP_OK)
                    {
                        GetNameAsync.this.resp = response;
                    }
                }
            });

            return resp;
        }

        @Override
        protected void onPostExecute(String s)
        {
            if (!resp.equals(""))
            {
                Log.i(TAG, "RESPONSE: " + resp);
                try
                {
                    //adapterName.clear();
                    JSONArray arr = new JSONArray(resp);
                    for (int i = 0; i < arr.length(); i++)
                    {
                        JSONObject obj = arr.getJSONObject(i);
                        adapterName.addDropdownListProduct(new Name(obj.getString("label"), i));
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }
}
