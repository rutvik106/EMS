package com.tapandtype.rutvik.ems;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import adapters.ReportViewAdapter;
import api.API;
import apimodels.CustomLeadReports;
import extras.AppUtils;
import extras.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityShowReport extends AppCompatActivity
{

    private static final String TAG = AppUtils.APP_TAG + ActivityShowReport.class.getSimpleName();

    Toolbar mToolbar;

    RecyclerView rvReportView;

    ReportViewAdapter adapter;

    String fromDate, toDate, userId, product, status;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null)
        {
            mToolbar.setTitle("Enquiry Report");
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        rvReportView = (RecyclerView) findViewById(R.id.rv_reportView);

        rvReportView.setLayoutManager(new LinearLayoutManager(this));

        rvReportView.setHasFixedSize(true);

        adapter = new ReportViewAdapter(this);

        rvReportView.setAdapter(adapter);

        fromDate = getIntent().getStringExtra(Constants.REPORT_FROM_DATE);
        product = getIntent().getStringExtra(Constants.REPORT_PRODUCT);
        status = getIntent().getStringExtra(Constants.REPORT_LEAD_STATUS);
        toDate = getIntent().getStringExtra(Constants.REPORT_TO_DATE);

        generateReport();

    }

    private void generateReport()
    {

        final String sessionId = ((App) getApplication()).getUser().getSession_id();

        API.getInstance().getCustomLeadReport(sessionId, fromDate, toDate, userId, status, product, new Callback<CustomLeadReports>()
        {
            @Override
            public void onResponse(Call<CustomLeadReports> call, Response<CustomLeadReports> response)
            {
                Log.i(TAG, "RESPONSE CODE: " + response.code());

                if (response.isSuccessful())
                {
                    for (CustomLeadReports.CustomLeadReportsBean reportsBean : response.body().getCustomLeadReports())
                    {
                        adapter.addReportListItem(reportsBean);
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomLeadReports> call, Throwable t)
            {
                System.out.println(t.getMessage());
            }
        });

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
