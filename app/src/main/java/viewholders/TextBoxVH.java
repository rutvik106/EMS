package viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import extras.AppUtils;
import extras.Log;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;


import org.w3c.dom.Text;

import java.util.Map;

import adapters.SimpleFormAdapter;
import ComponentFactory.Component;
import ComponentFactory.MyTextBox;
import ComponentFactory.RowItem;

import static extras.CommonUtils.MANDATORY_FIELD;

/**
 * Created by ACER on 05-Feb-16.
 */
public class TextBoxVH extends RecyclerView.ViewHolder implements RowItem, TextWatcher
{

    MyTextBox myTextBox;

    public static final String TAG = AppUtils.APP_TAG + TextBoxVH.class.getSimpleName();

    Map map;

    long viewId = 0;

    public TextBoxVH(MyTextBox myTextBox)
    {
        super(myTextBox);
        this.myTextBox = myTextBox;
    }

    public static TextBoxVH create(Context context)
    {
        TextBoxVH vh = new TextBoxVH(new MyTextBox(context));
        vh.myTextBox.editText.addTextChangedListener(vh);
        return vh;
    }

    public void setInputType(int inputType)
    {

    }

    public static void bind(TextBoxVH vh, Map map, Component component)
    {
        Log.i(TAG, "HINT TEXT: " + map.get("label"));
        vh.map = map;
        vh.myTextBox.setHint(vh.map.get("label").toString());
        vh.myTextBox.editText.setInputType((Integer) map.get("input_type"));
        vh.myTextBox.editText.setText((String) map.get("value"));
        vh.myTextBox.editText.setEnabled((Boolean) map.get("enabled"));
        String defaultText = (String) map.get("default_text");
        vh.myTextBox.setIsMandatory((boolean) map.get("is_mandatory"));
        if (defaultText.length() > 0)
        {
            vh.myTextBox.editText.setText(defaultText);
        }
        component.setRowItem(vh);
        //vh.myTextBox.editText.text
    }

    @Override
    public String getValue()
    {
        if (myTextBox.isMandatory())
        {
            if (map.get("value").toString().trim().isEmpty())
            {
                return MANDATORY_FIELD;
            }
        }
        return map.get("value").toString().trim();
    }

    @Override
    public void setValue(Object object)
    {

        Log.i(TAG, "SETTING EDIT TEXT VALUE: " + object.toString());

        myTextBox.editText.setText(object.toString());
    }

    @Override
    public TextBoxVH getView()
    {
        return this;
    }

    @Override
    public int getComponentType()
    {
        return Component.TEXTBOX;
    }

    @Override
    public String getJSONName()
    {
        return null;
    }

    @Override
    public String getJSONKey()
    {
        return null;
    }

    @Override
    public String getJSONValue()
    {
        return null;
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


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        map.put("value", s.toString());
    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }
}
