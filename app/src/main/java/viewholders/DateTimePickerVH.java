package viewholders;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.rutvik.ems.R;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.util.Map;

import ComponentFactory.Component;
import ComponentFactory.MyDateTimePicker;
import ComponentFactory.RowItem;

/**
 * Created by ACER on 20-Feb-16.
 */
public class DateTimePickerVH extends RecyclerView.ViewHolder implements RowItem, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener, com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener
{

    MyDateTimePicker myDateTimePicker;

    Map map;

    public DateTimePickerVH(MyDateTimePicker myDateTimePicker)
    {
        super(myDateTimePicker);
        this.myDateTimePicker = myDateTimePicker;
        this.myDateTimePicker.setDateTimeListeners(this, this);
    }

    public static DateTimePickerVH create(Context context, FragmentManager fragmentManager, MyDateTimePicker.PickerType type)
    {
        return new DateTimePickerVH(new MyDateTimePicker(context, fragmentManager, type));
    }

    public static void bind(DateTimePickerVH vh, Map map, Component component)
    {
        vh.map = map;
/**        vh.label = (String) vh.map.get("label");
        vh.name = (String) vh.map.get("name");
        vh.value=(String) vh.map.get("value");*/
        vh.myDateTimePicker.setHint(vh.map.get("label").toString());
        vh.myDateTimePicker.setDialogTitle(vh.map.get("title").toString());
        component.setRowItem(vh);
    }


    @Override
    public Object getValue()
    {
        return map.get("value").toString();
    }

    @Override
    public void setValue(Object object)
    {
        myDateTimePicker.editText.setText(object.toString());
    }

    @Override
    public DateTimePickerVH getView()
    {
        return this;
    }

    @Override
    public int getComponentType()
    {
        return Component.DATEPICKER;
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
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int seconds)
    {
        String time = hourOfDay + ":" + minute + ":" + seconds;
        myDateTimePicker.editText.setText(time);
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        String month=String.valueOf(monthOfYear + 1);
        if(month.length()==1){
            month="0"+month;
        }

        String day=String.valueOf(dayOfMonth);
        if(day.length()==1){
            day="0"+day;
        }

        String date = day + "/" + month + "/" + year;
        map.put("value", date);
        myDateTimePicker.editText.setText(date);
    }
}
