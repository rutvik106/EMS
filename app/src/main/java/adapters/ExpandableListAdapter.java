package adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.rutvik.ems.R;

import java.util.List;
import java.util.Map;

import extras.AppUtils;
import extras.Log;
import models.FollowUp;

/**
 * Created by rutvik on 26-04-2016 at 01:11 PM.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private static final String TAG = AppUtils.APP_TAG + ExpandableListAdapter.class.getSimpleName();

    Context context;

    private List<String> modelList;

    private Map<String, List<FollowUp>> modelListMap;

    public ExpandableListAdapter(Context context, List<String> modelList,
                                 Map<String, List<FollowUp>> modelListMap) {
        Log.i(TAG, "Creating Expandable List View Adapter");
        this.context = context;
        this.modelList = modelList;
        this.modelListMap = modelListMap;
    }

    @Override
    public int getGroupCount() {
        Log.i(TAG, "Group Count: " + modelList.size());
        return modelList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.i(TAG, "Child Count: " + modelListMap.get(modelList.get(groupPosition)).size());
        return modelListMap.get(modelList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        Log.i(TAG,"getting group at position: "+groupPosition+" group: "+modelList.get(groupPosition));
        return modelList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return modelListMap.get(modelList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Log.i(TAG,"inside getGroupView");

        String title = (String) getGroup(groupPosition);

        if (convertView == null) {
            Log.i(TAG,"group view is null inflating layout");
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_list_group, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tv_elvTitle);
        tv.setText(title);
        tv.setTypeface(null, Typeface.BOLD);

        return convertView;
    }

    class FollowUpsViewHolder {
        TextView tvTitle, tvName, tvdiscussionsContent;

        CardView cv;

        public FollowUpsViewHolder(View v) {
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            tvName = (TextView) v.findViewById(R.id.tv_name);
            tvdiscussionsContent = (TextView) v.findViewById(R.id.tv_discussionsContent);
            cv = (CardView) v.findViewById(R.id.cv_notificationItem);
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Log.i(TAG,"inside getChildView");

        final FollowUp followUp = (FollowUp) getChild(groupPosition, childPosition);

        FollowUpsViewHolder followUpsViewHolder = null;

        if (convertView == null) {
            Log.i(TAG,"child view is null inflating layout");
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_list_item, null);
            followUpsViewHolder = new FollowUpsViewHolder(convertView);
            convertView.setTag(followUpsViewHolder);
        } else {
            followUpsViewHolder = (FollowUpsViewHolder) convertView.getTag();
        }

        followUpsViewHolder.tvTitle.setText(followUp.getFollowUpDate());
        followUpsViewHolder.tvName.setText(followUp.getName());
        followUpsViewHolder.tvdiscussionsContent.setText(followUp.getDiscussion());


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
