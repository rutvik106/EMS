package viewholders;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import extras.AppUtils;
import extras.Log;

import android.view.View;
import android.widget.AdapterView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import extras.PostServiceHandler;
import ComponentFactory.Component;
import ComponentFactory.MySpinner;
import ComponentFactory.RowItem;

/**
 * Created by ACER on 22-Mar-16 at 8:46 AM.
 */
public class DependentSpinnerVH extends RecyclerView.ViewHolder implements RowItem, SpinnerVH.ParentSpinner.ParentListener, AdapterView.OnItemSelectedListener
{

    private static final String TAG = AppUtils.APP_TAG + DependentSpinnerVH.class.getSimpleName();

    HashMap<String, String> componentDataMap = new HashMap<>();

    MySpinner mySpinner;

    private String JSONName;

    private String JSONKey;

    private String JSONValue;

    private String url;

    private SpinnerVH.MySpinnerBaseAdapter baseAdapter;

    Map map;

    Component parentComponent;

    //private ArrayList<SpinnerVH.SpinnerData> data = new ArrayList<>();

    private final Context context;

    private SpinnerVH.ParentSpinner.ParentListener listener;

    public DependentSpinnerVH(MySpinner mySpinner, Context context)
    {
        super(mySpinner);
        this.context = context;
        this.mySpinner = mySpinner;
        this.mySpinner.spinner.setOnItemSelectedListener(this);
        this.mySpinner.spinner.setEnabled(false);
    }

    public static DependentSpinnerVH create(Context context)
    {
        DependentSpinnerVH vh = new DependentSpinnerVH(new MySpinner(context), context);
        vh.baseAdapter = new SpinnerVH.MySpinnerBaseAdapter(context);
        vh.mySpinner.spinner.setAdapter(vh.baseAdapter);
        return vh;
    }

    public void setParentRowItem(Component parentComponent)
    {
        Log.i(TAG, "SETTING PARENT ROW ITEM AND ITS LISTENER");
        this.parentComponent = parentComponent;
        if (this.parentComponent.getRowItem().getComponentType() == Component.SPINNER)
        {
            ((SpinnerVH) this.parentComponent.getRowItem().getView()).setListener(this);
        } else if (this.parentComponent.getRowItem().getComponentType() == Component.DEPENDENT_SPINNER)
        {
            ((DependentSpinnerVH) this.parentComponent.getRowItem().getView()).setListener(this);
        }
    }

    public static void bind(DependentSpinnerVH vh, Map map, Component component, Component parentComponent)
    {

        vh.map = map;
        //vh.componentDataMap = (HashMap<String, String>) map.get("component_data");

        vh.setParentRowItem(parentComponent);

        //vh.setParentRowItem(parentComponent);

        Log.i(TAG, "json name: " + map.get("json_name"));
        Log.i(TAG, "json key: " + map.get("json_key"));
        Log.i(TAG, "json value: " + map.get("json_value"));

        vh.JSONName = (String) map.get("json_name");
        vh.JSONKey = (String) map.get("json_key");
        vh.JSONValue = (String) map.get("json_value");
        vh.url = (String) map.get("url");

        //vh.mySpinner.setAdapterArray(new ArrayList<>(vh.componentDataMap.values()));
        vh.mySpinner.spinner.setSelection(vh.baseAdapter.getSelection());
        vh.mySpinner.setTitle(vh.map.get("label").toString());
        component.setRowItem(vh);
    }

    public void setListener(SpinnerVH.ParentSpinner.ParentListener listener)
    {
        this.listener = listener;
    }

    public void setComponentDataMap(HashMap<String, String> map)
    {
        componentDataMap = map;
        mySpinner.setAdapterArray(new ArrayList<>(componentDataMap.values()));
    }

    @Override
    public Object getValue()
    {
        /*Log.i(TAG, "GETTING SPINNER VALUE ID...");
        Log.i(TAG, "ITERATING THROUGH HASH MAP...");
        //Log.i(TAG, "SELECTED ITEM: " + mySpinner.spinner.getSelectedItem().toString());

        if(mySpinner.spinner.getSelectedItem()!=null){
            Log.i(TAG,"SELECTED ITEM: "+mySpinner.spinner.getSelectedItem().toString());
            return mySpinner.spinner.getSelectedItem();
        }

        return new SpinnerVH.SpinnerData("0","none");*/

        return map.get("value").toString();

        /*for (Map.Entry<String, String> e : componentDataMap.entrySet()) {
            Log.i(TAG, "KEY: " + e.getKey() + " VALUE: " + e.getValue());
            if (e.getValue().equals(mySpinner.spinner.getSelectedItem().toString())) {
                return e;
            }
        }
        Map.Entry<String,String> e=new Map.Entry<String, String>() {
            @Override
            public String getKey() {
                return "none";
            }

            @Override
            public String getValue() {
                return "none";
            }

            @Override
            public String setValue(String s) {
                return null;
            }
        };

        return e;*/
    }


    @Override
    public void setValue(Object object)
    {
        mySpinner.spinner.setSelection(mySpinner.getAdapter().getPosition(object.toString()));
    }


    @Override
    public DependentSpinnerVH getView()
    {
        return this;
    }

    @Override
    public int getComponentType()
    {
        return Component.DEPENDENT_SPINNER;
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        Log.i(TAG, "ITEM CHANGED ON DEPENDENT SPINNER");
        baseAdapter.setSelection(i);
        map.put("value", ((SpinnerVH.SpinnerData) adapterView.getItemAtPosition(i)).getId());
        if (listener != null)
        {
            listener.onParentChanged(((SpinnerVH.SpinnerData) adapterView.getItemAtPosition(i)).getId());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    @Override
    public void onParentChanged(final String id)
    {

        Log.i(TAG, "ITEM CHANGED ON PARENT...");

        //Log.i(TAG, "int i: " + i);

        //Log.i(TAG, "long l: " + l);

        //Log.i(TAG, "SELECTED ITEM: " + ((SpinnerVH.SpinnerData) adapterView.getItemAtPosition(i)).getValue());


        //final String id = ((SpinnerVH.SpinnerData) adapterView.getItemAtPosition(i)).getId();

        //((Map) parentComponent.getObject()).put("value", id);

        //((SpinnerVH.MySpinnerBaseAdapter) adapterView.getAdapter()).setSelection(i);

        Log.i(TAG, "ID: " + id);

        new AsyncTask<Void, Void, Void>()
        {
            String response = "";

            @Override
            protected Void doInBackground(Void... voids)
            {
                try
                {
                    response = new PostServiceHandler(TAG, 2, 2000)
                            .doGet(url + id);
                } catch (Exception e)
                {
                    Log.i(TAG, e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                try
                {
                    Log.i(TAG, "TRYING TO PARSE JSON RESPONSE...");
                    Log.i(TAG, "JSON NAME: " + getJSONName());
                    Log.i(TAG, "JSON KEY NAME: " + getJSONKey());
                    Log.i(TAG, "JSON VALUE NAME: " + getJSONValue());
                    JSONArray arr = new JSONObject(response).getJSONArray(getJSONName());

                    ArrayList<SpinnerVH.SpinnerData> data = new ArrayList<>();

                    for (int i = 0; i < arr.length(); i++)
                    {
                        JSONObject o = arr.getJSONObject(i);
                        //Log.i(TAG, "KEY: " + o.getString(getJSONKey()));
                        //Log.i(TAG, "VALUE: " + o.getString(getJSONValue()));
                        data.add(new SpinnerVH.SpinnerData(o.getString(getJSONKey()), o.getString(getJSONValue())));
                    }
                    Log.i(TAG, "CREATING BASE ADAPTER...");
                    baseAdapter.setData(data);
                    Log.i(TAG, "NOW SETTING DEPENDENT SPINNER ADAPTER...");
                    mySpinner.spinner.setEnabled(true);
                    baseAdapter.notifyDataSetChanged();

                } catch (JSONException e)
                {
                    Log.i(TAG, e.getMessage());
                }
            }
        }.execute();

    }
}
