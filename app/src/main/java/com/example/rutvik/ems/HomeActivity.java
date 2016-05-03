package com.example.rutvik.ems;

import android.content.Intent;
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
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import adapters.ExpandableListAdapter;
import adapters.HomeGridAdapter;
import adapters.NotificationListAdapter;
import models.FollowUp;
import models.GridItem;
import models.NotificationHeader;

public class HomeActivity extends AppCompatActivity {

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

    List<NotificationHeader> modelList;

    RecyclerView rv;

    RecyclerView.LayoutManager lm;

    NotificationListAdapter adapter;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_home);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        elvNotificationsListView = (ExpandableListView) findViewById(R.id.elv_notificationList);



        /*elvNotificationsListView.setFocusable(false);*/

        //prepareData();


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    setTitle("Notifications");
                    isShow = true;
                } else if (isShow) {
                    setTitle("EMS");
                    isShow = false;
                }
            }
        });


        //String itemTitle = getIntent().getStringExtra(EXTRA_TITLE);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //ollapsingToolbarLayout.setTitle("EMS");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

       /* final ImageView image = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(image, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override
            public void onError() {

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

        HomeGridAdapter homeGridAdapter = new HomeGridAdapter(gridItemList);
        recyclerView.setAdapter(homeGridAdapter);


        rv = (RecyclerView) findViewById(R.id.rv_notification);

        rv.setHasFixedSize(true);

        rv.setNestedScrollingEnabled(false);

        lm = new LinearLayoutManager(this);

        rv.setLayoutManager(lm);

        prepareData();


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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    void prepareData() {

        final String data = "{\"follow_up\":{ \"id\":\"1\", \"follow_up_date\":\"14-4-2016\", \"discussion\":\"yoyo\", \"name\":\"jeet patel\", \"product\":\"ems\", \"extra_details\":\"extra long\", \"phone\":\"9824243009\", \"handle_by\":\"sanket jasani\" } }";


        modelList = new LinkedList();


        try {

            final JSONObject obj = new JSONObject(data).getJSONObject("follow_up");

            List<FollowUp> f1 = new LinkedList<>();
            f1.add(new FollowUp(obj, "Today"));
            f1.add(new FollowUp(obj, "Today"));
            f1.add(new FollowUp(obj, "Today"));
            f1.add(new FollowUp(obj, "Today"));

            modelList.add(new NotificationHeader("Today", f1));

            List<FollowUp> f2 = new LinkedList<>();
            f2.add(new FollowUp(obj, "Tomorrow"));
            f2.add(new FollowUp(obj, "Tomorrow"));
            f2.add(new FollowUp(obj, "Tomorrow"));
            f2.add(new FollowUp(obj, "Tomorrow"));

            modelList.add(new NotificationHeader("Tomorrow", f2));

            adapter = new NotificationListAdapter(this, modelList);

            rv.setAdapter(adapter);

        } catch (JSONException e) {
            Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


}