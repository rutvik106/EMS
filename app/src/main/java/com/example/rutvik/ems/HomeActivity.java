package com.example.rutvik.ems;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import adapters.HomeGridAdapter;
import models.GridItem;

public class HomeActivity extends AppCompatActivity {

    private static final String EXTRA_IMAGE = "com.antonioleiva.materializeyourapp.extraImage";
    private static final String EXTRA_TITLE = "com.antonioleiva.materializeyourapp.extraTitle";
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private GridLayoutManager gridLayoutManager;

    private Toolbar toolbar;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_home);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();

        toolbar=(Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        setTitle("Home");

        String itemTitle = getIntent().getStringExtra(EXTRA_TITLE);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
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
        gridItemList.add(new GridItem("Add New Inquiry",R.mipmap.ic_add_circle_white_48dp));
        gridItemList.add(new GridItem("Search Customers",R.mipmap.ic_search_white_48dp));
        gridItemList.add(new GridItem("Add New Customer",R.mipmap.ic_person_add_white_48dp));
        gridItemList.add(new GridItem("Reports",R.mipmap.ic_event_note_white_48dp));

        gridLayoutManager = new GridLayoutManager(this, 4);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_homeGrid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        HomeGridAdapter homeGridAdapter = new HomeGridAdapter(gridItemList);
        recyclerView.setAdapter(homeGridAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home,menu);

        return super.onCreateOptionsMenu(menu);
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


}