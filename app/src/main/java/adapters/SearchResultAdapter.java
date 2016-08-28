package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import models.SearchResultItem;
import viewholders.SearchResultItemVH;

/**
 * Created by rutvik on 28-08-2016 at 09:52 AM.
 */

public class SearchResultAdapter extends RecyclerView.Adapter
{

    List<SearchResultItem> searchResultItemList;

    final Context context;

    public SearchResultAdapter(final Context context)
    {
        this.context = context;
        searchResultItemList = new ArrayList<>();
    }

    public void addSearchResultItem(SearchResultItem item)
    {
        searchResultItemList.add(item);
        notifyItemInserted(searchResultItemList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return SearchResultItemVH.create(context, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        SearchResultItemVH.bind((SearchResultItemVH) holder, searchResultItemList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return searchResultItemList.size();
    }
}
