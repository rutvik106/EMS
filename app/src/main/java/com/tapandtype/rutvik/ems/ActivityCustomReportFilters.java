package com.tapandtype.rutvik.ems;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import adapters.SimpleFormAdapter;
import api.API;
import apimodels.FiltersForCustomLeadReports;
import extras.AppUtils;
import extras.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCustomReportFilters extends AppCompatActivity
{

    private static final String TAG = AppUtils.APP_TAG + ActivityCustomReportFilters.class.getSimpleName();
    final Map<String, Map<String, String>> dataMap = new HashMap<>();
    final Map postData = new HashMap<>();
    RecyclerView rvCustomReportFilters;
    SimpleFormAdapter adapter;
    long count = -1;
    Toolbar mToolbar;
    int reportType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_report_filters);

        reportType = getIntent().getIntExtra(Constants.REPORT_TYPE, 0);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null)
        {
            mToolbar.setTitle("Report Filters");
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
            }
        }

        rvCustomReportFilters = (RecyclerView) findViewById(R.id.rv_customReportFilters);

        rvCustomReportFilters.setLayoutManager(new LinearLayoutManager(this));

        rvCustomReportFilters.setHasFixedSize(true);

        adapter = new SimpleFormAdapter(this);

        rvCustomReportFilters.setAdapter(adapter);

        findViewById(R.id.btn_generateCustomLeadReports).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                collectFormData();
            }
        });

        getFilters();

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


    private void getFilters()
    {

        final String sessionId = ((App) getApplication()).getUser().getSession_id();

        new API(this).getFilterForCustomEnquiryReport(sessionId, new Callback<FiltersForCustomLeadReports>()
        {
            @Override
            public void onResponse(Call<FiltersForCustomLeadReports> call, Response<FiltersForCustomLeadReports> response)
            {
                Log.i(TAG, "RESPONSE CODE: " + response.code());

                if (response.isSuccessful())
                {
                    final Map<String, String> productMap = new HashMap<>();
                    for (FiltersForCustomLeadReports
                            .ProductListBean product : response.body().getProductList())
                    {
                        productMap.put(product.getSubCatId(), product.getSubCatName());
                    }
                    dataMap.put("products", productMap);

                    final Map<String, String> userMap = new HashMap<>();
                    for (FiltersForCustomLeadReports
                            .UserListBean user : response.body().getUserList())
                    {
                        userMap.put(user.getAdminId(), user.getAdminName());
                    }
                    dataMap.put("users", userMap);

                    final Map<String, String> status = new LinkedHashMap<>();
                    status.put("0", "New");
                    status.put("3", "Ongoing");
                    status.put("1", "Successful");
                    status.put("2", "Unsuccessful");

                    dataMap.put("status", status);

                    findViewById(R.id.ll_loadingFilters).setVisibility(View.GONE);

                    setupFilters();
                }
            }

            @Override
            public void onFailure(Call<FiltersForCustomLeadReports> call, Throwable t)
            {
                Log.i(TAG, "RETROFIT CALL FAILED");

                System.out.println(t.getMessage());

            }
        });
    }

    private void setupFilters()
    {

        adapter.addDatePicker("From Date", "from", ++count, "Select From Date");

        adapter.addDatePicker("To Date", "to", ++count, "Select To Date");

        if (reportType == Constants.USER_SNAPSHOT_REPORT || reportType == Constants.CUSTOM_FOLLOWUP_REPORT)
        {
            return;
        }

        if (reportType == Constants.CUSTOM_LEAD_REPORT)
        {
            adapter.addCheckListSpinner("Select Enquiry Status", "status", dataMap.get("status"), ++count);
        }

        adapter.addCheckListSpinner("Select User", "user", dataMap.get("users"), ++count);

        adapter.addCheckListSpinner("Select Product", "product", dataMap.get("products"), ++count);


    }


    private void collectFormData()
    {

        for (long i = 0; i < adapter.getItemCount(); i++)
        {
            Log.i(TAG, "KEY: " + adapter.getComponentListMap().get(i).getRowItem().getName() +
                    "VALUE: " + adapter.getComponentListMap().get(i).getRowItem().getValue());

            postData.put(adapter.getComponentListMap().get(i).getRowItem().getName(),
                    adapter.getComponentListMap().get(i).getRowItem().getValue());
        }


        Intent i = new Intent(this, ActivityShowReport.class);

        i.putExtra(Constants.REPORT_TYPE, reportType);

        i.putExtra(Constants.REPORT_FROM_DATE,
                postData.get("from") != null ? postData.get("from").toString() : "");

        i.putExtra(Constants.REPORT_TO_DATE,
                postData.get("to") != null ? postData.get("to").toString() : "");

        i.putExtra(Constants.REPORT_USER_ID,
                postData.get("user") != null ? postData.get("user").toString() : "");

        i.putExtra(Constants.REPORT_LEAD_STATUS,
                postData.get("status") != null ? postData.get("status").toString() : "");

        i.putExtra(Constants.REPORT_PRODUCT,
                postData.get("product") != null ? postData.get("product").toString() : "");

        startActivity(i);

    }


}
