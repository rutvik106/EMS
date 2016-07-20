package com.example.rutvik.ems;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import adapters.ExpandableListAdapter;
import adapters.HomeGridAdapter;
import adapters.NotificationListAdapter;
import extras.AppUtils;
import extras.Log;
import extras.PostServiceHandler;
import models.FollowUp;
import models.GridItem;
import models.NotificationHeader;

public class HomeActivity extends AppCompatActivity
{

    private static final String EXTRA_IMAGE = "com.antonioleiva.materializeyourapp.extraImage";
    private static final String EXTRA_TITLE = "com.antonioleiva.materializeyourapp.extraTitle";

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private GridLayoutManager gridLayoutManager;

    private Toolbar toolbar;

    private ExpandableListView elvNotificationsListView;

    private ExpandableListAdapter expandableListAdapter;

    Map<String, List<FollowUp>> modelListMap;

    boolean isShow = false;
    int scrollRange = -1;

    List<NotificationHeader> modelList = new LinkedList<>();

    RecyclerView rv;

    RecyclerView.LayoutManager lm;

    NotificationListAdapter adapter;

    LinkedList<FollowUp> followUpArray = new LinkedList<FollowUp>();

    public static final String TAG = AppUtils.APP_TAG + HomeActivity.class.getSimpleName();

    FrameLayout flNoNotifications;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_home);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        elvNotificationsListView = (ExpandableListView) findViewById(R.id.elv_notificationList);

        flNoNotifications = (FrameLayout) findViewById(R.id.frame_noNotifications);


        /*elvNotificationsListView.setFocusable(false);*/

        //prepareData();


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
        {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
            {
                if (scrollRange == -1)
                {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0)
                {
                    setTitle("Notifications");
                    isShow = true;
                } else if (isShow)
                {
                    setTitle("EMS");
                    isShow = false;
                }
            }
        });


        //String itemTitle = getIntent().getStringExtra(EXTRA_TITLE);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //ollapsingToolbarLayout.setTitle("EMS");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        /** final ImageView image = (ImageView) findViewById(R.id.image);
         Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(image, new Callback() {
        @Override public void onSuccess() {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
        public void onGenerated(Palette palette) {
        applyPalette(palette);
        }
        });
        }

        @Override public void onError() {

        }
        });

         TextView title = (TextView) findViewById(R.id.title);
         title.setText(itemTitle);*/

        List<GridItem> gridItemList = new ArrayList<>();
        gridItemList.add(new GridItem("Add New Inquiry", R.mipmap.ic_add_circle_white_48dp));
        gridItemList.add(new GridItem("Search Customers", R.mipmap.ic_search_white_48dp));
        gridItemList.add(new GridItem("Add New Customer", R.mipmap.ic_person_add_white_48dp));
        gridItemList.add(new GridItem("Reports", R.mipmap.ic_event_note_white_48dp));

        gridLayoutManager = new GridLayoutManager(this, 4);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_homeGrid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        HomeGridAdapter homeGridAdapter = new HomeGridAdapter(this, gridItemList);
        recyclerView.setAdapter(homeGridAdapter);


        rv = (RecyclerView) findViewById(R.id.rv_notification);

        rv.setHasFixedSize(true);

        rv.setNestedScrollingEnabled(false);

        lm = new LinearLayoutManager(this);

        rv.setLayoutManager(lm);


        //prepareData();1

        getFollowUpAsync();

    }

    @Override
    protected void onStart()
    {
        super.onStart();


    }

    void getFollowUpAsync()
    {
        new AsyncTask<Void, Void, Void>()
        {

            ProgressDialog dialog;

            String response = "";

            @Override
            protected void onPreExecute()
            {
                dialog = new ProgressDialog(HomeActivity.this);
                dialog.setTitle("Please Wait");
                dialog.setMessage("Getting follow up...");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialog)
                    {
                        HomeActivity.this.finish();
                    }
                });
                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... params)
            {
                try
                {
                    response = new PostServiceHandler(AppUtils.APP_TAG, 3, 2000).doGet("http://192.168.1.134/ems/webservice/webservice.php?method=get_follow_up&id=29");
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                Log.i(TAG, "RESPONSE: " + response);
                if (!response.isEmpty())
                {
                    Log.i(TAG, "RESPONSE NOT EMPTY");
                    try
                    {
                        followUpArray.clear();
                        Log.i(TAG, "PARSING JSON");
                        JSONArray arr = new JSONArray(response);
                        Log.i(TAG, "JSON ARRAY LENGTH: " + arr.length());
                        for (int i = 0; i < arr.length(); i++)
                        {
                            followUpArray.add(new FollowUp(arr.getJSONObject(i)));
                        }


                        adapter = new NotificationListAdapter(HomeActivity.this, preapareModelList(followUpArray));

                        rv.setAdapter(adapter);

                        if (adapter.getItemCount() < 1)
                        {
                            flNoNotifications.setVisibility(View.VISIBLE);
                        } else
                        {
                            flNoNotifications.setVisibility(View.GONE);
                        }

                        Toast.makeText(HomeActivity.this, "Model Size: " + modelList.size(), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "parsing error", Toast.LENGTH_SHORT).show();
                    }
                }

                if (dialog != null)
                {
                    if (dialog.isShowing())
                    {
                        dialog.dismiss();
                    }
                }

            }
        }.execute();
    }



    /*void prepareData() {

        final String data = "{\"follow_up\":{ \"id\":\"1\", \"follow_up_date\":\"14-4-2016\", \"discussion\":\"yoyo\", \"name\":\"jeet patel\", \"product\":\"ems\", \"extra_details\":\"extra long\", \"phone\":\"9824243009\", \"handle_by\":\"sanket jasani\" } }";


        try {
            final JSONObject obj = new JSONObject(data).getJSONObject("follow_up");

            modelList = new ArrayList<>();
            modelList.add("Today");
            modelList.add("Tomorrow");

            List<FollowUp> today = new ArrayList<>();
            today.add(new FollowUp(obj, "Today"));
            today.add(new FollowUp(obj, "Today"));

            List<FollowUp> tomorrow = new ArrayList<>();
            tomorrow.add(new FollowUp(obj, "Tomorrow"));
            tomorrow.add(new FollowUp(obj, "Tomorrow"));

            modelListMap = new HashMap<>();
            modelListMap.put(modelList.get(0), today);
            modelListMap.put(modelList.get(1), tomorrow);

            expandableListAdapter = new ExpandableListAdapter(this, modelList, modelListMap);
            elvNotificationsListView.setAdapter(expandableListAdapter);

            for (int i = 0; i < expandableListAdapter.getGroupCount(); i++) {
                elvNotificationsListView.expandGroup(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "error parsing json", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_profile:
                //startActivity(new Intent(this, SettingsActivity.class));
                return true;

            case R.id.action_refresh:
                getFollowUpAsync();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent)
    {
        try
        {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e)
        {
            return false;
        }
    }

    private void initActivityTransitions()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }


    private List preapareModelList(List<FollowUp> followUpList)
    {
        modelList.clear();
        Log.i(TAG, "PREPARING MODEL LIST");
        Log.i(TAG, "FOLLOW UP LIST SIZE: " + followUpList.size());
        HashMap<String, List<FollowUp>> dates = new LinkedHashMap<>();
        for (FollowUp followUp : followUpList)
        {
            Log.i(TAG, "follow up date: " + followUp.getFollowUpDate());
            if (dates.get(dateToNotificationLabel(followUp.getFollowUpDate())) == null)
            {
                Log.i(TAG, "DATE NOT FOUND IN dates map");
                List<FollowUp> f = new LinkedList<>();
                f.add(followUp);
                dates.put(dateToNotificationLabel(followUp.getFollowUpDate()), f);
            } else
            {
                dates.get(dateToNotificationLabel(followUp.getFollowUpDate())).add(followUp);
            }
        }
        Set<Map.Entry<String, List<FollowUp>>> set = dates.entrySet();
        Iterator<Map.Entry<String, List<FollowUp>>> entry = set.iterator();

        while (entry.hasNext())
        {
            Map.Entry<String, List<FollowUp>> singleEntry = entry.next();
            Log.i(TAG, "KEY: " + singleEntry.getKey());
            modelList.add(new NotificationHeader(singleEntry.getKey(), singleEntry.getValue()));

        }

        return modelList;
    }

    private String dateToNotificationLabel(String date)
    {
        Log.i(TAG, "CHECKING DATE: " + date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String d = null;
        String today = null;
        String tomorrow = null;

        try
        {
            d = simpleDateFormat.format(simpleDateFormat.parse(date));
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        today = simpleDateFormat.format(Calendar.getInstance().getTime());
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        tomorrow = simpleDateFormat.format(c.getTime());
        Log.i(TAG, "DATE: " + d);
        Log.i(TAG, "TODAY: " + today);
        Log.i(TAG, "TOMORROW: " + tomorrow);


        if (d.equals(today))
        {
            return "Today";
        } else if (d.equals(tomorrow))
        {
            return "Tomorrow";
        } else
        {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String dt = d;
            try
            {
                dt = simpleDateFormat2.format(simpleDateFormat.parse(d));
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
            return dt;
        }
    }


    void prepareData()
    {

        final String data = "{\"follow_up\":{ \"id\":\"1\", \"follow_up_date\":\"14-4-2016\", \"discussion\":\"yoyo\", \"name\":\"jeet patel\", \"product\":\"ems\", \"extra_details\":\"extra long\", \"phone\":\"9824243009\", \"handle_by\":\"sanket jasani\" } }";


        modelList = new LinkedList();


        try
        {

            final JSONObject obj = new JSONObject(data).getJSONObject("follow_up");

            List<FollowUp> f1 = new LinkedList<>();
            f1.add(new FollowUp(obj));
            f1.add(new FollowUp(obj));
            f1.add(new FollowUp(obj));
            f1.add(new FollowUp(obj));

            modelList.add(new NotificationHeader("Today", f1));

            List<FollowUp> f2 = new LinkedList<>();
            f2.add(new FollowUp(obj));
            f2.add(new FollowUp(obj));
            f2.add(new FollowUp(obj));
            f2.add(new FollowUp(obj));

            modelList.add(new NotificationHeader("Tomorrow", f2));

            adapter = new NotificationListAdapter(this, modelList);

            rv.setAdapter(adapter);

        } catch (JSONException e)
        {
            Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

}