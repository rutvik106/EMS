package models;

import java.util.ArrayList;
import java.util.List;

import component.NotificationListItemComp;

/**
 * Created by rutvik on 27-04-2016 at 03:37 PM.
 */
public class NotificationHeader implements NotificationListItemComp{

    public interface HeaderExpandCollapseListener{
        void onExpandOrCollapse(NotificationHeader header);
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    boolean expand=true;

    public List<FollowUp> getFollowUpArrayList() {
        return followUpArrayList;
    }

    public void setFollowUpList(List<FollowUp> followUpArrayList) {
        this.followUpArrayList = followUpArrayList;
    }

    private List<FollowUp> followUpArrayList;



    public String getTitle() {
        return title;
    }

    public String getChildCount() {
        return childCount;
    }

    String title;
    String childCount;

    public NotificationHeader(String title,List<FollowUp> followUpArrayList){
        this.title=title;
        this.followUpArrayList=followUpArrayList;
        this.childCount=followUpArrayList.size()+"";
    }

    @Override
    public int getComponentType() {
        return Type.HEADER;
    }
}
