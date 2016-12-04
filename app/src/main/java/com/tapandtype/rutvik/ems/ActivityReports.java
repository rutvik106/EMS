package com.tapandtype.rutvik.ems;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class ActivityReports extends AppCompatActivity
{

    Toolbar mToolbar;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        i = new Intent(ActivityReports.this, ActivityCustomReportFilters.class);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null)
        {
            mToolbar.setTitle("Reports");
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null)
            {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        findViewById(R.id.cv_enquiryReport).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                i.putExtra(Constants.REPORT_TYPE, Constants.CUSTOM_LEAD_REPORT);
                startActivity(i);
            }
        });

        findViewById(R.id.cv_efficiencyReport).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                i.putExtra(Constants.REPORT_TYPE, Constants.EFFICIENCY_REPORT);
                startActivity(i);
            }
        });

        findViewById(R.id.cv_userSnapshotReport).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                i.putExtra(Constants.REPORT_TYPE, Constants.USER_SNAPSHOT_REPORT);
                startActivity(i);
            }
        });

        findViewById(R.id.cv_followUpReport).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                i.putExtra(Constants.REPORT_TYPE, Constants.CUSTOM_FOLLOWUP_REPORT);
                startActivity(i);
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
