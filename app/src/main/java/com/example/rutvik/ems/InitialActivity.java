package com.example.rutvik.ems;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import extras.AppUtils;
import extras.PostServiceHandler;
import jsonobject.Error;
import jsonobject.User;

public class InitialActivity extends AppCompatActivity implements View.OnClickListener
{

    //Toolbar toolbar;

    public static final String TAG = AppUtils.APP_TAG + InitialActivity.class.getSimpleName();

    Button btnLogin;

    EditText etHost, etUsername, etPassword;

    //TextInputLayout tilHost, tilUsername, tilPassword;

    LinearLayout llLoggingIn;

    TryLogin tryLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_initial);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

        //toolbar.setTitle("EMS");

        btnLogin = (Button) findViewById(R.id.btn_login);

        llLoggingIn = (LinearLayout) findViewById(R.id.ll_loggingIn);

        etHost = (EditText) findViewById(R.id.et_host);

        etUsername = (EditText) findViewById(R.id.et_username);

        etPassword = (EditText) findViewById(R.id.et_password);

        TextView tv = (TextView) findViewById(R.id.tv_emsTitle);

        /**       tilHost = (TextInputLayout) findViewById(R.id.til_host);

         tilUsername = (TextInputLayout) findViewById(R.id.til_username);

         tilPassword = (TextInputLayout) findViewById(R.id.til_password);*/

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/carrois_gothic.ttf");

        tv.setTypeface(font);

        btnLogin.setOnClickListener(this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String host = sp.getString("host", "");
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");

        if (!host.isEmpty() && !username.isEmpty() && !password.isEmpty())
        {
            etHost.setText(host);
            etUsername.setText(username);
            etPassword.setText(password);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_initial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v)
    {
        String tmpHost = etHost.getText().toString();
        if (!tmpHost.contains("http://"))
        {
            tmpHost = "http://" + tmpHost;
        }

        final String host = tmpHost;
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        if (isFieldsValid(host, username, password))
        {
            tryLogin = new TryLogin(host, username, password);
            tryLogin.execute();
        }


    }

    class TryLogin extends AsyncTask<Void, Void, Void>
    {

        String host, username, password;

        boolean success = false;

        Error error;

        User user;

        public TryLogin(String host, String username, String password)
        {
            this.host = host;
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute()
        {
            llLoggingIn.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            Map<String, String> postParams = new HashMap<>();
            postParams.put("method", "try_login");
            postParams.put("username", username);
            postParams.put("password", password);

            new PostServiceHandler(TAG, 2, 4000).doPostRequest(host + AppUtils.URL_WEBSERVICE, postParams, new PostServiceHandler.ResponseCallback()
            {
                @Override
                public void response(int status, String response)
                {
                    if (status == HttpURLConnection.HTTP_OK)
                    {

                        try
                        {
                            user = new User(response);
                            success = true;
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            try
                            {
                                error = new Error(response);
                            } catch (JSONException ee)
                            {
                                ee.printStackTrace();
                            }
                        }

                    }
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if (success)
            {
                if (user != null)
                {

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(InitialActivity.this);

                    sp.edit()
                            .putString("host", host)
                            .putString("username", username)
                            .putString("password", password).apply();

                    ((App) getApplication()).setUser(user);

                    Intent i = new Intent(InitialActivity.this, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                }
            } else
            {
                if (error != null)
                {
                    Toast.makeText(InitialActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }

                llLoggingIn.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);

                Toast.makeText(InitialActivity.this, "Login failed, Please check host/network", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isFieldsValid(String host, String username, String password)
    {

        boolean isValid = true;

        if (host.isEmpty())
        {
            isValid = false;
            //tilHost.setErrorEnabled(true);
            etHost.setError("Enter valid host");
        } else
        {
            //tilHost.setErrorEnabled(false);
            etHost.setError(null);
        }
        if (username.isEmpty())
        {
            isValid = false;
            //tilUsername.setErrorEnabled(true);
            etUsername.setError("Enter username");
        } else
        {
            //tilUsername.setErrorEnabled(false);
            etUsername.setError(null);
        }
        if (password.isEmpty())
        {
            isValid = false;
            //tilPassword.setErrorEnabled(true);
            etPassword.setError("Enter password");
        } else
        {
            //tilPassword.setErrorEnabled(false);
            etPassword.setError(null);
        }

        return isValid;

    }

}
