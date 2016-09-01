package com.example.rutvik.ems;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import adapters.HomeGridAdapter;
import adapters.ViewPagerAdapter;
import extras.AppUtils;
import extras.Log;
import extras.PostServiceHandler;
import fragments.ExpiredFollowUpFragment;
import fragments.NotificationFragment;
import models.FollowUp;
import models.GridItem;

public class HomeActivity extends AppCompatActivity
{


    private static final String EXTRA_IMAGE = "com.antonioleiva.materializeyourapp.extraImage";
    private static final String EXTRA_TITLE = "com.antonioleiva.materializeyourapp.extraTitle";

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private GridLayoutManager gridLayoutManager;

    private Toolbar toolbar;

    boolean isShow = false;
    int scrollRange = -1;


    public static final String TAG = AppUtils.APP_TAG + HomeActivity.class.getSimpleName();


    private TabLayout tabLayout;
    private ViewPager viewPager;

    final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

    NotificationFragment upcoming;
    NotificationFragment expired;


    private LinkedList<FollowUp> upcomingFollowUpArray = new LinkedList<>();
    private LinkedList<FollowUp> expiredFollowUpArray = new LinkedList<>();


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


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener()
        {
            @Override
            public void onViewAttachedToWindow(View view)
            {
                Log.i(TAG, "onViewAttachedToWindow: view attached to pager now");
                getFollowUpAsync();
            }

            @Override
            public void onViewDetachedFromWindow(View view)
            {

            }
        });

        //setupTabIcons();


        //prepareData();

        //getFollowUpAsync();

    }

    private void setupTabIcons()
    {

        tabLayout.getTabAt(0).setIcon(R.drawable.logo);

    }

    private void setupViewPager(ViewPager viewPager)
    {

        upcoming = NotificationFragment.getInstance(this);
        expired = NotificationFragment.getInstance(this);

        adapter.addFragment(upcoming, "UPCOMING");
        adapter.addFragment(expired, "EXPIRED");

        viewPager.setAdapter(adapter);

    }

    @Override
    protected void onStart()
    {
        super.onStart();


    }


    /**
     * void prepareData() {
     * <p>
     * final String data = "{\"follow_up\":{ \"id\":\"1\", \"follow_up_date\":\"14-4-2016\", \"discussion\":\"yoyo\", \"name\":\"jeet patel\", \"product\":\"ems\", \"extra_details\":\"extra long\", \"phone\":\"9824243009\", \"handle_by\":\"sanket jasani\" } }";
     * <p>
     * <p>
     * try {
     * final JSONObject obj = new JSONObject(data).getJSONObject("follow_up");
     * <p>
     * modelList = new ArrayList<>();
     * modelList.add("Today");
     * modelList.add("Tomorrow");
     * <p>
     * List<FollowUp> today = new ArrayList<>();
     * today.add(new FollowUp(obj, "Today"));
     * today.add(new FollowUp(obj, "Today"));
     * <p>
     * List<FollowUp> tomorrow = new ArrayList<>();
     * tomorrow.add(new FollowUp(obj, "Tomorrow"));
     * tomorrow.add(new FollowUp(obj, "Tomorrow"));
     * <p>
     * modelListMap = new HashMap<>();
     * modelListMap.put(modelList.get(0), today);
     * modelListMap.put(modelList.get(1), tomorrow);
     * <p>
     * expandableListAdapter = new ExpandableListAdapter(this, modelList, modelListMap);
     * elvNotificationsListView.setAdapter(expandableListAdapter);
     * <p>
     * for (int i = 0; i < expandableListAdapter.getGroupCount(); i++) {
     * elvNotificationsListView.expandGroup(i);
     * }
     * <p>
     * } catch (Exception e) {
     * e.printStackTrace();
     * Toast.makeText(this, "error parsing json", Toast.LENGTH_SHORT).show();
     * }
     * }
     */

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
                //getFollowUpAsync();
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


    void getFollowUpAsync()
    {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);

        final String host = sp.getString("host", "http://127.0.0.1/");

        final String sessionId = ((App) getApplication()).getUser().getSession_id();

        new AsyncTask<Void, Void, Void>()
        {

            //ProgressDialog dialog;

            String response = "";

            @Override
            protected void onPreExecute()
            {
                /**                dialog = new ProgressDialog(HomeActivity.this);
                 dialog.setTitle("Please Wait");
                 dialog.setMessage("Getting follow up...");
                 dialog.setCancelable(true);
                 dialog.setCanceledOnTouchOutside(false);
                 dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
                 {
                 @Override public void onCancel(DialogInterface dialog)
                 {
                 HomeActivity.this.finish();
                 }
                 });
                 dialog.show();*/
            }

            @Override
            protected Void doInBackground(Void... params)
            {

                Map<String, String> postParams = new HashMap<>();
                postParams.put("method", "get_follow_up");
                postParams.put("session_id", sessionId);

                new PostServiceHandler(AppUtils.APP_TAG, 2, 2000)
                        .doPostRequest(host + AppUtils.URL_WEBSERVICE, postParams, new PostServiceHandler.ResponseCallback()
                        {
                            @Override
                            public void response(int status, String r)
                            {
                                if (status == HttpURLConnection.HTTP_OK)
                                {
                                    response = r;
                                }
                            }
                        });

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
                        upcomingFollowUpArray.clear();
                        Log.i(TAG, "PARSING JSON");
                        JSONArray upcomingFollowUps = new JSONObject(response).getJSONArray("upcoming");
                        Log.i(TAG, "JSON ARRAY LENGTH: " + upcomingFollowUps.length());
                        for (int i = 0; i < upcomingFollowUps.length(); i++)
                        {
                            upcomingFollowUpArray.add(new FollowUp(upcomingFollowUps.getJSONObject(i)));
                        }

                        upcoming.setupAdapter(upcomingFollowUpArray);

                        //Toast.makeText(HomeActivity.this, "Model Size: " + modelList.size(), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "parsing error", Toast.LENGTH_SHORT).show();
                    }


                    try
                    {
                        expiredFollowUpArray.clear();
                        Log.i(TAG, "PARSING JSON");
                        JSONArray expiredFollowUps = new JSONObject(response).getJSONArray("expired");
                        Log.i(TAG, "JSON ARRAY LENGTH: " + expiredFollowUps.length());
                        for (int i = 0; i < expiredFollowUps.length(); i++)
                        {
                            expiredFollowUpArray.add(new FollowUp(expiredFollowUps.getJSONObject(i)));
                        }

                        expired.setupAdapter(expiredFollowUpArray);

                        //Toast.makeText(HomeActivity.this, "Model Size: " + modelList.size(), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "parsing error", Toast.LENGTH_SHORT).show();
                    }


                }

                /**if (dialog != null)
                 {
                 if (dialog.isShowing())
                 {
                 dialog.dismiss();
                 }
                 }*/

            }
        }.execute();
    }


}