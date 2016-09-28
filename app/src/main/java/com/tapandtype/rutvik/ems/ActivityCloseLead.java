package com.tapandtype.rutvik.ems;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import extras.AppUtils;
import extras.PostServiceHandler;
import viewholders.SpinnerVH;

public class ActivityCloseLead extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    Spinner spinSmsOption, spinReasonsToDecline;

    RadioGroup rgStatus;

    EditText etPurchaseDate, etInstallationDate, etDescription;

    FrameLayout flPurchased, flNotPurchased;

    GetDeclineReasonsAsync getDeclineReasonsAsync;

    public static final String TAG = AppUtils.APP_TAG + ActivityCloseLead.class.getSimpleName();

    private String enquiryId;

    private SpinnerVH.MySpinnerBaseAdapter spinnerBaseAdapter;

    private SpinnerVH.MySpinnerBaseAdapter spinnerDeclineReasonAdapter;

    ArrayList<SpinnerVH.SpinnerData> spinnerDeclineReasonData = new ArrayList<>();

    private boolean isPositive = true;

    private String declineReasonId, smsOption;

    private DatePickerDialog dpd1;
    private DatePickerDialog dpd2;

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

        spinSmsOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Object obj = adapterView.getItemAtPosition(i);
                if (obj instanceof SpinnerVH.SpinnerData)
                {
                    smsOption = ((SpinnerVH.SpinnerData) obj).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

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
                    isPositive = true;
                } else if (checkedId == R.id.rb_notPurchased)
                {
                    flPurchased.setVisibility(View.GONE);
                    flNotPurchased.setVisibility(View.VISIBLE);
                    isPositive = false;
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

        etDescription = (EditText) findViewById(R.id.et_description);

        dpd1 = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
                    {
                        etPurchaseDate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));


        dpd1.setAccentColor(getResources().getColor(R.color.accent));

        dpd2 = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
                    {
                        etInstallationDate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        etPurchaseDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dpd1.show(getFragmentManager(), "DPD1");
            }
        });

        etInstallationDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dpd2.show(getFragmentManager(), "DPD2");
            }
        });

        findViewById(R.id.btn_closeLead).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new CloseLeadAsync().execute();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        Object obj = adapterView.getItemAtPosition(i);
        if (obj instanceof SpinnerVH.SpinnerData)
        {
            declineReasonId = ((SpinnerVH.SpinnerData) obj).getId();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

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

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if (!response.isEmpty())
            {

                try
                {
                    JSONArray arr = new JSONArray(response);

                    for (int i = 0; i < arr.length(); i++)
                    {
                        JSONObject obj = arr.getJSONObject(i);
                        spinnerDeclineReasonData
                                .add(new SpinnerVH.SpinnerData(obj.getString("decline_id"),
                                        obj.getString("decline_reason")));
                    }

                    spinnerDeclineReasonAdapter = new SpinnerVH.MySpinnerBaseAdapter(ActivityCloseLead.this);

                    spinnerDeclineReasonAdapter.setData(spinnerDeclineReasonData);
                    spinReasonsToDecline.setAdapter(spinnerDeclineReasonAdapter);
                    spinnerDeclineReasonAdapter.notifyDataSetChanged();

                    spinReasonsToDecline.setOnItemSelectedListener(ActivityCloseLead.this);

                } catch (JSONException e)
                {
                    e.printStackTrace();
                    //FirebaseCrash.report(e);
                }


            }
        }
    }


    class CloseLeadAsync extends AsyncTask<Void, Void, Void>
    {

        String response = "";

        final String sessionId;

        final String host;

        final String description;

        final String date1, date2;

        public CloseLeadAsync()
        {
            sessionId = ((App) getApplication()).getUser().getSession_id();
            host = ((App) getApplication()).getHost();
            description = etDescription.getText().toString();
            date1 = etPurchaseDate.getText().toString();
            date2 = etInstallationDate.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {

            final Map<String, String> postParam = new HashMap<>();
            postParam.put("method", "close_lead");
            postParam.put("session_id", sessionId);

            if (isPositive)
            {
                postParam.put("purchase_date", date1);
                postParam.put("end_date", date2);
            } else
            {
                postParam.put("decline_reason_id", declineReasonId);
                postParam.put("note", description);
            }
            postParam.put("is_bought", isPositive ? "1" : "2");
            postParam.put("enquiry_form_id", enquiryId);
            postParam.put("sms_status", smsOption);


            new PostServiceHandler(TAG, 3, 2000).doPostRequest(host + AppUtils.URL_WEBSERVICE,
                    postParam, new PostServiceHandler.ResponseCallback()
                    {
                        @Override
                        public void response(int status, String response)
                        {
                            if (status == HttpURLConnection.HTTP_OK)
                            {
                                CloseLeadAsync.this.response = response;
                            }
                        }
                    });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if (!response.isEmpty())
            {
                if (response.contains("success"))
                {
                    Toast.makeText(ActivityCloseLead.this, "Successfully closed", Toast.LENGTH_SHORT).show();
                    ActivityCloseLead.this.finish();
                }
            }
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
