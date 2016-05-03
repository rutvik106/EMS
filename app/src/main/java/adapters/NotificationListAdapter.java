package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import component.NotificationListItemComp;
import extras.AppUtils;
import models.FollowUp;
import models.NotificationHeader;
import viewholders.NotificationListChildVH;
import viewholders.NotificationListHeaderVH;

/**
 * Created by rutvik on 27-04-2016 at 03:08 PM.
 */
public class NotificationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NotificationHeader.HeaderExpandCollapseListener {

    private static final String TAG = AppUtils.APP_TAG + NotificationListAdapter.class.getSimpleName();

    Context context;

    List modelList;

    List<NotificationHeader> headerModelList;


    public NotificationListAdapter(Context context, List<NotificationHeader> headerModelList) {
        this.context = context;
        this.headerModelList = headerModelList;
        this.modelList=new LinkedList();
        for(NotificationHeader h:headerModelList){
            modelList.add(h);
            for(FollowUp f:h.getFollowUpArrayList()){
                modelList.add(f);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        Log.i(TAG, "COMPONENT TYPE: " + ((NotificationListItemComp) modelList.get(position)).getComponentType());
        return ((NotificationListItemComp) modelList.get(position)).getComponentType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.i(TAG,"inside on create view holder");

        Log.i(TAG,"view type: "+viewType);

        switch (viewType) {

            case NotificationListItemComp.Type.HEADER:
                Log.i(TAG,"creating header");

                return NotificationListHeaderVH.create(context,parent,this);

            case NotificationListItemComp.Type.CHILD:
                Log.i(TAG,"creating child");
                return NotificationListChildVH.create(context,parent);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.i(TAG,"inside on bind");

        Log.i(TAG,"position: "+position);

        switch (getItemViewType(position)) {

            case NotificationListItemComp.Type.HEADER:
                Log.i(TAG,"binding header");
                NotificationListHeaderVH.bind((NotificationListHeaderVH) holder, (NotificationHeader) modelList.get(position));
                break;

            case NotificationListItemComp.Type.CHILD:
                Log.i(TAG,"binding child");
                NotificationListChildVH.bind((NotificationListChildVH) holder, (FollowUp) modelList.get(position));
                break;

        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public void onExpandOrCollapse(NotificationHeader header) {

        Log.i(TAG,"on expanded called (button clicked)");

        if(header.isExpand()){
            for (FollowUp f: header.getFollowUpArrayList()){
                f.setRender(false);
            }
        }else{
            for (FollowUp f: header.getFollowUpArrayList()){
                f.setRender(true);
            }
        }

        header.setExpand(!header.isExpand());

        notifyDataSetChanged();
    }

}
