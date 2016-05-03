package com.example.rutvik.ems;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import adapters.ExpandableListAdapter;
import adapters.NotificationListAdapter;
import models.FollowUp;
import models.NotificationHeader;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;

    List modelList;

    RecyclerView rv;

    RecyclerView.LayoutManager lm;

    NotificationListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Settings");

        rv=(RecyclerView) findViewById(R.id.rv_notification);

        rv.setHasFixedSize(true);

        lm=new LinearLayoutManager(this);

        rv.setLayoutManager(lm);

        //prepareData();

    }


    /*void prepareData() {

        final String data = "{\"follow_up\":{ \"id\":\"1\", \"follow_up_date\":\"14-4-2016\", \"discussion\":\"yoyo\", \"name\":\"jeet patel\", \"product\":\"ems\", \"extra_details\":\"extra long\", \"phone\":\"9824243009\", \"handle_by\":\"sanket jasani\" } }";



        modelList = new LinkedList();
        modelList.add(new NotificationHeader("Today", "4"));

        try {
            final JSONObject obj = new JSONObject(data).getJSONObject("follow_up");

            modelList.add(new FollowUp(obj, "Today"));
            modelList.add(new FollowUp(obj, "Today"));
            modelList.add(new FollowUp(obj, "Today"));
            modelList.add(new FollowUp(obj, "Today"));

            modelList.add(new NotificationHeader("Tomorrow", "4"));

            modelList.add(new FollowUp(obj, "Tomorrow"));
            modelList.add(new FollowUp(obj, "Tomorrow"));
            modelList.add(new FollowUp(obj, "Tomorrow"));
            modelList.add(new FollowUp(obj, "Tomorrow"));

            adapter=new NotificationListAdapter(this,modelList);

            rv.setAdapter(adapter);

        } catch (JSONException e) {
            Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
*/
}
