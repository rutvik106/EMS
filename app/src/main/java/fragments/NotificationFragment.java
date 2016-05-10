package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.rutvik.ems.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.ExpandableListAdapter;
import extras.AppUtils;
import models.FollowUp;

/**
 * Created by rutvik on 26-04-2016 at 07:00 PM.
 */
public class NotificationFragment extends Fragment {


    private static final String TAG = AppUtils.APP_TAG + NotificationFragment.class.getSimpleName();

    private ExpandableListView elvNotificationsListView;

    private ExpandableListAdapter expandableListAdapter;

    List<String> modelList;

    Map<String, List<FollowUp>> modelListMap;

    public NotificationFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notification_fragment, container, false);

        elvNotificationsListView = (ExpandableListView) rootView.findViewById(R.id.elv_notificationList);

        prepareData();

        return rootView;

    }


    void prepareData() {

        final String data = "{\"follow_up\":{ \"id\":\"1\", \"follow_up_date\":\"14-4-2016\", \"discussion\":\"yoyo\", \"name\":\"jeet patel\", \"product\":\"ems\", \"extra_details\":\"extra long\", \"phone\":\"9824243009\", \"handle_by\":\"sanket jasani\" } }";


        try {
            final JSONObject obj = new JSONObject(data).getJSONObject("follow_up");

            modelList = new ArrayList<>();
            modelList.add("Today");
            modelList.add("Tomorrow");

            List<FollowUp> today = new ArrayList<>();
            today.add(new FollowUp(obj));
            today.add(new FollowUp(obj));

            List<FollowUp> tomorrow = new ArrayList<>();
            tomorrow.add(new FollowUp(obj));
            tomorrow.add(new FollowUp(obj));

            modelListMap = new HashMap<>();
            modelListMap.put(modelList.get(0), today);
            modelListMap.put(modelList.get(1), tomorrow);

            expandableListAdapter = new ExpandableListAdapter(getActivity(), modelList, modelListMap);
            elvNotificationsListView.setAdapter(expandableListAdapter);

            for (int i = 0; i < expandableListAdapter.getGroupCount(); i++) {
                elvNotificationsListView.expandGroup(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "error parsing json", Toast.LENGTH_SHORT).show();
        }
    }


}
