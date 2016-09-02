package fragments;

/**
 * Created by rutvik on 31-08-2016 at 05:06 PM.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tapandtype.rutvik.ems.App;
import com.tapandtype.rutvik.ems.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import adapters.NotificationListAdapter;
import extras.AppUtils;
import extras.Log;
import extras.PostServiceHandler;
import models.FollowUp;
import models.NotificationHeader;


public class ExpiredFollowUpFragment extends Fragment
{


    private static final String TAG = AppUtils.APP_TAG + NotificationFragment.class.getSimpleName();

    RecyclerView rv;

    RecyclerView.LayoutManager lm;

    NotificationListAdapter adapter;

    FrameLayout flNoNotifications;
    FrameLayout flLoadingFollowUp;

    Context context;

    List<NotificationHeader> modelList = new LinkedList<>();

    private LinkedList<FollowUp> expiredFollowUpArray = new LinkedList<>();

    public ExpiredFollowUpFragment()
    {

    }

    public static ExpiredFollowUpFragment getInstance(Context context)
    {
        ExpiredFollowUpFragment fragment = new ExpiredFollowUpFragment();
        fragment.context = context;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_follow_up, container, false);

        flNoNotifications = (FrameLayout) rootView.findViewById(R.id.frame_noNotifications);
        flLoadingFollowUp = (FrameLayout) rootView.findViewById(R.id.fl_loadingFollowUp);


        rv = (RecyclerView) rootView.findViewById(R.id.rv_notification);

        rv.setHasFixedSize(true);

        rv.setNestedScrollingEnabled(false);

        lm = new LinearLayoutManager(context);

        rv.setLayoutManager(lm);

        getFollowUpAsync();

        return rootView;

    }

    void getFollowUpAsync()
    {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        final String host = sp.getString("host", "http://127.0.0.1/");

        final String sessionId = ((App) getActivity().getApplication()).getUser().getSession_id();

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
                        expiredFollowUpArray.clear();
                        Log.i(TAG, "PARSING JSON");
                        JSONArray upcomingFollowUps = new JSONObject(response).getJSONArray("expired");
                        Log.i(TAG, "JSON ARRAY LENGTH: " + upcomingFollowUps.length());
                        for (int i = 0; i < upcomingFollowUps.length(); i++)
                        {
                            expiredFollowUpArray.add(new FollowUp(upcomingFollowUps.getJSONObject(i)));
                        }

                        setupAdapter(expiredFollowUpArray);

                        //Toast.makeText(HomeActivity.this, "Model Size: " + modelList.size(), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "parsing error", Toast.LENGTH_SHORT).show();
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

    public void setupAdapter(final LinkedList<FollowUp> followUpArray)
    {
        adapter = new NotificationListAdapter(context, preapareModelList(followUpArray));

        rv.setAdapter(adapter);

        if (adapter.getItemCount() < 1)
        {
            flNoNotifications.setVisibility(View.VISIBLE);
        } else
        {
            flNoNotifications.setVisibility(View.GONE);
        }

        flLoadingFollowUp.setVisibility(View.GONE);
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



}

