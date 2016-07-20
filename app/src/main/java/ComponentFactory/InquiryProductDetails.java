package ComponentFactory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.rutvik.ems.R;

import java.util.ArrayList;

import extras.AppUtils;
import extras.Log;

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

    ArrayList<AnotherProductDetails> anotherProductDetailsArrayList = new ArrayList<>();

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
        params.gravity=Gravity.RIGHT;
        btnAddAnotherProduct.setLayoutParams(params);
        btnAddAnotherProduct.setText("+ Add Another Product");
        btnAddAnotherProduct.setTextColor(Color.WHITE);
        btnAddAnotherProduct.setGravity(Gravity.CENTER);
        btnAddAnotherProduct.setOnClickListener(this);

        this.addView(btnAddAnotherProduct);

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
        this.addView(ap,this.getChildCount()-1);
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

}
