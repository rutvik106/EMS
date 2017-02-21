package com.tapandtype.rutvik.ems;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import api.API;
import apimodels.AssignUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAssingLead extends AppCompatActivity
{

    AppCompatSpinner spinAssignUser;

    String enquiryId;

    String assignedUserId;

    AssignUserSpinnerAdapter adapter;

    List<AssignUser> assignUserList;

    private String assignedUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assing_lead);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle("Assign Lead");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        spinAssignUser = (AppCompatSpinner) findViewById(R.id.spin_assignUser);

        enquiryId = getIntent().getStringExtra("enquiry_id");

        if (enquiryId != null)
        {

            spinAssignUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
                    AssignUser assignUser = (AssignUser) adapterView.getAdapter().getItem(i);
                    //Toast.makeText(ActivityAssingLead.this, assignUser.getAdminName(), Toast.LENGTH_SHORT).show();
                    assignedUserId = assignUser.getAdminId();
                    assignedUserName = assignUser.getAdminName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView)
                {

                }
            });

            findViewById(R.id.btn_assignLeadTo).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (assignedUserId != null)
                    {
                        if (!assignedUserId.isEmpty())
                        {
                            promptUserForAssigningLead(assignedUserName);
                        }
                    }
                }
            });

            getAssignUsers();


        }


    }

    private void promptUserForAssigningLead(String name)
    {

        new AlertDialog.Builder(this)
                .setTitle("Assign Lead")
                .setMessage("Assign this lead to " + assignedUserName + "?")
                .setPositiveButton("ASSIGN", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        assignLeadTo();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();

    }

    private void assignLeadTo()
    {
        final String sessionId = ((App) getApplication()).getUser().getSession_id();

        new API(this).assignLeadTo(sessionId, enquiryId, assignedUserId, new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if (response.isSuccessful())
                {
                    if (response.body().contains("success"))
                    {
                        Toast.makeText(ActivityAssingLead.this, "Assigned Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                    {
                        Toast.makeText(ActivityAssingLead.this, "", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {

            }
        });
    }

    private void getAssignUsers()
    {
        new API(this).getListUsersForAssignLead(new Callback<List<AssignUser>>()
        {
            @Override
            public void onResponse(Call<List<AssignUser>> call, Response<List<AssignUser>> response)
            {
                if (response.isSuccessful())
                {
                    assignUserList = response.body();
                    adapter = new AssignUserSpinnerAdapter(ActivityAssingLead.this,
                            R.layout.single_spinner_row, assignUserList);
                    spinAssignUser.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else
                {
                    Toast.makeText(ActivityAssingLead.this, "Try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AssignUser>> call, Throwable t)
            {
                Toast.makeText(ActivityAssingLead.this, "Try again later", Toast.LENGTH_SHORT).show();
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


    class AssignUserSpinnerAdapter extends ArrayAdapter<AssignUser>
    {

        public AssignUserSpinnerAdapter(Context context, int resource, List<AssignUser> objects)
        {
            super(context, resource, objects);
        }

        @Override
        public int getCount()
        {
            return assignUserList.size();
        }

        @Nullable
        @Override
        public AssignUser getItem(int position)
        {
            return assignUserList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return assignUserList.get(position).hashCode();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = LayoutInflater.from(ActivityAssingLead.this)
                        .inflate(R.layout.single_spinner_row, parent, false);
            }

            TextView tvAssignedUser = (TextView) convertView.findViewById(android.R.id.text1);

            tvAssignedUser.setText(assignUserList.get(position).getAdminName());

            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = LayoutInflater.from(ActivityAssingLead.this)
                        .inflate(R.layout.single_spinner_row, parent, false);
            }

            TextView tvAssignedUser = (TextView) convertView.findViewById(android.R.id.text1);

            tvAssignedUser.setText(assignUserList.get(position).getAdminName());

            return convertView;
        }
    }


}
