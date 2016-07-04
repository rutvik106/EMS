package com.example.rutvik.ems;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TakeFollowUp extends AppCompatActivity
{

    Toolbar toolbar;

    TextView tvFollowUpCustomerName, tvFollowUpCustomerContact;

    LinearLayout llCustomerContact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_follow_up);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null)
        {
            toolbar.setTitle("Take Follow Up");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            upArrow.setColorFilter(getResources().getColor(R.color.mdtp_white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        tvFollowUpCustomerName = (TextView) findViewById(R.id.tv_followUpCustomerName);
        tvFollowUpCustomerContact = (TextView) findViewById(R.id.tv_followUpCustomerContact);
        llCustomerContact = (LinearLayout) findViewById(R.id.ll_customerContact);

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

        String customerName, customerContact;
        customerName = getIntent().getStringExtra("follow_up_customer_name");
        customerContact = getIntent().getStringExtra("follow_up_customer_contact");

        if (customerName != null && customerContact != null)
        {
            tvFollowUpCustomerName.setText(customerName);
            tvFollowUpCustomerContact.setText(customerContact);
        }

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
}
