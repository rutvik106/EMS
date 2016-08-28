package com.example.rutvik.ems;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapters.SearchResultAdapter;
import extras.AppUtils;
import models.SearchResultItem;

public class ActivitySearchResult extends AppCompatActivity
{
    private static final String TAG = AppUtils.APP_TAG + ActivitySearchCustomer.class.getSimpleName();

    RecyclerView rvSearchResults;

    SearchResultAdapter adapter;

    String response = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Result");

        rvSearchResults = (RecyclerView) findViewById(R.id.rv_searchResults);
        rvSearchResults.setHasFixedSize(true);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchResultAdapter(this);
        rvSearchResults.setAdapter(adapter);

        response = getIntent().getStringExtra("search_result");

        if (!response.isEmpty())
        {
            try
            {
                JSONArray arr = new JSONObject(response).getJSONObject("response").getJSONArray("result");
                for (int i = 0; i < arr.length(); i++)
                {
                    JSONObject customer = arr.getJSONObject(i).getJSONObject("customer");
                    final String id = customer.getString("customer_id");
                    final String name = customer.getString("customer_name");

                    JSONArray contact = arr.getJSONObject(i).getJSONArray("customer_contact");
                    String contactNo = "";
                    for (int j = 0; j < contact.length(); j++)
                    {
                        Log.i(TAG, "Contact no: " + contact.getJSONObject(j).getString("customer_contact_no"));
                        contactNo = contactNo + contact.getJSONObject(j).getString("customer_contact_no") + ", ";
                    }
                    Log.i(TAG, "Contact No: " + contactNo);
                    contactNo = contactNo.substring(0, contactNo.length() - 2);
                    adapter.addSearchResultItem(new SearchResultItem(id, name, contactNo));
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
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


}
