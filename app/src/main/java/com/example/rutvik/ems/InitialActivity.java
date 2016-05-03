package com.example.rutvik.ems;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.zip.Inflater;

public class InitialActivity extends AppCompatActivity implements View.OnClickListener {

    //Toolbar toolbar;

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

        //toolbar.setTitle("EMS");

        btnLogin=(Button) findViewById(R.id.btn_login);

        TextView tv=(TextView) findViewById(R.id.tv_emsTitle);

        Typeface font=Typeface.createFromAsset(getAssets(),"fonts/carrois_gothic.ttf");

        tv.setTypeface(font);

        btnLogin.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_initial, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,HomeActivity.class));
    }
}
