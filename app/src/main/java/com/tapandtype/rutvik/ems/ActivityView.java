package com.tapandtype.rutvik.ems;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import adapters.SimpleFormAdapter;
import extras.AppUtils;
import extras.CommonUtils;
import extras.Log;
import extras.PostServiceHandler;
import fragments.SimpleFormFragment;
import models.FollowUpSeparator;

public class ActivityView extends AppCompatActivity
{

    private static final String TAG = AppUtils.APP_TAG + ActivityView.class.getSimpleName();

    Toolbar toolbar;

    SimpleFormAdapter adapterEnquiryStatus;
    SimpleFormFragment fragmentEnquiryStatus;

    SimpleFormAdapter adapterCustomerDetail;
    SimpleFormFragment fragmentCustomerDetail;

    SimpleFormAdapter adapterFollowUpDetail;
    SimpleFormFragment fragmentFollowUpDetail;

    SimpleFormAdapter adapterProductDetails;
    SimpleFormFragment fragmentProductDetails;

    SimpleFormAdapter adapterEnquiryDetails;
    SimpleFormFragment fragmentEnquiryDetails;

    SimpleFormAdapter adapterUnsuccessfullyClosedLeadDeails;
    SimpleFormFragment fragmentUnsuccessfullyClosedLeadDeails;

    LinearLayout fragSimpleForm;

    FrameLayout flLoadingView;

    App app;

    Map responseJson = new HashMap<>();

    GetViewDetailAsync getViewDetailAsync;

    String enquiryId, customerName, customerContact;

    String is_bought;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        app = (App) getApplication();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null)
        {
            toolbar.setTitle("Enquiry View");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }


        if (savedInstanceState != null)
        {

            Log.i(TAG, "RESTORNIG SAVED INSTANCE");

            fragmentCustomerDetail = (SimpleFormFragment) getSupportFragmentManager().getFragment(savedInstanceState, "CUSTOMER_DETAILS");

            fragmentEnquiryDetails = (SimpleFormFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ENQUIRY_DETAILS");

            fragmentEnquiryStatus = (SimpleFormFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ENQUIRY_STATUS");

            fragmentFollowUpDetail = (SimpleFormFragment) getSupportFragmentManager().getFragment(savedInstanceState, "FOLLOW_UP_DETAILS");

            fragmentProductDetails = (SimpleFormFragment) getSupportFragmentManager().getFragment(savedInstanceState, "PRODUCT_DETAILS");

            fragmentUnsuccessfullyClosedLeadDeails = (SimpleFormFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, "UNSUCCESSFULLY_CLOSED_LEAD_DETAILS");

        }


        fragSimpleForm = (LinearLayout) findViewById(R.id.frag_simpleForm);

        flLoadingView = (FrameLayout) findViewById(R.id.fl_loadingView);

        enquiryId = getIntent().getStringExtra("enquiry_id");
        customerName = getIntent().getStringExtra("follow_up_customer_name");
        customerContact = getIntent().getStringExtra("follow_up_customer_contact");

        if (enquiryId != null)
        {
            new GetViewDetailAsync(enquiryId, this).execute();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        try
        {

            getSupportFragmentManager().putFragment(outState, "CUSTOMER_DETAILS", fragmentCustomerDetail);

            getSupportFragmentManager().putFragment(outState, "ENQUIRY_DETAILS", fragmentEnquiryDetails);

            getSupportFragmentManager().putFragment(outState, "ENQUIRY_STATUS", fragmentEnquiryStatus);

            getSupportFragmentManager().putFragment(outState, "FOLLOW_UP_DETAILS", fragmentFollowUpDetail);

            getSupportFragmentManager().putFragment(outState, "PRODUCT_DETAILS", fragmentProductDetails);

            getSupportFragmentManager()
                    .putFragment(outState, "UNSUCCESSFULLY_CLOSED_LEAD_DETAILS", fragmentUnsuccessfullyClosedLeadDeails);

        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if (getViewDetailAsync != null)
        {
            getViewDetailAsync.cancel(true);
            getViewDetailAsync = null;
        }
    }

    private void parseResponse(String response)
    {
        try
        {
            JSONObject obj = new JSONObject(response);
            responseJson.put("enquiry", obj.getJSONObject("enquiry"));
            responseJson.put("customer", obj.getJSONObject("customer"));
            responseJson.put("contact", obj.getJSONArray("contact"));
            responseJson.put("follow_up_details", obj.getJSONArray("follow_up_details"));
            responseJson.put("product_details", obj.getJSONArray("product_details"));
            responseJson.put("enquiry_details", obj.getJSONObject("enquiry_details"));
            responseJson.put("unsuccessfully_closed_lead_details",
                    obj.getJSONArray("unsuccessfully_closed_lead_details"));

            populateView();

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    private void populateView()
    {

        addButtonListeners();

        flLoadingView.setVisibility(View.GONE);
        populateEnquiryStatus();
        populateCustomerDetail();
        populateProductDetails();
        populateEnquiryDetails();
        populateFollowUpDetail();
        populateUnsuccessfullyClosedLeadDetails();
    }

    private void populateUnsuccessfullyClosedLeadDetails()
    {
        int i = -1;
        adapterUnsuccessfullyClosedLeadDeails = new SimpleFormAdapter(this);

        try
        {

            if (is_bought.equals("2"))
            {
                JSONArray arr = (JSONArray) responseJson.get("unsuccessfully_closed_lead_details");
                if (arr.length() == 0)
                {
                    return;
                }
                //UNSUCCESSFULLY CLOSED LEAD
                for (int j = 0; j < arr.length(); j++)
                {
                /*adapterUnsuccessfullyClosedLeadDeails
                        .addFollowUpSeparator(++i,
                                new FollowUpSeparator("", "", "", "Follow Up " + (j + 1)));*/

                    //STATUS
                    final String is_bought = arr.getJSONObject(j).getString("is_bought");

                    adapterUnsuccessfullyClosedLeadDeails.addSimpleTextView(++i, "Status: ",
                            getStatusByBoughtId(is_bought), null);

                    //ENQUIRY CLOSING DATE

                    final String handled_by = arr.getJSONObject(j).getString("enquiry_close_date");
                    adapterUnsuccessfullyClosedLeadDeails.addSimpleTextView(++i, "Enquiry Closing Date: ",
                            handled_by, null);

                    //ENQUIRY CLOSED BY

                    final String closed_by = arr.getJSONObject(j).getString("admin_name");
                    adapterUnsuccessfullyClosedLeadDeails.addSimpleTextView(++i, "Closed By: ", closed_by, null);


                    //REASON TO DECLINE

                    final String declineReason = arr.getJSONObject(j).getString("decline_reason");
                    adapterUnsuccessfullyClosedLeadDeails.addSimpleTextView(++i, "Decline Reason: ", declineReason, null);

                    //DESCRIPTION
                    final String discussion = arr.getJSONObject(j).getString("discussion");
                    adapterUnsuccessfullyClosedLeadDeails.addSimpleTextView(++i, "Discussion: ", discussion, null);
                }

            } else if (is_bought.equals("1"))
            {
                //Toast.makeText(this, "I AM HERE", Toast.LENGTH_SHORT).show();
                //SUCCESSFULLY CLODED LEAD

                JSONObject obj = (JSONObject) responseJson.get("enquiry");

                //STATUS
                final String is_bought = obj.getString("is_bought");

                adapterUnsuccessfullyClosedLeadDeails.addSimpleTextView(++i, "Status: ",
                        getStatusByBoughtId(is_bought), null);

                //ENQUIRY CLOSING DATE

                final String handled_by = obj.getString("enquiry_close_date");
                adapterUnsuccessfullyClosedLeadDeails.addSimpleTextView(++i, "Enquiry Closing Date: ",
                        handled_by, null);

                //ENQUIRY CLOSED BY

                final String closed_by = obj.getString("admin_name");
                adapterUnsuccessfullyClosedLeadDeails.addSimpleTextView(++i, "Closed By: ", closed_by, null);


                //REGISTRATION DATE
                final String purchaseDate = obj.getString("purchase_date");
                adapterUnsuccessfullyClosedLeadDeails.addSimpleTextView(++i, "Registration Date: ",
                        purchaseDate.contains("1970") ? "N/A" : purchaseDate, null);


                //ENDING DATE
                final String endingDate = obj.getString("tour_ending_date");
                adapterUnsuccessfullyClosedLeadDeails.addSimpleTextView(++i, "Ending Date: ",
                        endingDate.contains("1970") ? "N/A" : endingDate, null);


            } else
            {
                return;
            }


        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        if (fragmentUnsuccessfullyClosedLeadDeails == null)
        {
            fragmentUnsuccessfullyClosedLeadDeails = new SimpleFormFragment();
        }
        fragmentUnsuccessfullyClosedLeadDeails.setData(adapterUnsuccessfullyClosedLeadDeails,
                "Enquiry Closing Details");
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag_simpleForm, fragmentUnsuccessfullyClosedLeadDeails, "CLOSED_LEAD_DETAILS")
                .commit();
        adapterUnsuccessfullyClosedLeadDeails.notifyDataSetChanged();
    }

    private void addButtonListeners()
    {


        findViewById(R.id.btn_takeFollowUp).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                final Intent i = new Intent(ActivityView.this,
                        TakeFollowUp.class);
                i.putExtra("enquiry_id", enquiryId);

                i.putExtra("follow_up_customer_name", customerName);
                customerContact = customerContact.substring(0, customerContact.length() - 2);
                i.putExtra("follow_up_customer_contact", customerContact);
                i.putExtra("enquiry_id", enquiryId);

                startActivity(i);
            }
        });

        findViewById(R.id.btn_closeLead).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Intent i = new Intent(ActivityView.this,
                        ActivityCloseLead.class);
                i.putExtra("enquiry_id", enquiryId);

                startActivity(i);
            }
        });


        findViewById(R.id.btn_assignLead).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Intent i = new Intent(ActivityView.this,
                        ActivityAssingLead.class);
                i.putExtra("enquiry_id", enquiryId);

                startActivity(i);
            }
        });


    }

    private void populateEnquiryStatus()
    {
        int i = -1;
        adapterEnquiryStatus = new SimpleFormAdapter(this);

        try
        {
            JSONObject obj = (JSONObject) responseJson.get("enquiry");

            final String unique_enquiry_id = obj.getString("unique_enquiry_id");
            adapterEnquiryStatus.addSimpleTextView(++i, "Enquiry ID: ", unique_enquiry_id, null);

            is_bought = obj.getString("is_bought");

            String current_lead_status = "";

            if (is_bought != null)
            {
                current_lead_status = getStatusByBoughtId(is_bought);
                if (is_bought.equals("1") || is_bought.equals("2"))
                {
                    findViewById(R.id.ll_enquiryOptionsButtonContainer).setVisibility(View.GONE);
                }
            }
            adapterEnquiryStatus.addSimpleTextView(++i, "Current Lead Status: ", current_lead_status, null);

            final String total_enquiry = obj.getString("total_enquiry");
            adapterEnquiryStatus.addSimpleTextView(++i, "Successful/Total Enquiries: ", total_enquiry, null);

            try
            {
                final String group = obj.getString("group").substring(0, obj.getString("group").lastIndexOf(','));
                adapterEnquiryStatus.addSimpleTextView(++i, "Group: ", group, null);
            } catch (StringIndexOutOfBoundsException s)
            {
                s.printStackTrace();
                final String group = obj.getString("group");
                adapterEnquiryStatus.addSimpleTextView(++i, "Group: ", group, null);
            }


        } catch (JSONException e)
        {
            e.printStackTrace();
        }


        if (fragmentEnquiryStatus == null)
        {
            fragmentEnquiryStatus = new SimpleFormFragment();
        }
        fragmentEnquiryStatus.setData(adapterEnquiryStatus, "Enquiry Status");
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag_simpleForm, fragmentEnquiryStatus, "ENQUIRY_STATUS")
                .commitAllowingStateLoss();
        adapterEnquiryStatus.notifyDataSetChanged();
    }

    private String getStatusByBoughtId(String is_bought)
    {
        if (is_bought.equals("0"))
        {
            return "New Enquiry";
        } else if (is_bought.equals("1"))
        {
            return "Successfully Closed Enquiry";
        } else if (is_bought.equals("2"))
        {
            return "Unsuccessfully Closed Enquiry";
        } else if (is_bought.equals("3"))
        {
            return "Ongoing Enquiry";
        }
        return "";
    }

    private void populateCustomerDetail()
    {
        int i = -1;
        adapterCustomerDetail = new SimpleFormAdapter(this);

        try
        {
            JSONObject obj = (JSONObject) responseJson.get("customer");

            final String customer_name = obj.getString("customer_name");

            final String prefix = obj.getString("prefix");
            adapterCustomerDetail.addSimpleTextView(++i, "Customer Name: ", prefix + " " + customer_name, null);

            final String customer_email = obj.getString("customer_email");
            adapterCustomerDetail.addSimpleTextView(++i, "Email: ", customer_email, null);

            final JSONArray arr = (JSONArray) responseJson.get("contact");

            customerContact = "";

            for (int j = 0; j < arr.length(); j++)
            {
                final String contact = arr.getJSONObject(j).getString("customer_contact_no");

                customerContact = customerContact + contact + ", ";

                adapterCustomerDetail.addSimpleTextView(++i, "Contact No: " + (j + 1), contact, new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (contact != null)
                        {
                            if (!contact.isEmpty())
                            {
                                Intent intent = new Intent(Intent.ACTION_CALL,
                                        Uri.parse("tel:" + contact));
                                if (ActivityCompat.checkSelfPermission(ActivityView.this,
                                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                                {
                                    Toast.makeText(ActivityView.this,
                                            "Call permission not granted", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                CommonUtils.promptForMakingCall(ActivityView.this, intent);
                            }
                        }
                    }
                });
            }


        } catch (JSONException e)
        {
            e.printStackTrace();
        }


        if (fragmentCustomerDetail == null)
        {
            fragmentCustomerDetail = new SimpleFormFragment();
        }
        fragmentCustomerDetail.setData(adapterCustomerDetail, "Customer Details");
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag_simpleForm, fragmentCustomerDetail, "CUSTOMER_DETAILS")
                .commit();
        adapterCustomerDetail.notifyDataSetChanged();
    }

    private void populateFollowUpDetail()
    {
        int i = -1;
        adapterFollowUpDetail = new SimpleFormAdapter(this);

        try
        {
            JSONArray arr = (JSONArray) responseJson.get("follow_up_details");

            for (int j = 0; j < arr.length(); j++)
            {
                adapterFollowUpDetail
                        .addFollowUpSeparator(++i,
                                new FollowUpSeparator("", "", "", "Follow Up " + (j + 1)));

                final String next_follow_up_date = arr.getJSONObject(j).getString("next_follow_up_date");
                adapterFollowUpDetail.addSimpleTextView(++i, "Follow Up Date: ", next_follow_up_date, null);

                final String follow_up_type = arr.getJSONObject(j).getString("follow_up_type");
                adapterFollowUpDetail.addSimpleTextView(++i, "Follow Up Type: ", follow_up_type, null);

                final String discussion = arr.getJSONObject(j).getString("discussion");
                adapterFollowUpDetail.addSimpleTextView(++i, "Discussion: ", discussion, null);

                final String handled_by = arr.getJSONObject(j).getString("handled_by");
                adapterFollowUpDetail.addSimpleTextView(++i, "Handled By: ", handled_by, null);

                final String date_added = arr.getJSONObject(j).getString("date_added");
                adapterFollowUpDetail.addSimpleTextView(++i, "Date Added: ", date_added, null);

            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        if (fragmentFollowUpDetail == null)
        {
            fragmentFollowUpDetail = new SimpleFormFragment();
        }
        fragmentFollowUpDetail.setData(adapterFollowUpDetail, "Follow Up Details");
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag_simpleForm, fragmentFollowUpDetail, "FOLLOW_UP_DETAILS")
                .commit();
        adapterFollowUpDetail.notifyDataSetChanged();
    }

    private void populateProductDetails()
    {
        int i = -1;
        adapterProductDetails = new SimpleFormAdapter(this);

        //do stuff here

        try
        {
            JSONArray arr = (JSONArray) responseJson.get("product_details");

            for (int j = 0; j < arr.length(); j++)
            {

                final String product_name = arr.getJSONObject(j).getString("product_name");
                adapterProductDetails.addSimpleTextView(++i, "Product: ", product_name, null);

                final JSONArray types = arr.getJSONObject(j).getJSONArray("type");
                for (int k = 0; k < types.length(); k++)
                {

                    String type = types.getString(k);
                    type = type.replace("[\"", "");
                    type = type.replace("\"]", "");
                    String singleType[] = type.split(":");

                    adapterProductDetails.addSimpleTextView(++i, singleType[0] + ": ", singleType[1], null);
                }

                final String quantity = arr.getJSONObject(j).getString("quantity");
                adapterProductDetails.addSimpleTextView(++i, "Quantity: ", quantity, null);

                final String price = arr.getJSONObject(j).getString("price");
                adapterProductDetails.addSimpleTextView(++i, "Estimated Price : ", price, null);


            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        ///////////////

        if (fragmentProductDetails == null)
        {
            fragmentProductDetails = new SimpleFormFragment();
        }
        fragmentProductDetails.setData(adapterProductDetails, "Product Details");
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag_simpleForm, fragmentProductDetails, "PRODUCT_DETAILS")
                .commit();
        adapterProductDetails.notifyDataSetChanged();

    }

    private void populateEnquiryDetails()
    {
        int i = -1;
        adapterEnquiryDetails = new SimpleFormAdapter(this);

        //do stuff here

        try
        {
            JSONObject obj = (JSONObject) responseJson.get("enquiry_details");

            final String enquiry_type = obj.getString("enquiry_type");
            adapterEnquiryDetails.addSimpleTextView(++i, "Enquiry Type: ", enquiry_type, null);

            final String customer_budget = obj.getString("customer_budget");
            adapterEnquiryDetails.addSimpleTextView(++i, "Customer Budget: ", customer_budget, null);

            final String discussion = obj.getString("discussion");
            adapterEnquiryDetails.addSimpleTextView(++i, "Discussion: ", discussion, null);

            final String fst_follow_up_date = obj.getString("fst_follow_up_date");
            adapterEnquiryDetails.addSimpleTextView(++i, "1st Follow Up Date: ", fst_follow_up_date, null);

            final String date_of_enquiry = obj.getString("date_of_enquiry");
            adapterEnquiryDetails.addSimpleTextView(++i, "Date of Enquiry: ", date_of_enquiry, null);

            final String enquiry_added_by = obj.getString("enquiry_added_by");
            adapterEnquiryDetails.addSimpleTextView(++i, "Enquiry Added By: ", enquiry_added_by, null);

            final String enquiry_currently_handled_by = obj.getString("enquiry_currently_handled_by");
            adapterEnquiryDetails.addSimpleTextView(++i, "Enquiry Currently Handled By : ", enquiry_currently_handled_by, null);


        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        ///////////////

        if (fragmentEnquiryDetails == null)
        {
            fragmentEnquiryDetails = new SimpleFormFragment();
        }
        fragmentEnquiryDetails.setData(adapterEnquiryDetails, "Enquiry Details");
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag_simpleForm, fragmentEnquiryDetails, "ENQUIRY_DETAILS")
                .commit();
        adapterEnquiryDetails.notifyDataSetChanged();

    }

    final class GetViewDetailAsync extends AsyncTask<Void, Void, Void>
    {

        final Context context;
        final String enquiryId;
        String response = "";

        public GetViewDetailAsync(final String enquiryId, final Context context)
        {
            this.enquiryId = enquiryId;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {

            final String host = PreferenceManager.getDefaultSharedPreferences(context).getString("host", "");
            if (host != null)
            {
                final Map postParam = new HashMap();
                postParam.put("method", "get_follow_up_view");
                postParam.put("enquiry_id", enquiryId);

                new PostServiceHandler(TAG, 2, 2000).doPostRequest(host + AppUtils.URL_WEBSERVICE, postParam, new PostServiceHandler.ResponseCallback()
                {
                    @Override
                    public void response(int status, String resp)
                    {
                        if (status == HttpURLConnection.HTTP_OK)
                        {
                            response = resp;
                        }
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if (!response.isEmpty())
            {

                parseResponse(response);

            }
        }
    }


}
