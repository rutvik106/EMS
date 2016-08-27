package com.example.rutvik.ems;

import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import adapters.DropdownProductAdapter;
import extras.AppUtils;
import extras.PostServiceHandler;
import jsonobject.DropdownProduct;

public class ActivitySearchCustomer extends AppCompatActivity
{
    private static final String TAG = AppUtils.APP_TAG + ActivitySearchCustomer.class.getSimpleName();

    private AutoCompleteTextView actEnquiryId, actContact, actName, actEmail;

    private DropdownProductAdapter adapterName, adapterContact, adapterEmail, adapterEnquiry;

    private GetSearchData getSearchDataName, getSearchDataContact, getSearchDataEmail, getSearchDataEnquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_customer);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        getSupportActionBar().setTitle("Search Customer");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String host = PreferenceManager.getDefaultSharedPreferences(ActivitySearchCustomer.this).getString("host", "");

        final String urlName = host + AppUtils.URL_GET_CUSTOMER_NAME;
        final String urlContact = host + AppUtils.URL_GET_CUSTOMER_CONTACT;
        final String urlEmail = host + AppUtils.URL_GET_CUSTOMER_EMAIL;
        final String urlEnquiry = host + AppUtils.URL_GET_ENQUIRY_ID;

        actEmail = (AutoCompleteTextView) findViewById(R.id.act_customerEmail);
        adapterEmail = new DropdownProductAdapter(ActivitySearchCustomer.this)
        {
            @Override
            public Filter getFilter()
            {
                return new DropdownProductAdapter.CustomFilter()
                {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint)
                    {
                        if (getSearchDataEmail == null)
                        {
                            getSearchDataEmail = new GetSearchData(constraint.toString(), urlEmail, adapterEmail);
                        } else
                        {
                            getSearchDataEmail.cancel(true);
                            getSearchDataEmail = new GetSearchData(constraint.toString(), urlEmail, adapterEmail);
                        }
                        getSearchDataEmail.execute();
                        return super.performFiltering(constraint);
                    }
                };
            }
        };
        actEmail.setAdapter(adapterEmail);
        actEmail.setThreshold(3);


        actEnquiryId = (AutoCompleteTextView) findViewById(R.id.act_enquiryId);
        adapterEnquiry = new DropdownProductAdapter(ActivitySearchCustomer.this)
        {
            @Override
            public Filter getFilter()
            {
                return new DropdownProductAdapter.CustomFilter()
                {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint)
                    {
                        if (getSearchDataEnquiry == null)
                        {
                            getSearchDataEnquiry = new GetSearchData(constraint.toString(), urlEnquiry, adapterEnquiry);
                        } else
                        {
                            getSearchDataEnquiry.cancel(true);
                            getSearchDataEnquiry = new GetSearchData(constraint.toString(), urlEnquiry, adapterEnquiry);
                        }
                        getSearchDataEnquiry.execute();
                        return super.performFiltering(constraint);
                    }

                };
            }
        };
        actEnquiryId.setAdapter(adapterEnquiry);
        actEnquiryId.setThreshold(3);


        actContact = (AutoCompleteTextView) findViewById(R.id.act_customerContact);
        adapterContact = new DropdownProductAdapter(ActivitySearchCustomer.this)
        {
            @Override
            public Filter getFilter()
            {
                return new DropdownProductAdapter.CustomFilter()
                {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint)
                    {
                        if (getSearchDataContact == null)
                        {
                            getSearchDataContact = new GetSearchData(constraint.toString(), urlContact, adapterContact);
                        } else
                        {
                            getSearchDataContact.cancel(true);
                            getSearchDataContact = new GetSearchData(constraint.toString(), urlContact, adapterContact);
                        }
                        getSearchDataContact.execute();
                        return super.performFiltering(constraint);
                    }

                };
            }
        };
        actContact.setAdapter(adapterContact);
        actContact.setThreshold(3);


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
                        if (getSearchDataName == null)
                        {
                            getSearchDataName = new GetSearchData(constraint.toString(), urlName, adapterName);
                        } else
                        {
                            getSearchDataName.cancel(true);
                            getSearchDataName = new GetSearchData(constraint.toString(), urlName, adapterName);
                        }
                        getSearchDataName.execute();
                        return super.performFiltering(constraint);
                    }

                };
            }
        };
        actName.setAdapter(adapterName);
        actName.setThreshold(3);

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


    static class GetSearchData extends AsyncTask<Void, Void, String>
    {

        final String url;

        String resp = "";

        final String term;

        DropdownProductAdapter adapter;

        class Label implements DropdownProductAdapter.AutoCompleteDropDownItem
        {
            String term;
            int id;

            public Label(String term, int id)
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

        public GetSearchData(final String term, final String url, final DropdownProductAdapter adapter)
        {
            this.url = url;
            this.term = term;
            this.adapter = adapter;
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
                        GetSearchData.this.resp = response;
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
                        adapter.addDropdownListProduct(new Label(obj.getString("label"), i));
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }
}
