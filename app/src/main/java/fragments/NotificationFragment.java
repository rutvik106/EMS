package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tapandtype.rutvik.ems.R;

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
import models.FollowUp;
import models.NotificationHeader;

/**
 * Created by rutvik on 26-04-2016 at 07:00 PM.
 */
public class NotificationFragment extends Fragment
{


    private static final String TAG = AppUtils.APP_TAG + NotificationFragment.class.getSimpleName();

    RecyclerView rv;

    RecyclerView.LayoutManager lm;

    NotificationListAdapter adapter;

    FrameLayout flNoNotifications;
    FrameLayout flLoadingFollowUp;

    Context context;

    List<NotificationHeader> modelList = new LinkedList<>();


    public NotificationFragment()
    {

    }

    public static NotificationFragment getInstance(Context context)
    {
        NotificationFragment fragment = new NotificationFragment();
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

        rv.setNestedScrollingEnabled(true);

        lm = new LinearLayoutManager(context);

        rv.setLayoutManager(lm);

        return rootView;

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
