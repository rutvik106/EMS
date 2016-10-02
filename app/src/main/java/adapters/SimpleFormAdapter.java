package adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import ComponentFactory.AppendableTextBox;
import ComponentFactory.Component;
import ComponentFactory.MyDateTimePicker;
import ComponentFactory.RowItem;
import extras.AppUtils;
import extras.Log;
import models.FollowUpSeparator;
import viewholders.AppendableTextBoxVH;
import viewholders.CheckListSpinnerVH;
import viewholders.DateTimePickerVH;
import viewholders.DependentSpinnerVH;
import viewholders.FollowUpSeparatorVH;
import viewholders.InquiryProductDetailsVH;
import viewholders.SimpleTextViewVH;
import viewholders.SpinnerVH;
import viewholders.TextBoxVH;

/**
 * Created by rutvik on 06-07-2016 at 12:52 PM.
 */

public class SimpleFormAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    final Context context;

    public Map<Long, Component> getComponentListMap()
    {
        return componentListMap;
    }

    Map<Long, Component> componentListMap;

    Map<Long, Long> dependentOnList = new HashMap<>();

    final Activity activity;

    AppendableTextBox.OnUrlTriggered urlListener;

    public static final String TAG = AppUtils.APP_TAG + SimpleFormAdapter.class.getSimpleName();

    public SimpleFormAdapter(Activity activity)
    {
        this.context = activity;
        this.activity = activity;
        componentListMap = new HashMap<>();
    }

    public void addSimpleTextView(long id, String label, String value, View.OnClickListener onClickListener)
    {
        Map map = new HashMap();
        map.put("label", label);
        map.put("value", value);
        map.put("on_click_listener", onClickListener);
        componentListMap.put(id, new Component(map, Component.SIMPLE_TEXT_VIEW, id));
        notifyItemInserted(componentListMap.size());
    }

    public void addTextBox(String label, String name, long id, Integer inputType, Boolean enabled, String defaultText)
    {
        Map map = new HashMap();
        map.put("label", label);
        map.put("name", name);
        map.put("input_type", inputType);
        map.put("value", "");
        map.put("enabled", enabled);
        map.put("default_text", defaultText);
        componentListMap.put(id, new Component(map, Component.TEXTBOX, id));
        notifyItemInserted(componentListMap.size());
    }

    public void addDatePicker(String label, String name, long id, String dialogTitle)
    {
        Map map = new HashMap();
        map.put("label", label);
        map.put("name", name);
        map.put("value", "");
        map.put("title", dialogTitle);
        componentListMap.put(id, new Component(map, Component.DATEPICKER, id));
        notifyItemInserted(componentListMap.size());
    }

    public void addTimePicker(String label, String name, long id, String dialogTitle)
    {
        Map map = new HashMap();
        map.put("label", label);
        map.put("name", name);
        map.put("value", "");
        map.put("title", dialogTitle);
        componentListMap.put(id, new Component(map, Component.TIMEPICKER, id));
        notifyItemInserted(componentListMap.size());
    }

    public void addSpinner(String label, String name, Map componentData, long id)
    {
        Map map = new HashMap();
        map.put("label", label);
        map.put("name", name);
        map.put("component_data", componentData);
        map.put("value", "");
        componentListMap.put(id, new Component(map, Component.SPINNER, id));
        notifyItemInserted(componentListMap.size());
    }

    public void addDependentSpinner(String label, String name, long dependentOnId, long id, String JSONName, String JSONKey, String JSONValue, String url)
    {
        //Log.i(TAG,"adding dependent spinner...");
        Map map = new HashMap();
        map.put("name", name);
        map.put("label", label);
        map.put("json_name", JSONName);
        map.put("json_key", JSONKey);
        map.put("json_value", JSONValue);
        map.put("url", url);
        map.put("value", "");
        dependentOnList.put(id, dependentOnId);
        componentListMap.put(id, new Component(map, Component.DEPENDENT_SPINNER, id));
    }

    public void addCheckListSpinner(String label, String name, Map componentData, long id)
    {

        Map map = new HashMap();
        map.put("label", label);
        map.put("name", name);
        map.put("component_data", componentData);
        map.put("value", "");
        componentListMap.put(id, new Component(map, Component.CHECKLIST, id));
        notifyItemInserted(componentListMap.size());
    }

    public void addAppendableTextBox(String label, String name, long id,
                                     String triggerUrl,
                                     AppendableTextBox.OnUrlTriggered urlListener,
                                     int inputType)
    {
        this.urlListener = urlListener;
        Map map = new HashMap();
        map.put("label", label);
        map.put("name", name);
        map.put("trigger_url", triggerUrl);
        map.put("input_type", inputType);
        componentListMap.put(id, new Component(map, Component.APPENDABLE_TEXT_BOX, id));
        notifyItemInserted(componentListMap.size());
    }

    public void addInquiryProduct(long id, Map<String, String> perDataMap)
    {
        Map map = new HashMap();
        map.put("per", perDataMap);
        componentListMap.put(id, new Component(map, Component.INQUIRY_PRODUCT_DETAILS, id));
    }


    public void addFollowUpSeparator(long id, FollowUpSeparator followUpSeparator)
    {
        componentListMap.put(id, new Component(followUpSeparator, Component.FOLLOW_UP_SEPARATOR, id));
        notifyItemInserted(componentListMap.size());
    }

    @Override
    public int getItemViewType(int position)
    {
        //Log.i(TAG,"position: "+position);
        //Log.i(TAG,"component type: "+ componentListMap.get(Long.valueOf(position)).viewType);
        return componentListMap.get(Long.valueOf(position)).viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {


        //Log.i(TAG,"switch: "+i);

        switch (i)
        {
            case Component.TEXTBOX:
                //Log.i(TAG,"CREATING TEXTBOX");
                return TextBoxVH.create(context);

            case Component.SPINNER:
                //Log.i(TAG,"CREATING SPINNER");
                return SpinnerVH.create(context);

            case Component.CHECKLIST:
                //Log.i(TAG,"CREATING CHECKLIST");
                return CheckListSpinnerVH.create(context);

            case Component.DATEPICKER:
                //Log.i(TAG,"CREATING CHECKLIST");
                return DateTimePickerVH.create(context, activity.getFragmentManager(), MyDateTimePicker.PickerType.DATE_PICKER);

            case Component.TIMEPICKER:
                //Log.i(TAG,"CREATING CHECKLIST");
                return DateTimePickerVH.create(context, activity.getFragmentManager(), MyDateTimePicker.PickerType.TIME_PICKER);

            case Component.DEPENDENT_SPINNER:
                //Log.i(TAG,"CREATING DEPENDENT SPINNER");
                return DependentSpinnerVH.create(context);

            case Component.APPENDABLE_TEXT_BOX:
                //Log.i(TAG,"CREATING DEPENDENT SPINNER");
                return AppendableTextBoxVH.create(context, urlListener);

            case Component.INQUIRY_PRODUCT_DETAILS:
                return InquiryProductDetailsVH.create(context);

            case Component.SIMPLE_TEXT_VIEW:
                return SimpleTextViewVH.create(context, viewGroup);

            case Component.FOLLOW_UP_SEPARATOR:
                return FollowUpSeparatorVH.create(context, viewGroup);

        }

        return null;

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i)
    {

        //viewHolder.getLinearLayout().removeView(rowItems.get(i).getView());
        //viewHolder.getLinearLayout().addView(rowItems.get(i).getView());

        Log.i(TAG, "inside onBindViewHolder");

        switch (getItemViewType(i))
        {
            case Component.TEXTBOX:
                Log.i(TAG, "BINDING DATA TO TEXTBOX...");
                TextBoxVH.bind((TextBoxVH) viewHolder,
                        (Map) componentListMap.get(Long.valueOf(i)).object,
                        componentListMap.get(Long.valueOf(i)));
                break;

            case Component.DATEPICKER:
                Log.i(TAG, "BINDING DATA TO DATEPICKER...");
                DateTimePickerVH.bind((DateTimePickerVH) viewHolder,
                        (Map) componentListMap.get(Long.valueOf(i)).object,
                        componentListMap.get(Long.valueOf(i)));
                break;

            case Component.TIMEPICKER:
                Log.i(TAG, "BINDING DATA TO DATEPICKER...");
                DateTimePickerVH.bind((DateTimePickerVH) viewHolder,
                        (Map) componentListMap.get(Long.valueOf(i)).object,
                        componentListMap.get(Long.valueOf(i)));
                break;

            case Component.SPINNER:
                Log.i(TAG, "BINDING DATA TO SPINNER...");
                SpinnerVH.bind((SpinnerVH) viewHolder,
                        (Map) componentListMap.get(Long.valueOf(i)).object,
                        (RowItem) componentListMap.get(dependentOnList.get(((Map) componentListMap.get("title")))),
                        componentListMap.get(Long.valueOf(i)));
                break;

            case Component.CHECKLIST:
                Log.i(TAG, "BINDING DATA TO CHECKLIST...");
                CheckListSpinnerVH.bind((CheckListSpinnerVH) viewHolder,
                        (Map) componentListMap.get(Long.valueOf(i)).object,
                        componentListMap.get(Long.valueOf(i)));
                break;

            case Component.DEPENDENT_SPINNER:
                Log.i(TAG, "BINDING DATA TO DEPENDENT SPINNER...");
                DependentSpinnerVH.bind((DependentSpinnerVH) viewHolder,
                        (Map) componentListMap.get(Long.valueOf(i)).object,
                        componentListMap.get(Long.valueOf(i)),
                        componentListMap.get(dependentOnList.get(Long.valueOf(i)))
                );

                break;

            case Component.APPENDABLE_TEXT_BOX:
                Log.i(TAG, "BINDING DATA TO APPENDABLE TEXT BOX...");
                AppendableTextBoxVH.bind((AppendableTextBoxVH) viewHolder,
                        (Map) componentListMap.get(Long.valueOf(i)).object,
                        componentListMap.get(Long.valueOf(i))
                );

                break;

            case Component.INQUIRY_PRODUCT_DETAILS:
                Log.i(TAG, "BINDING DATA TO INQUIRYPRODUCT DETAILS");
                InquiryProductDetailsVH.bind((InquiryProductDetailsVH) viewHolder,
                        (Map) componentListMap.get(Long.valueOf(i)).object,
                        componentListMap.get(Long.valueOf(i)));
                break;

            case Component.SIMPLE_TEXT_VIEW:
                Log.i(TAG, "BINDING DATA TO SIMPLE TEXT VIEW");
                SimpleTextViewVH.bind((SimpleTextViewVH) viewHolder,
                        componentListMap.get(Long.valueOf(i)));
                break;

            case Component.FOLLOW_UP_SEPARATOR:
                FollowUpSeparatorVH.bind((FollowUpSeparatorVH) viewHolder,
                        (FollowUpSeparator) componentListMap.get(Long.valueOf(i)).getObject());
                break;
        }

    }

    @Override
    public long getItemId(int position)
    {
        return componentListMap.get(Long.valueOf(position)).getId();
    }

    @Override
    public int getItemCount()
    {
        return componentListMap.size();
    }

}
