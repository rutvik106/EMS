package com.tapandtype.rutvik.ems;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.DropdownProductAdapter;
import api.API;
import apimodels.CustomerSearchResult;
import extras.AppUtils;
import extras.CommonUtils;
import extras.PostServiceHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySearchCustomer extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private static final String TAG = AppUtils.APP_TAG + ActivitySearchCustomer.class.getSimpleName();
    private final Handler mHandler = new Handler();
    Call<List<CustomerSearchResult>> call;
    private AutoCompleteTextView actEnquiryId, actContact, actName, actEmail;
    private DropdownProductAdapter adapterName, adapterContact, adapterEmail, adapterEnquiry;
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

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        final String host = PreferenceManager.getDefaultSharedPreferences(ActivitySearchCustomer.this).getString("host", "");

        final String urlName = host + AppUtils.URL_GET_CUSTOMER_NAME;
        final String urlContact = host + AppUtils.URL_GET_CUSTOMER_CONTACT;
        final String urlEmail = host + AppUtils.URL_GET_CUSTOMER_EMAIL;
        final String urlEnquiry = host + AppUtils.URL_GET_ENQUIRY_ID;

        actEmail = (AutoCompleteTextView) findViewById(R.id.act_customerEmail);
        adapterEmail = new DropdownProductAdapter(ActivitySearchCustomer.this);
        actEmail.setAdapter(adapterEmail);
        actEmail.setThreshold(2);
        actEmail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (charSequence.length() > 2)
                {
                    new GetSearchDataUsingRetro(charSequence.toString(), adapterEmail).doSearch("email.php");
                } else
                {
                    adapterEmail.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });


        actEnquiryId = (AutoCompleteTextView) findViewById(R.id.act_enquiryId);
        adapterEnquiry = new DropdownProductAdapter(ActivitySearchCustomer.this);
        actEnquiryId.setAdapter(adapterEnquiry);
        actEnquiryId.setThreshold(2);
        actEnquiryId.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (charSequence.length() > 2)
                {
                    new GetSearchDataUsingRetro(charSequence.toString(), adapterEnquiry).doSearch("enquiry_id.php");
                } else
                {
                    adapterEnquiry.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });


        actContact = (AutoCompleteTextView) findViewById(R.id.act_customerContact);
        adapterContact = new DropdownProductAdapter(ActivitySearchCustomer.this);
        actContact.setAdapter(adapterContact);
        actContact.setThreshold(2);
        actContact.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (charSequence.length() > 2)
                {
                    new GetSearchDataUsingRetro(charSequence.toString(), adapterContact).doSearch("mobile_no.php");
                } else
                {
                    adapterContact.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });


        actName = (AutoCompleteTextView) findViewById(R.id.act_customerName);
        adapterName = new DropdownProductAdapter(ActivitySearchCustomer.this);
        actName.setAdapter(adapterName);
        actName.setThreshold(2);
        actName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (charSequence.length() > 2)
                {
                    new GetSearchDataUsingRetro(charSequence.toString(), adapterName).doSearch("customer_name.php");
                } else
                {
                    adapterName.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

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

        actContact.setOnItemClickListener(this);
        actEmail.setOnItemClickListener(this);
        actEnquiryId.setOnItemClickListener(this);
        actName.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        CommonUtils.hideSoftKeyboard(this);
    }

    @Override
    public void finish()
    {
        if (call != null)
        {
            call.cancel();
        }
        /*if (getSearchDataContact != null)
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
        }*/
        super.finish();
    }

    /**
     * class GetSearchData extends AsyncTask<Void, Void, String>
     * {
     * <p>
     * final String url;
     * final String term;
     * final DropdownProductAdapter adapter;
     * String resp = "";
     * <p>
     * public GetSearchData(final String term, final String url, final DropdownProductAdapter adapter)
     * {
     * this.url = url;
     * this.term = term;
     * this.adapter = adapter;
     * }
     *
     * @Override protected String doInBackground(Void... voids)
     * {
     * <p>
     * final Map<String, String> postParam = new HashMap<>();
     * postParam.put("term", term);
     * new PostServiceHandler(TAG, 2, 2000).doPostRequest(url, postParam, new PostServiceHandler.ResponseCallback()
     * {
     * @Override public void response(int status, String response)
     * {
     * if (status == HttpURLConnection.HTTP_OK)
     * {
     * GetSearchData.this.resp = response;
     * }
     * }
     * });
     * <p>
     * return resp;
     * }
     * @Override protected void onPostExecute(String s)
     * {
     * if (!resp.equals(""))
     * {
     * Log.i(TAG, "RESPONSE: " + resp);
     * try
     * {
     * //adapterName.clear();
     * JSONArray arr = new JSONArray(resp);
     * for (int i = 0; i < arr.length(); i++)
     * {
     * JSONObject obj = arr.getJSONObject(i);
     * adapter.addDropdownListProduct(new Label(obj.getString("label"), i));
     * }
     * mHandler.post(new Runnable()
     * {
     * @Override public void run()
     * {
     * adapter.notifyDataSetChanged();
     * }
     * });
     * if (!actContact.getText().toString().isEmpty())
     * {
     * actContact.showDropDown();
     * adapterContact.getFilter().filter(actContact.getText().toString());
     * }
     * <p>
     * if (!actEmail.getText().toString().isEmpty())
     * {
     * actEmail.showDropDown();
     * adapterEmail.getFilter().filter(actEmail.getText().toString());
     * }
     * <p>
     * if (!actEnquiryId.getText().toString().isEmpty())
     * {
     * actEnquiryId.showDropDown();
     * adapterEnquiry.getFilter().filter(actEnquiryId.getText().toString());
     * }
     * <p>
     * if (!actName.getText().toString().isEmpty())
     * {
     * actName.showDropDown();
     * adapterName.getFilter().filter(actName.getText().toString());
     * }
     * } catch (JSONException e)
     * {
     * e.printStackTrace();
     * }
     * }
     * }
     */

    class GetSearchDataUsingRetro
    {

        final String term;
        final DropdownProductAdapter adapter;
        String resp = "";

        public GetSearchDataUsingRetro(final String term,
                                       final DropdownProductAdapter adapter)
        {
            this.term = term;
            this.adapter = adapter;
        }

        public void doSearch(final String file)
        {
            if (call != null)
            {
                call.cancel();
            }
            call = new API(ActivitySearchCustomer.this)
                    .searchCustomer(file, term, new Callback<List<CustomerSearchResult>>()
                    {
                        @Override
                        public void onResponse(Call<List<CustomerSearchResult>> call, Response<List<CustomerSearchResult>> response)
                        {
                            if (response.isSuccessful() && response.body() != null)
                            {
                                for (int i = 0; i < response.body().size(); i++)
                                {
                                    adapter.addDropdownListProduct(new Label(response.body().get(i).getLabel(), i));
                                }
                                mHandler.post(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
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
                            }
                        }

                        @Override
                        public void onFailure(Call<List<CustomerSearchResult>> call, Throwable t)
                        {

                        }
                    });


        }
    }

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

    public class SearchCustomerAsync extends AsyncTask<Void, Void, Void>
    {

        final String url = PreferenceManager.getDefaultSharedPreferences(ActivitySearchCustomer.this)
                .getString("host", "") + AppUtils.URL_WEBSERVICE;
        final String name, contact, email, enquiryId;
        String response = "";

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
}
