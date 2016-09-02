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
import android.widget.TextView;

import com.tapandtype.rutvik.ems.R;

import adapters.SimpleFormAdapter;
import extras.AppUtils;
import extras.Log;

/**
 * Created by rutvik on 06-07-2016 at 01:12 PM.
 */

public class SimpleFormFragment extends Fragment
{

    private static final String TAG = AppUtils.APP_TAG + SimpleFormFragment.class.getSimpleName();

    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private TextView tvFragTitle;

    SimpleFormAdapter adapter;

    String fragmentTitle="";

    //Map<Long, Component> formComponentMap;

    public SimpleFormFragment()
    {

    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.i(TAG, "ATTACHED");
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        Log.i(TAG, "DETACHED");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(TAG, "DESTROYED");
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Log.i(TAG, "VIEW DESTROYED");
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "SAVE INSTANCE CALLED!");

    }

    public void setData(SimpleFormAdapter adapter,String fragmentTitle)
    {

        Log.i(TAG, "SETTING STUDENT DATA ADAPTER");

        this.adapter = adapter;
        this.fragmentTitle=fragmentTitle;
        //this.formComponentMap=formComponentMap;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "ON CREATE CALLED!");

        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        Log.i(TAG, "ON CREATE VIEW CALLED!");

        View rootView = inflater.inflate(R.layout.simple_form_fragment, container, false);



        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_studentDetails);

        tvFragTitle=(TextView) rootView.findViewById(R.id.tv_fragTitle);

        tvFragTitle.setText(fragmentTitle);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        recyclerView.setNestedScrollingEnabled(false);

        return rootView;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "ON ACTIVITY CREATED");
    }

}
