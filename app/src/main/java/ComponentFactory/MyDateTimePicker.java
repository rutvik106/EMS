package ComponentFactory;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by ACER on 20-Feb-16.
 */
public class MyDateTimePicker extends MyTextBox
{


    public static enum PickerType
    {
        DATE_PICKER,
        TIME_PICKER
    }

    public DatePickerDialog dpd;

    public TimePickerDialog tpd;

    PickerType pickerType;

    Context context;

    FragmentManager fragmentManager;

    private String dialogTitle = "";

    DatePickerDialog.OnDateSetListener dateListener;
    TimePickerDialog.OnTimeSetListener timeListener;

    public void setDateTimeListeners(DatePickerDialog.OnDateSetListener dateListener, TimePickerDialog.OnTimeSetListener timeListener)
    {
        this.dateListener = dateListener;
        this.timeListener = timeListener;
    }

    public void setDialogTitle(String title)
    {
        dialogTitle = title;
    }

    public MyDateTimePicker(Context context, FragmentManager fragmentManager, PickerType pickerType)
    {
        super(context);
        this.context = context;
        this.pickerType = pickerType;
        this.fragmentManager = fragmentManager;

        editText.setTextIsSelectable(true);

        editText.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (b)
                {
                    showDialog();
                }
            }
        });

        editText.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showDialog();
            }
        });

    }

    public void showDialog()
    {
        Calendar now = Calendar.getInstance();
        if (pickerType == PickerType.DATE_PICKER)
        {
            dpd = DatePickerDialog.newInstance(dateListener, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            dpd.setAccentColor(Color.parseColor("#00695C"));
            dpd.setTitle(dialogTitle);
            dpd.show(fragmentManager, "Datepickerdialog");
        }
        if (pickerType == PickerType.TIME_PICKER)
        {
            tpd = TimePickerDialog.newInstance(timeListener, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), true);
            tpd.setAccentColor(Color.parseColor("#00695C"));
            tpd.setTitle(dialogTitle);
            tpd.show(fragmentManager, "Timepickerdialog");
        }
    }

}
