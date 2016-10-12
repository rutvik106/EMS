package com.tapandtype.rutvik.ems;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import adapters.DropdownProductAdapter;
import extras.AppUtils;
import extras.PostServiceHandler;

public class ActivitySearchCustomer extends AppCompatActivity
{
    private static final String TAG = AppUtils.APP_TAG + ActivitySearchCustomer.class.getSimpleName();

    private AutoCompleteTextView actEnquiryId, actContact, actName, actEmail;

    private DropdownProductAdapter adapterName, adapterContact, adapterEmail, adapterEnquiry;

    private GetSearchData getSearchDataName, getSearchDataContact, getSearchDataEmail, getSearchDataEnquiry;

    private Button btnSearchCustomer;

    private ProgressBar pbSearchInProgress;

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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        {
                            getSearchDataEmail.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else
                        {
                            getSearchDataEmail.execute();
                        }
                        return super.performFiltering(constraint);
                    }
                };
            }
        };
        actEmail.setAdapter(adapterEmail);

        actEmail.setThreshold(2);


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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        {
                            getSearchDataEnquiry.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else
                        {
                            getSearchDataEnquiry.execute();
                        }
                        return super.performFiltering(constraint);
                    }

                };
            }
        };
        actEnquiryId.setAdapter(adapterEnquiry);
        actEnquiryId.setThreshold(2);


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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        {
                            getSearchDataContact.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else
                        {
                            getSearchDataContact.execute();
                        }

                        return super.performFiltering(constraint);
                    }

                };
            }
        };
        actContact.setAdapter(adapterContact);
        actContact.setThreshold(2);


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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        {
                            getSearchDataName.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        } else
                        {
                            getSearchDataName.execute();
                        }
                        return super.performFiltering(constraint);
                    }

                };
            }
        };
        actName.setAdapter(adapterName);
        actName.setThreshold(2);


        btnSearchCustomer = (Button) findViewById(R.id.btn_searchCustomer);
        btnSearchCustomer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new SearchCustomerAsync(actName.getText().toString(),
                        actEmail.getText().toString(),
                        actContact.getText().toString(),
                        actEnquiryId.getText().toString()).execute();
            }
        });

        pbSearchInProgress = (ProgressBar) findViewById(R.id.pb_searchInProgress);

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


    class GetSearchData extends AsyncTask<Void, Void, String>
    {

        final String url;

        String resp = "";

        final String term;

        final DropdownProductAdapter adapter;

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
                    adapter.notifyDataSetChanged();
                    if (!actContact.getText().toString().isEmpty())
                    {
                        actContact.showDropDown();
                        adapterContact.getFilter().filter(actContact.getText().toString());
                    }

                    if (!actEmail.getText().toString().isEmpty())
                    {
                        actEmail.showDropDown();
                        adapterEmail.getFilter().filter(actEmail.getText().toString());
                    }

                    if (!actEnquiryId.getText().toString().isEmpty())
                    {
                        actEnquiryId.showDropDown();
                        adapterEnquiry.getFilter().filter(actEnquiryId.getText().toString());
                    }

                    if (!actName.getText().toString().isEmpty())
                    {
                        actName.showDropDown();
                        adapterName.getFilter().filter(actName.getText().toString());
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }


    public class SearchCustomerAsync extends AsyncTask<Void, Void, Void>
    {

        final String url = PreferenceManager.getDefaultSharedPreferences(ActivitySearchCustomer.this)
                .getString("host", "") + AppUtils.URL_WEBSERVICE;

        String response = "";

        final String name, contact, email, enquiryId;

        public SearchCustomerAsync(final String name, final String email,
                                   final String contact, final String enquiryId)
        {
            this.email = email;
            this.contact = contact;
            this.enquiryId = enquiryId;
            this.name = name;
        }

        @Override
        protected void onPreExecute()
        {
            btnSearchCustomer.setVisibility(View.GONE);
            pbSearchInProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            final Map<String, String> postParam = new HashMap<>();
            postParam.put("method", "search_customer");
            postParam.put("enquiry_id", enquiryId);
            postParam.put("mobile_no", contact);
            postParam.put("name", name);
            postParam.put("email", email);

            new PostServiceHandler(TAG, 2, 2000).doPostRequest(url, postParam, new PostServiceHandler.ResponseCallback()
            {
                @Override
                public void response(int status, String response)
                {
                    if (status == HttpURLConnection.HTTP_OK)
                    {
                        SearchCustomerAsync.this.response = response;
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            try
            {
                if (!response.isEmpty())
                {
                    JSONObject obj = new JSONObject(response).getJSONObject("response");
                    try
                    {
                        obj.getJSONArray("result");
                        Intent i = new Intent(ActivitySearchCustomer.this, ActivitySearchResult.class);
                        i.putExtra("search_result", response);
                        startActivity(i);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        final String result = obj.getString("result");
                        try
                        {
                            final int customerId = Integer.valueOf(result);
                            Toast.makeText(ActivitySearchCustomer.this,
                                    customerId + "", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(ActivitySearchCustomer.this, ActivityCustomerDetails.class);
                            i.putExtra("customer_id", String.valueOf(customerId));
                            startActivity(i);
                        } catch (NumberFormatException n)
                        {
                            e.printStackTrace();
                            Toast.makeText(ActivitySearchCustomer.this,
                                    result, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            } finally
            {
                btnSearchCustomer.setVisibility(View.VISIBLE);
                pbSearchInProgress.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void finish()
    {
        if (getSearchDataContact != null)
        {
            getSearchDataContact.cancel(true);
            getSearchDataContact = null;
        }

        if (getSearchDataEmail != null)
        {
            getSearchDataEmail.cancel(true);
            getSearchDataEmail = null;
        }

        if (getSearchDataEnquiry != null)
        {
            getSearchDataEnquiry.cancel(true);
            getSearchDataEnquiry = null;
        }

        if (getSearchDataName != null)
        {
            getSearchDataName.cancel(true);
            getSearchDataName = null;
        }
        super.finish();
    }
}
