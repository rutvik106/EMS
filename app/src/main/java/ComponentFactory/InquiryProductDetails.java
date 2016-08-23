package ComponentFactory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.rutvik.ems.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adapters.DropdownProductAdapter;
import extras.AppUtils;
import extras.Log;
import extras.PostServiceHandler;
import jsonobject.DropdownProduct;
import viewholders.SpinnerVH;

/**
 * Created by rutvik on 09-07-2016 at 04:01 PM.
 */

public class InquiryProductDetails extends LinearLayout implements View.OnClickListener
{

    public static final String TAG = AppUtils.APP_TAG + InquiryProductDetails.class.getSimpleName();

    Context context;

    AutoCompleteTextView actProduct;
    EditText etProductPrice;
    Spinner spinUnit;
    SpinnerVH.MySpinnerBaseAdapter unitSpinnerAdapter;
    EditText etInquiryProductQuantity;
    LinearLayout llProductAttributes;
    Button btnAddAnotherProduct;

    ArrayList<SpinnerVH.SpinnerData> spinnerDataList=new ArrayList<>();

    DropdownProductAdapter adapter;

    ArrayList<AnotherProductDetails> anotherProductDetailsArrayList = new ArrayList<>();

    GetDropdownDataAsync getDropdownDataAsync;

    OnProductSelected onProductSelectedListener;

    PopulateAttributeView populateAttributeView;

    String selectedUnitId = "";

    Map<String, String> unitSpinnerData;

    public InquiryProductDetails(Context context)
    {
        super(context);
        this.context = context;

        this.setOrientation(VERTICAL);
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        View view = LayoutInflater.from(context).inflate(R.layout.inquiry_product_details, null, false);

        if (view != null)
        {
            actProduct = (AutoCompleteTextView) view.findViewById(R.id.act_products);

            etProductPrice = (EditText) view.findViewById(R.id.et_inquiryProductPrice);
            spinUnit = (Spinner) view.findViewById(R.id.spin_productPricePer);
            spinUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Log.i(TAG, "UNIT SPINNER SELECTED ITEM ID: " + ((SpinnerVH.MySpinnerBaseAdapter) adapterView.getAdapter()).getItem(i).getId());
                    selectedUnitId = String.valueOf(((SpinnerVH.MySpinnerBaseAdapter) adapterView.getAdapter()).getItem(i).getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView)
                {

                }
            });
            unitSpinnerAdapter = new SpinnerVH.MySpinnerBaseAdapter(context);
            spinUnit.setAdapter(unitSpinnerAdapter);


            etInquiryProductQuantity = (EditText) view.findViewById(R.id.et_inquiryProductQuantity);
            llProductAttributes = (LinearLayout) view.findViewById(R.id.ll_productAttributes);

            populateAttributeView = new PopulateAttributeView(context, llProductAttributes);

            onProductSelectedListener = new OnProductSelected(context, populateAttributeView);

            actProduct.setOnItemClickListener(onProductSelectedListener);
        }

        this.addView(view);

        btnAddAnotherProduct = new Button(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        btnAddAnotherProduct.setLayoutParams(params);
        btnAddAnotherProduct.setText("+ Add Another Product");
        btnAddAnotherProduct.setTextColor(Color.WHITE);
        btnAddAnotherProduct.setGravity(Gravity.CENTER);
        btnAddAnotherProduct.setOnClickListener(this);

        this.addView(btnAddAnotherProduct);

        String url = PreferenceManager.getDefaultSharedPreferences(context).getString("host", "");
        if (!url.isEmpty())
        {
            url = url + AppUtils.URL_WEBSERVICE;
        }
        String sessionId = PreferenceManager.getDefaultSharedPreferences(context).getString("session_id", "");

        if (!url.isEmpty() && !sessionId.isEmpty())
        {
            getDropdownDataAsync = new GetDropdownDataAsync(context,
                    url, sessionId, actProduct, adapter);
            getDropdownDataAsync.execute();
        }


    }

    public Map<String, String> getProductDetailsValues()
    {
        Log.i(TAG, "COLLECTING VALUES FOR PRODUCT DETAILS");

        Map<String, String> productDetailMap = new HashMap<>();

        JSONArray product_id = new JSONArray();
        product_id.put(onProductSelectedListener.selectedSubCatId);

        JSONArray mrp = new JSONArray();
        mrp.put(etProductPrice.getText());

        JSONArray unit_id = new JSONArray();
        unit_id.put(selectedUnitId);

        JSONArray quantity_id = new JSONArray();
        quantity_id.put(etInquiryProductQuantity.getText());

        JSONObject attr = new JSONObject();

        try
        {
            JSONObject attribut_details = new JSONObject();
            for (SpinnerView sv : populateAttributeView.attributeSpinList)
            {
                JSONArray attribute_value = new JSONArray();
                attribute_value.put(sv.selectedItemId);
                attribut_details.put(sv.id, attribute_value);
            }
            attr.put(onProductSelectedListener.selectedSubCatId, attribut_details);
            Log.i(TAG, "attr: " + attr.toString());
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        for (AnotherProductDetails p : anotherProductDetailsArrayList)
        {
            try
            {
                JSONObject attribut_details = new JSONObject();
                for (SpinnerView sv : p.populateAttributeView.attributeSpinList)
                {
                    JSONArray attribute_value = new JSONArray();
                    attribute_value.put(sv.selectedItemId);
                    attribut_details.put(sv.id, attribute_value);
                }
                attr.put(p.onProductSelectedListener.selectedSubCatId, attribut_details);
                Log.i(TAG, "attr: " + attr.toString());
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            Log.i(TAG, "APPENDED TEXT BOX VALUE: " + p.onProductSelectedListener.selectedSubCatId);
            product_id.put(p.onProductSelectedListener.selectedSubCatId);

            Log.i(TAG, "APPENDED TEXT BOX VALUE: " + p.etProductPrice.getText());
            mrp.put(p.etProductPrice.getText());

            Log.i(TAG, "APPENDED TEXT BOX VALUE: " + p.selectedUnitId);
            unit_id.put(p.selectedUnitId);

            Log.i(TAG, "APPENDED TEXT BOX VALUE: " + p.etInquiryProductQuantity.getText());
            quantity_id.put(p.etInquiryProductQuantity.getText());


        }


        /*for (AnotherProductDetails p : anotherProductDetailsArrayList)
        {
            Log.i(TAG, "APPENDED TEXT BOX VALUE: " + p.onProductSelectedListener.selectedSubCatId);
            product_id.put(p.onProductSelectedListener.selectedSubCatId);
        }*/
        productDetailMap.put("product_id", product_id.toString());


        /*for (AnotherProductDetails p : anotherProductDetailsArrayList)
        {
            Log.i(TAG, "APPENDED TEXT BOX VALUE: " + p.etProductPrice.getText());
            mrp.put(p.etProductPrice.getText());
        }*/
        productDetailMap.put("mrp", mrp.toString());


        /*for (AnotherProductDetails p : anotherProductDetailsArrayList)
        {
            Log.i(TAG, "APPENDED TEXT BOX VALUE: " + p.selectedUnitId);
            unit_id.put(p.selectedUnitId);
        }*/
        productDetailMap.put("unit_id", unit_id.toString());


        /*for (AnotherProductDetails p : anotherProductDetailsArrayList)
        {
            Log.i(TAG, "APPENDED TEXT BOX VALUE: " + p.etInquiryProductQuantity.getText());
            quantity_id.put(p.etInquiryProductQuantity.getText());
        }*/
        productDetailMap.put("quantity_id", quantity_id.toString());

        productDetailMap.put("attribute_name_array", attr.toString());

        Log.i(TAG, "PRODUCT DETAIL MAP DATA: " + productDetailMap.toString());

        return productDetailMap;
    }

    public void setSpinnerAdapter(Map<String, String> data)
    {
        unitSpinnerData=data;
        Set<Map.Entry<String, String>> key = data.entrySet();
        for (Map.Entry<String, String> e : key)
        {
            spinnerDataList.add(new SpinnerVH.SpinnerData(e.getKey(), e.getValue()));
        }

        unitSpinnerAdapter.setData(spinnerDataList);

        unitSpinnerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view)
    {
        /**        if (anotherProductDetailsArrayList.size() == 0)
         {
         llAnotherProduct.setVisibility(VISIBLE);
         }*/
        AnotherProductDetails ap = new AnotherProductDetails(context,unitSpinnerData);
        anotherProductDetailsArrayList.add(ap);
        this.addView(ap, this.getChildCount() - 1);
    }

    class AnotherProductDetails extends InquiryProductDetails
    {

        public AnotherProductDetails(Context context, Map<String, String> unitSpinnerData)
        {
            super(context);
            btnAddAnotherProduct.setText("- Remove This Product");
            btnAddAnotherProduct.getBackground().setColorFilter(0xFFFF9800, PorterDuff.Mode.MULTIPLY);
            setSpinnerAdapter(unitSpinnerData);
        }

        @Override
        public void onClick(View view)
        {
            /**if(anotherProductDetailsArrayList.size()==1){
             llAnotherProduct.setVisibility(GONE);
             }*/
            Log.i(TAG, "REMOVING PRODUCT DETAILS");
            InquiryProductDetails.this.removeView(AnotherProductDetails.this);
            anotherProductDetailsArrayList.remove(AnotherProductDetails.this);
        }
    }


    static class OnProductSelected implements AdapterView.OnItemClickListener
    {
        private static final String TAG = AppUtils.APP_TAG + OnProductSelected.class.getSimpleName();

        Context context;

        PopulateAttributeView populateAttributeView;

        String selectedSubCatId;

        public OnProductSelected(Context context, PopulateAttributeView populateAttributeView)
        {
            Log.i(TAG, "CREATED ON PRODUCT SELECTED LISTENER");
            this.context = context;
            this.populateAttributeView = populateAttributeView;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            Log.i(TAG, "ITEM SELECTED INDEX IS: " + i);

            DropdownProductAdapter dp = (DropdownProductAdapter) adapterView.getAdapter();
            Log.i(TAG, "ITEM SELECTED ID IS: " + String.valueOf(dp.getItemId(i)));
            selectedSubCatId = String.valueOf(dp.getItemId(i));
            new GetAttributeAsync(String.valueOf(dp.getItemId(i)), context, populateAttributeView).execute();
        }
    }


    static class GetAttributeAsync extends AsyncTask<Void, Void, String>
    {

        private static final String TAG = AppUtils.APP_TAG + GetAttributeAsync.class.getSimpleName();

        String response;

        String subCatId;

        Context context;

        PopulateAttributeView populateAttributeView;

        public GetAttributeAsync(String subCatId, Context context, PopulateAttributeView populateAttributeView)
        {
            this.subCatId = subCatId;
            this.context = context;
            this.populateAttributeView = populateAttributeView;
        }

        @Override
        protected String doInBackground(Void... voids)
        {
            final String host = PreferenceManager.getDefaultSharedPreferences(context).getString("host", "");
            final String sessionId = PreferenceManager.getDefaultSharedPreferences(context).getString("session_id", "");
            if (!host.isEmpty() && !sessionId.isEmpty())
            {
                final Map<String, String> postParam = new HashMap<>();
                postParam.put("method", "get_attributes_from_subcat_id");
                postParam.put("session_id", sessionId);
                postParam.put("sub_cat_id", subCatId);

                new PostServiceHandler(TAG, 1, 1000).doPostRequest(host + AppUtils.URL_WEBSERVICE, postParam, new PostServiceHandler.ResponseCallback()
                {
                    @Override
                    public void response(int status, String response)
                    {
                        GetAttributeAsync.this.response = response;
                    }
                });
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s)
        {
            Log.i(TAG, "RESPONSE: " + s);
            if (!s.isEmpty())
            {
                populateAttributeView.populate(s);
            }
        }
    }

    static class PopulateAttributeView
    {
        List<SpinnerView> attributeSpinList = new ArrayList<>();

        LinearLayout llProductAttributes;

        Context context;

        public PopulateAttributeView(Context context, LinearLayout llProductAttributes)
        {
            this.llProductAttributes = llProductAttributes;
            this.context = context;
        }

        public void populate(String response)
        {
            Log.i(TAG, "POPULATING ATTRIBUTE VIEW");
            try
            {
                JSONArray arr = new JSONArray(response);
                if (arr.length() > 0)
                {
                    Log.i(TAG, "REMOVING/VISIBLE ATTRIBUTE VIEW AND");

                    llProductAttributes.setVisibility(VISIBLE);

                    llProductAttributes.removeAllViews();
                    attributeSpinList.clear();

                    for (int i = 0; i < arr.length(); i++)
                    {

                        JSONObject obj = arr.getJSONObject(i).getJSONObject("attribute_type");

                        JSONArray attributeNameArray = arr.getJSONObject(i).getJSONArray("attribute_name");

                        if (attributeNameArray.length() > 0)
                        {
                            Log.i(TAG, "GETTING ATTRIBUTE JSON");
                            ArrayList<SpinnerVH.SpinnerData> data = new ArrayList<>();
                            for (int j = 0; j < attributeNameArray.length(); j++)
                            {

                                JSONObject singleName = attributeNameArray.getJSONObject(j);

                                Log.i(TAG, "SPINNER DATA ID: " + singleName.getString("attribute_name_id"));
                                Log.i(TAG, "SPINNER DATA VALUES: " + singleName.getString("attribute_name"));

                                data.add(new SpinnerVH.
                                        SpinnerData(singleName.getString("attribute_name_id"),
                                        singleName.getString("attribute_name")));
                            }
                            SpinnerView sv = new SpinnerView(context, obj.getString("attribute_type"), obj.getString("attribute_type_id"), data);

                            attributeSpinList.add(sv);
                        }
                    }
                    for (SpinnerView sv : attributeSpinList)
                    {
                        Log.i(TAG, "ADDING ATTRIBUTE SPINNER TO VIEW");
                        llProductAttributes.addView(sv);
                    }
                }

            } catch (JSONException e)
            {

            }
        }

    }

    static class SpinnerView extends LinearLayout
    {
        MySpinner mySpinner;
        SpinnerVH.MySpinnerBaseAdapter adapter;

        ArrayList<SpinnerVH.SpinnerData> data;

        String id;

        String selectedItemId = "";

        public SpinnerView(Context context, String name, String id, ArrayList<SpinnerVH.SpinnerData> data)
        {
            super(context);
            mySpinner = new MySpinner(context);
            mySpinner.setTitle(name);
            adapter = new SpinnerVH.MySpinnerBaseAdapter(context);
            this.data = data;
            adapter.setData(this.data);
            mySpinner.spinner.setAdapter(adapter);
            mySpinner.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
                    selectedItemId = ((SpinnerVH.MySpinnerBaseAdapter) adapterView.getAdapter()).getItem(i).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView)
                {

                }
            });
            this.addView(mySpinner);
            this.id = id;

            adapter.notifyDataSetChanged();
            if (data == null)
            {
                Log.i(TAG, "DATA IS NULL");
            } else
            {
                Log.i(TAG, "DATA : " + data.toString());
            }
        }

    }

    static class GetDropdownDataAsync extends AsyncTask<Void, Void, String>
    {
        private static final String TAG = AppUtils.APP_TAG + GetDropdownDataAsync.class.getSimpleName();

        Context context;

        String url;
        String sessionId;

        String resp = "";
        AutoCompleteTextView act;

        DropdownProductAdapter adapter;

        public GetDropdownDataAsync(Context context, String url, String sessionId, AutoCompleteTextView act, DropdownProductAdapter adapter)
        {
            this.act = act;
            this.url = url;
            this.sessionId = sessionId;
            this.adapter = adapter;
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... voids)
        {

            final Map<String, String> postParam = new HashMap<>();
            postParam.put("method", "get_product_dropdown_data");
            postParam.put("session_id", sessionId);

            new PostServiceHandler(this.TAG, 2, 2000).doPostRequest(url, postParam, new PostServiceHandler.ResponseCallback()
            {
                @Override
                public void response(int status, String response)
                {
                    if (status == HttpURLConnection.HTTP_OK)
                    {
                        GetDropdownDataAsync.this.resp = response;
                    }
                }
            });

            return resp;
        }

        @Override
        protected void onPostExecute(String s)
        {
            if (!resp.equals(""))
            {
                try
                {
                    JSONArray arr = new JSONArray(resp);
                    if (arr.length() > 0)
                    {

                        adapter = new DropdownProductAdapter(context);

                        for (int i = 0; i < arr.length(); i++)
                        {
                            DropdownProduct dp = new DropdownProduct(arr.getJSONObject(i));
                            adapter.addDropdownListProduct(dp);
                        }

                        act.setAdapter(adapter);

                        act.setThreshold(1);

                        adapter.notifyDataSetChanged();

                    }

                } catch (JSONException e)
                {

                }
            }
        }
    }

}
