package com.tapandtype.rutvik.ems;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import ComponentFactory.AppendableTextBox;
import ComponentFactory.Component;
import adapters.SimpleFormAdapter;
import extras.AppUtils;
import extras.Log;
import extras.PostServiceHandler;
import fragments.SimpleFormFragment;
import jsonobject.Response;

import static extras.CommonUtils.MANDATORY_FIELD;

public class ActivityAddNewInquiry extends AppCompatActivity
{

    public final static String TAG = AppUtils.APP_TAG + ActivityAddNewInquiry.class.getSimpleName();
    SimpleFormAdapter simpleFormAdapter;
    SimpleFormFragment simpleFormFragment;
    SimpleFormAdapter interestedProductDetailsAdapter;
    SimpleFormFragment interestedProductDetailsFragment;
    SimpleFormAdapter leadInquiryDetailsAdapter;
    SimpleFormFragment leadInquiryDetailsFragment;
    LinearLayout fragSimpleForm;
    FrameLayout flLoadingForm;
    Map<String, Map<String, String>> newEnquiryExtraDataMap = new HashMap<>();
    App app;
    Button addEnquiry;
    ProgressBar pbProcessing;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_inquiry);

        pbProcessing = new ProgressBar(this);
        pbProcessing.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        pbProcessing.setIndeterminate(true);

        app = (App) getApplication();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null)
        {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Add New Inquiry");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        fragSimpleForm = (LinearLayout) findViewById(R.id.frag_simpleForm);

        flLoadingForm = (FrameLayout) findViewById(R.id.fl_loadingAddNewEnquiryForm);

        if (savedInstanceState != null)
        {
            Log.i(TAG, "RESTORING SAVED INSTANCE");
            simpleFormFragment = (SimpleFormFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ADD_NEW_INQUIRY");
        }

        new GetEnquiryExtraData(this, newEnquiryExtraDataMap, new GetEnquiryExtraData.CompletionCallback()
        {
            @Override
            public void onSuccess()
            {
                flLoadingForm.setVisibility(View.GONE);

                populateAddNewInquiryForm();

                populateInterestedProductDetails();

                populateLeadInquiryDetails();
            }

            @Override
            public void onFailure(JSONException e, String response)
            {
                Toast.makeText(ActivityAddNewInquiry.this, "JSON Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(ActivityAddNewInquiry.this, "RESPONSE: " + response, Toast.LENGTH_SHORT).show();
            }
        }).execute();


    }

    public void setAddEnquiryButton()
    {

        addEnquiry = new Button(this);
        ViewGroup.LayoutParams lParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        addEnquiry.setLayoutParams(lParams);
        addEnquiry.setText("Add Enquiry");
        addEnquiry.setTextColor(Color.WHITE);
        addEnquiry.getBackground().setColorFilter(0xFF00695C, PorterDuff.Mode.MULTIPLY);

        addEnquiry.setOnClickListener(new OnAddEnquiry());

        fragSimpleForm.addView(addEnquiry, 3);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        try
        {
            getSupportFragmentManager().putFragment(outState, "ADD_NEW_INQUIRY", simpleFormFragment);
        } catch (NullPointerException e)
        {
            e.printStackTrace();
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

    private void populateAddNewInquiryForm()
    {
        int i = -1;

        simpleFormAdapter = new SimpleFormAdapter(this);

        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int monthOfYear = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);

        String month = String.valueOf(monthOfYear + 1);
        if (month.length() == 1)
        {
            month = "0" + month;
        }

        String day = String.valueOf(dayOfMonth);
        if (day.length() == 1)
        {
            day = "0" + day;
        }

        String date = day + "/" + month + "/" + year;


        simpleFormAdapter.addTextBox("Enquiry Date*", "enquiry_date", ++i, InputType.TYPE_CLASS_TEXT, false, date, true);

        simpleFormAdapter.addSpinner("Customer Prefix", "prefix_id", newEnquiryExtraDataMap.get("prefix"), ++i);

        simpleFormAdapter.addTextBox("Customer Name*", "customer_name", ++i, InputType.TYPE_CLASS_TEXT, true, "", true);

        simpleFormAdapter.addAppendableTextBox("Contact No*", "mobile_no", ++i,
                app.getHost() + AppUtils.URL_EXACT_CONTACT_NO,
                new AppendableTextBoxUrlListener(), InputType.TYPE_CLASS_NUMBER);

        simpleFormAdapter.addTextBox("Email Address", "email_id", ++i, InputType.TYPE_CLASS_TEXT, true, "", false);

        if (simpleFormFragment == null)
        {
            simpleFormFragment = new SimpleFormFragment();
        }

        simpleFormFragment.setData(simpleFormAdapter, "Customer Details");

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag_simpleForm, simpleFormFragment, "ADD_NEW_INQUIRY")
                .commitAllowingStateLoss();

        simpleFormAdapter.notifyDataSetChanged();

    }

    private void populateInterestedProductDetails()
    {
        int i = -1;

        interestedProductDetailsAdapter = new SimpleFormAdapter(this);

        //product_id[]
        //mrp[]
        //unit_id[]
        //quantity_id[]
        interestedProductDetailsAdapter.addInquiryProduct(++i, newEnquiryExtraDataMap.get("units"));

        if (interestedProductDetailsFragment == null)
        {
            interestedProductDetailsFragment = new SimpleFormFragment();
        }

        interestedProductDetailsFragment.setData(interestedProductDetailsAdapter, "Interested Product Details");

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag_simpleForm, interestedProductDetailsFragment, "INTERESTED_PRODUCT_DETAILS")
                .commit();

        interestedProductDetailsAdapter.notifyDataSetChanged();


    }

    public void populateLeadInquiryDetails()
    {
        int i = -1;

        leadInquiryDetailsAdapter = new SimpleFormAdapter(this);

        leadInquiryDetailsAdapter.addCheckListSpinner("Add To Enquiry Group", "enquiry_group_id", newEnquiryExtraDataMap.get("enquiry_group"), ++i);

        leadInquiryDetailsAdapter.addTextBox("Customer Budget", "budget", ++i, InputType.TYPE_CLASS_TEXT, true, "", false);

        leadInquiryDetailsAdapter.addSpinner("Enquiry Type", "customer_type_id", newEnquiryExtraDataMap.get("enquiry_type"), ++i);

        leadInquiryDetailsAdapter.addTextBox("Discussion", "discussion", ++i, InputType.TYPE_CLASS_TEXT, true, "", false);

        leadInquiryDetailsAdapter.addDatePicker("Follow Up Date*", "reminder_date", ++i, "Pick Follow Up Date");

        final Map<String, String> smsOptions = new LinkedHashMap<>();
        smsOptions.put("1", "Yes");
        smsOptions.put("0", "No");
        leadInquiryDetailsAdapter.addSpinner("Send SMS", "sms_status", smsOptions, ++i);

        if (leadInquiryDetailsFragment == null)
        {
            leadInquiryDetailsFragment = new SimpleFormFragment();
        }

        leadInquiryDetailsFragment.setData(leadInquiryDetailsAdapter, "Lead Inquiry Details");

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag_simpleForm, leadInquiryDetailsFragment, "LEAD_ENQUIRY_DETAILS")
                .commit();

        leadInquiryDetailsAdapter.notifyDataSetChanged();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                setAddEnquiryButton();
            }
        }, 10);


    }

    private boolean isMandatoryFieldsEmpty(final Map postParams)
    {
        Set<Map.Entry<String, String>> entrySet = postParams.entrySet();

        Iterator<Map.Entry<String, String>> entryIterator = entrySet.iterator();

        String mandatoryFields = "";

        while (entryIterator.hasNext())
        {
            Map.Entry<String, String> entry = entryIterator.next();

            if (entry.getValue().equals(MANDATORY_FIELD))
            {
                mandatoryFields = mandatoryFields + entry.getKey() + ", ";
            }

        }

        if (!mandatoryFields.isEmpty())
        {
            fragSimpleForm.removeViewAt(3);
            fragSimpleForm.addView(addEnquiry, 3);
            mandatoryFields = mandatoryFields.substring(0, mandatoryFields.length() - 2);
            Toast.makeText(ActivityAddNewInquiry.this,
                    mandatoryFields + " cannot be empty", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    private static class GetEnquiryExtraData extends AsyncTask<Void, Void, String>
    {
        final Context context;
        final CompletionCallback completionCallback;
        final Map<String, Map<String, String>> newEnquiryExtraDataMap;
        private final String TAG = AppUtils.APP_TAG + GetEnquiryExtraData.class.getSimpleName();
        private final String host;

        private final String sessionId;

        Map<String, String> postParam = new HashMap<>();

        String resp = "";

        public GetEnquiryExtraData(Context context, Map<String, Map<String, String>> newEnquiryExtraDataMap, CompletionCallback completionCallback)
        {
            this.context = context;
            this.completionCallback = completionCallback;
            this.newEnquiryExtraDataMap = newEnquiryExtraDataMap;
            this.host = PreferenceManager.getDefaultSharedPreferences(context).getString("host", "");
            this.sessionId = PreferenceManager.getDefaultSharedPreferences(context).getString("session_id", "");

        }

        @Override
        protected String doInBackground(Void... voids)
        {

            postParam.put("method", "get_add_new_enquiry_data");

            postParam.put("session_id", sessionId);

            new PostServiceHandler(TAG, 2, 2000).doPostRequest(host + AppUtils.URL_WEBSERVICE, postParam, new PostServiceHandler.ResponseCallback()
            {
                @Override
                public void response(int status, String response)
                {
                    resp = response;
                }
            });

            return resp;
        }

        @Override
        protected void onPostExecute(String s)
        {
            try
            {
                JSONObject obj = new JSONObject(s).getJSONObject("response");

                JSONArray units = obj.getJSONArray("units");
                Map<String, String> unitsMap = new HashMap<>();
                for (int i = 0; i < units.length(); i++)
                {
                    JSONObject o = units.getJSONObject(i);
                    unitsMap.put(o.getString("unit_id"), o.getString("unit_name"));
                }

                newEnquiryExtraDataMap.put("units", unitsMap);

                JSONArray enquiryType = obj.getJSONArray("enquiry_type");
                Map<String, String> enquiryTypeMap = new HashMap<>();
                enquiryTypeMap.put("-1", "Please select");
                for (int i = 0; i < enquiryType.length(); i++)
                {
                    JSONObject o = enquiryType.getJSONObject(i);
                    enquiryTypeMap.put(o.getString("customer_type_id"), o.getString("customer_type"));
                }

                newEnquiryExtraDataMap.put("enquiry_type", enquiryTypeMap);

                JSONArray enquiryGroup = obj.getJSONArray("enquiry_group");
                Map<String, String> enquiryGroupMap = new HashMap<>();
                for (int i = 0; i < enquiryGroup.length(); i++)
                {
                    JSONObject o = enquiryGroup.getJSONObject(i);
                    enquiryGroupMap.put(o.getString("enquiry_group_id"), o.getString("enquiry_group_name"));
                }

                newEnquiryExtraDataMap.put("enquiry_group", enquiryGroupMap);

                JSONArray prefix = obj.getJSONArray("prefix");
                Map<String, String> prefixMap = new HashMap<>();
                for (int i = 0; i < prefix.length(); i++)
                {
                    JSONObject o = prefix.getJSONObject(i);
                    prefixMap.put(o.getString("prefix_id"), o.getString("prefix"));
                }

                newEnquiryExtraDataMap.put("prefix", prefixMap);

                completionCallback.onSuccess();

            } catch (JSONException e)
            {
                e.printStackTrace();
                completionCallback.onFailure(e, resp);
            }
        }

        interface CompletionCallback
        {
            void onSuccess();

            void onFailure(JSONException e, String response);
        }
    }

    class OnAddEnquiry implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            fragSimpleForm.removeViewAt(3);
            fragSimpleForm.addView(pbProcessing, 3);

            final Map postParams = new HashMap();

            postParams.put("method", "add_new_enquiry");

            postParams.put("session_id", app.getUser().getSession_id());

            for (int i = 0; i < simpleFormAdapter.getItemCount(); i++)
            {
                Log.i(TAG, "DATA: " + simpleFormAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getValue());

                postParams.put(simpleFormAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getName(),
                        simpleFormAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getValue());

            }

            for (int i = 0; i < interestedProductDetailsAdapter.getItemCount(); i++)
            {
                Log.i(TAG, "DATA: " + interestedProductDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getValue());

                if (interestedProductDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getComponentType() == Component.INQUIRY_PRODUCT_DETAILS)
                {

                    postParams.putAll((Map) interestedProductDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getValue());

                } else
                {

                    postParams.put(interestedProductDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getName(),
                            interestedProductDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getValue());
                }

            }

            for (int i = 0; i < leadInquiryDetailsAdapter.getItemCount(); i++)
            {
                Log.i(TAG, "DATA: " + leadInquiryDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getValue());


                postParams.put(leadInquiryDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getName(),
                        leadInquiryDetailsAdapter.getComponentListMap().get(Long.valueOf(i)).getRowItem().getValue());

            }


            if (isMandatoryFieldsEmpty(postParams))
            {
                return;
            }


            Log.i(TAG, "POST PARAM: " + postParams.toString());


            new AsyncTask<Void, Void, Void>()

            {

                String response = "";

                Response jsonResponse;

                @Override
                protected Void doInBackground(Void... voids)
                {

                    new PostServiceHandler(TAG, 2, 2000)
                            .doPostRequest(app.getHost() + AppUtils.URL_WEBSERVICE,
                                    postParams,
                                    new PostServiceHandler.ResponseCallback()
                                    {
                                        @Override
                                        public void response(int status, String r)
                                        {
                                            response = r;
                                        }
                                    });

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid)
                {
                    try
                    {
                        jsonResponse = new Response(response);
                        if (jsonResponse.isStatusOk())
                        {
                            Toast.makeText(ActivityAddNewInquiry.this,
                                    "Inquiry added successfully",
                                    Toast.LENGTH_SHORT)
                                    .show();

                            Intent i = new Intent(ActivityAddNewInquiry.this, ActivityView.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.putExtra("enquiry_id", jsonResponse.getMessage());
                            startActivity(i);

                        } else
                        {
                            fragSimpleForm.removeViewAt(3);
                            fragSimpleForm.addView(addEnquiry, 3);
                            Toast.makeText(ActivityAddNewInquiry.this,
                                    jsonResponse.getMessage(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } catch (JSONException e)
                    {
                        fragSimpleForm.removeViewAt(3);
                        fragSimpleForm.addView(addEnquiry, 3);
                        Log.i(TAG, e.getMessage());
                        Toast.makeText(ActivityAddNewInquiry.this,
                                "Something went wrong, Please try again later",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }

                    .

                            execute();

        }
    }

    class AppendableTextBoxUrlListener implements AppendableTextBox.OnUrlTriggered
    {
        @Override
        public void urlTriggered(EditText etContact, TextView tvDuplicateErrorMsg, String response)
        {
            etContact.setTextColor(Color.RED);
            final String msg = "Contact already exist for id " + response;
            tvDuplicateErrorMsg.setText(msg);
        }
    }

}
