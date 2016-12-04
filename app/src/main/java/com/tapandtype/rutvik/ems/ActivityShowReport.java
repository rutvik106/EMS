package com.tapandtype.rutvik.ems;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import adapters.CustomLeadReportViewAdapter;
import adapters.UserSnapshotReportViewAdapter;
import api.API;
import apimodels.CustomLeadReports;
import apimodels.UserSnapshotReport;
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

    RecyclerView.Adapter adapter;

    String fromDate, toDate, userId, product, status;

    int reportType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report);

        reportType = getIntent().getIntExtra(Constants.REPORT_TYPE, 0);

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

        fromDate = getIntent().getStringExtra(Constants.REPORT_FROM_DATE);
        product = getIntent().getStringExtra(Constants.REPORT_PRODUCT);
        status = getIntent().getStringExtra(Constants.REPORT_LEAD_STATUS);
        toDate = getIntent().getStringExtra(Constants.REPORT_TO_DATE);

        if (reportType == Constants.CUSTOM_LEAD_REPORT)
        {
            setActionBarTitle("Lead Report");
            adapter = new CustomLeadReportViewAdapter(this);
            rvReportView.setAdapter(adapter);
            generateCustomLeadReport();
        } else if (reportType == Constants.USER_SNAPSHOT_REPORT)
        {
            setActionBarTitle("User Snapshot Report");
            adapter = new UserSnapshotReportViewAdapter(this);
            rvReportView.setAdapter(adapter);
            generateUserSnapshotReport();
        } else if (reportType == Constants.EFFICIENCY_REPORT)
        {
            generateEfficiencyReport();
        } else if (reportType == Constants.CUSTOM_FOLLOWUP_REPORT)
        {
            generateCustomFollowupReport();
        }


    }

    private void generateCustomFollowupReport()
    {

    }

    private void generateEfficiencyReport()
    {

    }

    private void generateUserSnapshotReport()
    {
        final String sessionId = ((App) getApplication()).getUser().getSession_id();

        API.getInstance().getUserSnapshotReport(sessionId, fromDate, toDate, new Callback<List<UserSnapshotReport>>()
        {
            @Override
            public void onResponse(Call<List<UserSnapshotReport>> call, Response<List<UserSnapshotReport>> response)
            {
                Log.i(TAG, "RESPONSE CODE: " + response.code());

                if (response.isSuccessful())
                {
                    for (UserSnapshotReport report : response.body())
                    {
                        ((UserSnapshotReportViewAdapter) adapter).addUserSnapshot(report);
                    }
                    findViewById(R.id.ll_loadingReport).setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<UserSnapshotReport>> call, Throwable t)
            {
                System.out.println(t.getMessage());
            }
        });
    }

    private void generateCustomLeadReport()
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
                        ((CustomLeadReportViewAdapter) adapter).addReportListItem(reportsBean);
                    }
                    findViewById(R.id.ll_loadingReport).setVisibility(View.GONE);
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

    private void setActionBarTitle(final String title)
    {
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(title);
        }
    }

}
