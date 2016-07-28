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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.rutvik.ems.App;
import com.example.rutvik.ems.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapters.DropdownProductAdapter;
import extras.AppUtils;
import extras.Log;
import extras.PostServiceHandler;
import jsonobject.DropdownProduct;

/**
 * Created by rutvik on 09-07-2016 at 04:01 PM.
 */

public class InquiryProductDetails extends LinearLayout implements View.OnClickListener
{

    public static final String TAG = AppUtils.APP_TAG + InquiryProductDetails.class.getSimpleName();

    Context context;

    AutoCompleteTextView actProduct;
    EditText etProductPrice;
    Spinner spinProductPer;
    EditText etInquiryProductQuantity;
    LinearLayout llProductAttributes;
    Button btnAddAnotherProduct;

    DropdownProductAdapter adapter;

    ArrayList<AnotherProductDetails> anotherProductDetailsArrayList = new ArrayList<>();

    GetDropdownDataAsync getDropdownDataAsync;

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
            spinProductPer = (Spinner) view.findViewById(R.id.spin_productPricePer);
            etInquiryProductQuantity = (EditText) view.findViewById(R.id.et_inquiryProductQuantity);
            llProductAttributes = (LinearLayout) view.findViewById(R.id.ll_productAttributes);
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

    @Override
    public void onClick(View view)
    {
        /**        if (anotherProductDetailsArrayList.size() == 0)
         {
         llAnotherProduct.setVisibility(VISIBLE);
         }*/
        AnotherProductDetails ap = new AnotherProductDetails(context);
        anotherProductDetailsArrayList.add(ap);
        this.addView(ap, this.getChildCount() - 1);
    }

    class AnotherProductDetails extends InquiryProductDetails
    {

        public AnotherProductDetails(Context context)
        {
            super(context);
            btnAddAnotherProduct.setText("- Remove This Product");
            btnAddAnotherProduct.getBackground().setColorFilter(0xFFFF9800, PorterDuff.Mode.MULTIPLY);
        }

        @Override
        public void onClick(View view)
        {
            /**            if(anotherProductDetailsArrayList.size()==1){
             llAnotherProduct.setVisibility(GONE);
             }*/
            Log.i(TAG, "REMOVING PRODUCT DETAILS");
            InquiryProductDetails.this.removeView(AnotherProductDetails.this);
            anotherProductDetailsArrayList.remove(AnotherProductDetails.this);
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
            this.context=context;
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
