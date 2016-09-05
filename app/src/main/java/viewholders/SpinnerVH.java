package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.tapandtype.rutvik.ems.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import extras.AppUtils;
import extras.Log;
import ComponentFactory.Component;
import ComponentFactory.MySpinner;
import ComponentFactory.RowItem;

/**
 * Created by ACER on 05-Feb-16.
 */
public class SpinnerVH extends RecyclerView.ViewHolder implements RowItem, AdapterView.OnItemSelectedListener
{

    private static final String TAG = AppUtils.APP_TAG + SpinnerVH.class.getSimpleName();

    HashMap<String, String> componentDataMap = new HashMap<>();

    Map map;

    RowItem dependentRowItem;

    MySpinner mySpinner;

    private String JSONName;

    private String JSONKey;

    private String JSONValue;

    private MySpinnerBaseAdapter baseAdapter;

    private Context context;

    private ParentSpinner.ParentListener listener;

    public static class ParentSpinner
    {
        public interface ParentListener
        {
            void onParentChanged(String id);
        }
    }

    public SpinnerVH(MySpinner mySpinner, Context context)
    {
        super(mySpinner);
        this.mySpinner = mySpinner;
        mySpinner.spinner.setOnItemSelectedListener(this);
        this.context = context;
    }

    public static SpinnerVH create(Context context)
    {
        MySpinner spinner = new MySpinner(context);
        SpinnerVH vh = new SpinnerVH(spinner, context);
        vh.baseAdapter = new MySpinnerBaseAdapter(context);
        return vh;
    }

    public static void bind(SpinnerVH vh, Map map, RowItem rowItem, Component component)
    {
        vh.map = map;
        vh.componentDataMap = (HashMap<String, String>) map.get("component_data");
        vh.dependentRowItem = rowItem;
        vh.JSONName = (String) vh.map.get("json_name");
        vh.JSONKey = (String) map.get("json_key");
        vh.JSONValue = (String) map.get("json_value");
        //Log.i(TAG,"gender_name: "+vh.componentDataMap.get("1"));
        ArrayList<SpinnerData> data = new ArrayList<>();
        if (vh.componentDataMap != null)
        {
            for (Map.Entry<String, String> e : vh.componentDataMap.entrySet())
            {
                data.add(new SpinnerData(e.getKey(), e.getValue()));
            }
        }
        vh.baseAdapter.setData(data);
        vh.mySpinner.spinner.setAdapter(vh.baseAdapter);
        //vh.mySpinner.spinner.setOnItemSelectedListener(vh.baseAdapter);
        vh.baseAdapter.setSelection(1);
        vh.mySpinner.spinner.setSelection(vh.baseAdapter.getSelection());

        vh.mySpinner.setTitle(vh.map.get("label").toString());
        component.setRowItem(vh);
    }

    public void setListener(ParentSpinner.ParentListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        Log.i(TAG, "OnITEM SELECTED VALUE: " + ((SpinnerData) parent.getItemAtPosition(position)).getValue()
                + " ID: " + ((SpinnerData) parent.getItemAtPosition(position)).getId());
        baseAdapter.setSelection(position);
        map.put("value", ((SpinnerData) parent.getItemAtPosition(position)).getId());
        if (listener != null)
        {
            listener.onParentChanged(((SpinnerData) parent.getItemAtPosition(position)).getId());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public static class SpinnerData
    {

        String id;
        String value;

        public SpinnerData(String id, String value)
        {
            this.id = id;
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }

        public String getId()
        {
            return id;
        }


    }

    public static class MySpinnerBaseAdapter extends BaseAdapter
    {

        final ArrayList<SpinnerData> data = new ArrayList<>();
        LayoutInflater inflater;
        Context context;

        private int selection = -1;

        public MySpinnerBaseAdapter(Context context)
        {
            Log.i(TAG, "Creating base adapter for spinner");
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void setData(ArrayList<SpinnerData> data)
        {
            this.data.clear();
            for (SpinnerData singleData : data)
            {
                this.data.add(singleData);
            }
        }

        @Override
        public int getCount()
        {
            return data.size();
        }

        @Override
        public SpinnerData getItem(int i)
        {
            return data.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return data.get(i).hashCode();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            if (view == null)
            {

                view = inflater.inflate(R.layout.single_spinner_row, viewGroup, false);
            }

            TextView tv = (TextView) view
                    .findViewById(android.R.id.text1);
            tv.setText(data.get(i).getValue());

            return view;
        }

        public int getSelection()
        {
            return selection;
        }

        public void setSelection(int i)
        {
            selection = i;
        }

    }

    public void setComponentDataMap(HashMap<String, String> map)
    {
        componentDataMap = map;
        mySpinner.setAdapterArray(new ArrayList<String>(componentDataMap.values()));
    }

    @Override
    public Object getValue()
    {

        return map.get("value").toString();

    }

    @Override
    public void setValue(Object object)
    {
        mySpinner.spinner.setSelection(mySpinner.getAdapter().getPosition(object.toString()));
    }

    @Override
    public SpinnerVH getView()
    {
        return this;
    }

    @Override
    public int getComponentType()
    {
        return Component.SPINNER;
    }

    @Override
    public String getJSONName()
    {
        return JSONName;
    }

    public String getJSONValue()
    {
        return JSONValue;
    }

    @Override
    public String getLabel()
    {
        return map.get("label").toString();
    }

    @Override
    public String getName()
    {
        return map.get("name").toString();
    }

    public String getJSONKey()
    {
        return JSONKey;
    }

    /*@Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mySpinner.getAdapter().getItem(i);
        Log.i(TAG, "GETTING SPINNER VALUE ID...");
        Log.i(TAG, "ITERATING THROUGH HASH MAP...");
        Log.i(TAG, "SELECTED ITEM: " + mySpinner.getAdapter().getItem(i).toString());
        for (Map.Entry<String, String> e : componentDataMap.entrySet()) {
            Log.i(TAG, "KEY: " + e.getKey() + " VALUE: " + e.getValue());
            if (e.getValue().equals(mySpinner.spinner.getSelectedItem().toString())) {
                final String id = e.getKey().toString();

                final RowItem ri=dependentRowItem;

                    new AsyncTask<Void, Void, Void>() {
                        String response = "";

                        @Override
                        protected Void doInBackground(Void... voids) {
                            try {
                                response = new PostServiceHandler(TAG, 2, 2000)
                                        .doGet("http://ictinfracon.com/STSAndroid/?type=brcdata&district_id=" + id);
                            } catch (Exception e) {
                                Log.i(TAG, e.getMessage());
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            try {
                                JSONArray arr = new JSONObject(response).getJSONArray(ri.getJSONName());
                                final HashMap<String, String> dataMap = new HashMap<String, String>();
                                for (int i = 0; i < arr.length(); i++) {
                                    JSONObject o = arr.getJSONObject(i);
                                    dataMap.put(o.getString(ri.getJSONKey()), o.getString(ri.getJSONValue()));
                                }
                                ri.setValue(dataMap);
                            } catch (JSONException e) {

                            }
                        }
                    }.execute();


            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/
}
