package ComponentFactory;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tapandtype.rutvik.ems.R;

/**
 * Created by ACER on 03-Feb-16.
 */
public class MyTextBox extends TextInputLayout
{

    public EditText editText;


    public MyTextBox(Context context)
    {

        super(context);

        this.setOrientation(VERTICAL);

        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.my_text_box, this, true);

        editText = (EditText) getChildAt(0);

    }


}
