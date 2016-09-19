package com.tapandtype.rutvik.ems;

import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import extras.AppUtils;
import extras.PostServiceHandler;
import viewholders.SpinnerVH;

public class ActivityCloseLead extends AppCompatActivity
{

    Spinner spinSmsOption, spinReasonsToDecline;

    RadioGroup rgStatus;

    EditText etPurchaseDate, etInstallationDate, etDescription;

    FrameLayout flPurchased, flNotPurchased;

    GetDeclineReasonsAsync getDeclineReasonsAsync;

    public static final String TAG = AppUtils.APP_TAG + ActivityCloseLead.class.getSimpleName();

    private String enquiryId;

    private SpinnerVH.MySpinnerBaseAdapter spinnerBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_lead);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        enquiryId = getIntent().getStringExtra("enquiry_id");

        if (enquiryId == null)
        {
            Toast.makeText(this, "enquiry id is null", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("Close Lead");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        flPurchased = (FrameLayout) findViewById(R.id.fl_purchased);
        flNotPurchased = (FrameLayout) findViewById(R.id.fl_notPurchased);

        spinSmsOption = (Spinner) findViewById(R.id.spin_smsOption);
        spinReasonsToDecline = (Spinner) findViewById(R.id.spin_reasonsToDecline);

        spinnerBaseAdapter = new SpinnerVH.MySpinnerBaseAdapter(this);

        ArrayList<SpinnerVH.SpinnerData> spinnerData = new ArrayList<>();
        spinnerData.add(new SpinnerVH.SpinnerData("0", "No"));
        spinnerData.add(new SpinnerVH.SpinnerData("1", "Yes"));

        spinnerBaseAdapter.setData(spinnerData);
        spinSmsOption.setAdapter(spinnerBaseAdapter);
        spinnerBaseAdapter.setSelection(1);



        etDescription = (EditText) findViewById(R.id.et_description);
        etInstallationDate = (EditText) findViewById(R.id.et_installationDate);
        etPurchaseDate = (EditText) findViewById(R.id.et_purchaseDate);

        rgStatus = (RadioGroup) findViewById(R.id.rg_status);

        rgStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                if (checkedId == R.id.rb_purchased)
                {
                    flPurchased.setVisibility(View.VISIBLE);
                    flNotPurchased.setVisibility(View.GONE);
                } else if (checkedId == R.id.rb_notPurchased)
                {
                    flPurchased.setVisibility(View.GONE);
                    flNotPurchased.setVisibility(View.VISIBLE);
                }
            }
        });

        getDeclineReasonsAsync = new GetDeclineReasonsAsync();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            getDeclineReasonsAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else
        {
            getDeclineReasonsAsync.execute();
        }


    }


    class GetDeclineReasonsAsync extends AsyncTask<Void, Void, Void>
    {

        String response = "";

        final String sessionId;

        final String host;

        public GetDeclineReasonsAsync()
        {
            sessionId = ((App) getApplication()).getUser().getSession_id();
            host = ((App) getApplication()).getHost();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {

            final Map<String, String> postParam = new HashMap<>();
            postParam.put("method", "get_decline_reasons");
            postParam.put("session_id", sessionId);

            new PostServiceHandler(TAG, 3, 2000).doPostRequest(host + AppUtils.URL_WEBSERVICE,
                    postParam, new PostServiceHandler.ResponseCallback()
                    {
                        @Override
                        public void response(int status, String response)
                        {
                            if (status == HttpURLConnection.HTTP_OK)
                            {
                                GetDeclineReasonsAsync.this.response = response;
                            }
                        }
                    });

            return null;
        }
    }

    @Override
    public void onBackPressed()
    {
        close();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            close();
        }
        return super.onOptionsItemSelected(item);
    }

    public void close()
    {
        if (getDeclineReasonsAsync != null)
        {
            getDeclineReasonsAsync.cancel(true);
        }
        finish();
    }
}