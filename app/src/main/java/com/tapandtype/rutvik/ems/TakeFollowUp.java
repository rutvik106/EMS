package com.tapandtype.rutvik.ems;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;

import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import extras.AppUtils;
import extras.PostServiceHandler;
import jsonobject.Response;

public class TakeFollowUp extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{

    Toolbar toolbar;

    TextView tvFollowUpCustomerName, tvFollowUpCustomerContact;

    LinearLayout llCustomerContact;

    Button btnSaveFollowUp;

    EditText etFollowUpDate, etFollowUpTime, etFollowUpDiscussion;

    Spinner spinFollowUpType, spinSendSms;

    App app;

    String sendSmsValue, followUpTypeValue;

    String customerName, customerContact, enquiryId;

    DatePickerDialog dpd;

    TimePickerDialog tpd;

    String followUpDate, followUpTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_follow_up);

        app = (App) getApplication();

        toolbar = (Toolbar) findViewById(R.id.toolbar);


        tvFollowUpCustomerName = (TextView) findViewById(R.id.tv_followUpCustomerName);
        tvFollowUpCustomerContact = (TextView) findViewById(R.id.tv_followUpCustomerContact);
        llCustomerContact = (LinearLayout) findViewById(R.id.ll_customerContact);

        customerName = getIntent().getStringExtra("follow_up_customer_name");
        customerContact = getIntent().getStringExtra("follow_up_customer_contact");
        enquiryId = getIntent().getStringExtra("enquiry_id");

        if (customerName != null && customerContact != null)
        {
            tvFollowUpCustomerName.setText(customerName);
            tvFollowUpCustomerContact.setText(customerContact);
        }

        llCustomerContact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String contactNo = tvFollowUpCustomerContact.getText().toString();
                if (contactNo != null && contactNo != "")
                {
                    Intent supportIntent = new Intent(Intent.ACTION_DIAL);
                    supportIntent.setData(Uri.parse("tel:" + contactNo));
                    startActivity(supportIntent);
                }
            }
        });

        etFollowUpDiscussion = (EditText) findViewById(R.id.et_followUpDiscussion);

        etFollowUpDate = (EditText) findViewById(R.id.et_followUpDate);

        followUpDate = AppUtils.dateToDMY(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        etFollowUpDate.setText(followUpDate);

        etFollowUpDate.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean hasFoucs)
            {
                if (hasFoucs)
                {
                    showDatePickerDialog();
                }
            }
        });

        etFollowUpDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showDatePickerDialog();
            }
        });

        etFollowUpTime = (EditText) findViewById(R.id.et_followUpTime);

        followUpTime = AppUtils.TimeToHMS(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                Calendar.getInstance().get(Calendar.SECOND));

        etFollowUpTime.setText(followUpTime);

        etFollowUpTime.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean hasFoucs)
            {
                if (hasFoucs)
                {
                    showTimePickerDialog();
                }
            }
        });

        etFollowUpTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showTimePickerDialog();
            }
        });


        spinFollowUpType = (Spinner) findViewById(R.id.spin_followUpType);
        spinFollowUpType.setAdapter(new ArrayAdapter<String>(this, R.layout.single_spinner_row, AppUtils.followUpTypeList));
        spinFollowUpType.setSelection(0);
        spinFollowUpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                followUpTypeValue = AppUtils.followUpTypeMap.get(AppUtils.followUpTypeList.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });


        spinSendSms = (Spinner) findViewById(R.id.spin_sendSMS);
        spinSendSms.setAdapter(new ArrayAdapter<String>(this, R.layout.single_spinner_row, AppUtils.sendSmsList));
        spinSendSms.setSelection(0);
        spinSendSms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                sendSmsValue = AppUtils.sendSmsOptionMap.get(AppUtils.sendSmsList.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        btnSaveFollowUp = (Button) findViewById(R.id.btn_saveFollowUp);

        btnSaveFollowUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new SaveNewFollowUp(TakeFollowUp.this,
                        app.getUser().getSession_id(),
                        etFollowUpDiscussion.getText().toString(),
                        followUpDate,
                        followUpTime,
                        sendSmsValue,
                        followUpTypeValue,
                        enquiryId).execute();
            }
        });


        if (toolbar != null)
        {
            toolbar.setTitle("Take Follow Up");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            upArrow.setColorFilter(getResources().getColor(R.color.mdtp_white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }


    }

    private void showDatePickerDialog()
    {
        dpd = DatePickerDialog.newInstance(TakeFollowUp.this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dpd.setMinDate(Calendar.getInstance());
        dpd.setTitle("NEXT FOLLOW UP DATE");
        dpd.show(TakeFollowUp.this.getFragmentManager(), "DATE_PICKER");
    }

    private void showTimePickerDialog()
    {
        tpd = TimePickerDialog.newInstance(TakeFollowUp.this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true);
        tpd.setTitle("NEXT FOLLOW UP TIME");
        tpd.show(TakeFollowUp.this.getFragmentManager(), "DATE_PICKER");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case android.R.id.home:
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        followUpDate = AppUtils.dateToDMY(year, monthOfYear, dayOfMonth);
        etFollowUpDate.setText(followUpDate);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second)
    {
        followUpTime = AppUtils.TimeToHMS(hourOfDay, minute, second);
        etFollowUpTime.setText(followUpTime);
    }


    static class SaveNewFollowUp extends AsyncTask<Void, Void, String>
    {

        private static final String TAG = AppUtils.APP_TAG + SaveNewFollowUp.class.getSimpleName();

        private final Map<String, String> postParams = new HashMap<>();

        private Context context;

        String response = "";

        public SaveNewFollowUp(Context context, String session_id, String followUpDiscussion, String next_follow_up_date,
                               String next_follow_up_time,
                               String sms_status,
                               String follow_up_type_id,
                               String enquiryId)
        {
            this.context = context;
            postParams.put("method", "add_new_follow_up");
            postParams.put("session_id", session_id);
            postParams.put("followUpDiscussion", followUpDiscussion);
            postParams.put("next_follow_up_date", next_follow_up_date);
            postParams.put("next_follow_up_time", next_follow_up_time);
            postParams.put("sms_status", sms_status);
            postParams.put("follow_up_type_id", follow_up_type_id);
            postParams.put("enquiry_id", enquiryId);
        }

        public String constructUrl()
        {
            String url = PreferenceManager.getDefaultSharedPreferences(context).getString("host", "");
            if (!url.isEmpty())
            {
                url = url + AppUtils.URL_WEBSERVICE;
            }
            return url;
        }


        @Override
        protected String doInBackground(Void... voids)
        {

            new PostServiceHandler(TAG, 2, 2000).doPostRequest(constructUrl(), postParams, new PostServiceHandler.ResponseCallback()
            {
                @Override
                public void response(int status, String resp)
                {
                    if (status == HttpURLConnection.HTTP_OK)
                    {
                        if (!resp.isEmpty())
                        {
                            response = resp;
                        }
                    }
                }
            });

            return response;
        }

        @Override
        protected void onPostExecute(String s)
        {
            if (!s.isEmpty())
            {
                try
                {
                    Response response = new Response(s);
                    if (response.isStatusOk())
                    {
                        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, ActivityView.class);
                        i.putExtra("enquiry_id", postParams.get("enquiry_id"));
                        context.startActivity(i);

                    } else
                    {
                        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e)
                {
                    Toast.makeText(context, "Parsing Error: " + s, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            } else
            {
                Toast.makeText(context, "Something went wrong, Please try again later", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
