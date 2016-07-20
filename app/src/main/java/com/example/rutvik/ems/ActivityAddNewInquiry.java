package com.example.rutvik.ems;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import adapters.SimpleFormAdapter;
import extras.AppUtils;
import extras.Log;
import fragments.SimpleFormFragment;

public class ActivityAddNewInquiry extends AppCompatActivity
{

    public final static String TAG = AppUtils.APP_TAG + ActivityAddNewInquiry.class.getSimpleName();

    private Toolbar mToolbar;

    SimpleFormAdapter simpleFormAdapter;
    SimpleFormFragment simpleFormFragment;

    SimpleFormAdapter interestedProductDetailsAdapter;
    SimpleFormFragment interestedProductDetailsFragment;

    SimpleFormAdapter leadInquiryDetailsAdapter;
    SimpleFormFragment leadInquiryDetailsFragment;

    LinearLayout fragSimpleForm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_inquiry);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (mToolbar != null)
        {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("Add New Inquiry");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fragSimpleForm = (LinearLayout) findViewById(R.id.frag_simpleForm);

        if (savedInstanceState != null)
        {
            Log.i(TAG, "RESTORING SAVED INSTANCE");
            simpleFormFragment = (SimpleFormFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ADD_NEW_INQUIRY");
        }

        populateAddNewInquiryForm();

        populateInterestedProductDetails();

        populateLeadInquiryDetails();



    }

    public void setAddEnquiryButton()
    {

        Button addEnquiry = new Button(this);
        ViewGroup.LayoutParams lParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        addEnquiry.setLayoutParams(lParams);
        addEnquiry.setText("Add Enquiry");
        addEnquiry.setTextColor(Color.WHITE);
        addEnquiry.getBackground().setColorFilter(0xFFFF9800, PorterDuff.Mode.MULTIPLY);

        fragSimpleForm.addView(addEnquiry,3);


        /*<Button
                style="@style/Widget.AppCompat.Button.Colored"
        android:layout_height="60dp"
        android:layout_margin="5dp"
        android:elevation="4dp"
        android:text="Add Enquiry"
        android:textColor="@color/white"
        android:textStyle="bold"
        android:layout_width="match_parent"
        android:layout_below="@+id/scrollView"/>*/
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


        simpleFormAdapter.addTextBox("Enquiry Date*", "", ++i, InputType.TYPE_CLASS_TEXT, false, date);

        Map<String, String> customerPrefixMap = new HashMap<>();
        customerPrefixMap.put("", "Mr.");
        customerPrefixMap.put("", "Ms.");
        simpleFormAdapter.addSpinner("Customer Prefix", "", customerPrefixMap, ++i);

        simpleFormAdapter.addTextBox("Customer Name*", "c_name", ++i, InputType.TYPE_CLASS_TEXT, true, "");

        simpleFormAdapter.addAppendableTextBox("Contact No*", "", ++i);

        simpleFormAdapter.addTextBox("Email Address", "c_name", ++i, InputType.TYPE_CLASS_TEXT, true, "");

        if (simpleFormFragment == null)
        {
            simpleFormFragment = new SimpleFormFragment();
        }

        simpleFormFragment.setData(simpleFormAdapter, "Customer Details");

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag_simpleForm, simpleFormFragment, "ADD_NEW_INQUIRY")
                .commit();

        simpleFormAdapter.notifyDataSetChanged();

    }

    private void populateInterestedProductDetails()
    {
        int i = -1;

        interestedProductDetailsAdapter = new SimpleFormAdapter(this);

        interestedProductDetailsAdapter.addInquiryProduct(++i);

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

        Map<String, String> enquiryGroup = new HashMap<>();
        enquiryGroup.put("0", "High Priority");
        enquiryGroup.put("1", "Trading");
        enquiryGroup.put("2", "Contracting");
        enquiryGroup.put("3", "Architect");
        leadInquiryDetailsAdapter.addSpinner("Add To Enquiry Group*", "to_group", enquiryGroup, ++i);

        leadInquiryDetailsAdapter.addTextBox("Customer Budget*", "c_budget", ++i, InputType.TYPE_CLASS_TEXT, true, "");

        Map<String, String> enquiryType = new HashMap<>();
        enquiryType.put("0", "Walk In");
        enquiryType.put("1", "Tele Calling");
        enquiryType.put("2", "Reference");
        enquiryType.put("3", "Advertisement");
        enquiryType.put("4", "Architect");
        enquiryType.put("5", "Structure Engineer");
        leadInquiryDetailsAdapter.addSpinner("Enquiry Type", "to_group", enquiryType, ++i);

        leadInquiryDetailsAdapter.addTextBox("Discussion", "discussion", ++i, InputType.TYPE_CLASS_TEXT, true, "");

        leadInquiryDetailsAdapter.addDatePicker("Follow Up Date", "f_date", ++i, "Pick Follow Up Date");


        Map<String, String> smsOption = new HashMap<>();
        smsOption.put("0", "Yes");
        smsOption.put("1", "No");
        leadInquiryDetailsAdapter.addSpinner("Enquiry Type", "to_group", smsOption, ++i);

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
        },10);


    }

}
