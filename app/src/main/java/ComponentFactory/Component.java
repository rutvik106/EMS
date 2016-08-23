package ComponentFactory;

/**
 * Created by rutvik on 06-07-2016 at 12:54 PM.
 */

public class Component<T>
{

    public static final int TEXTBOX = 0;
    public static final int SPINNER = 1;
    public static final int CHECKLIST = 2;
    public static final int DATEPICKER = 3;
    public static final int TIMEPICKER = 4;
    public static final int DEPENDENT_SPINNER = 5;
    public static final int APPENDABLE_TEXT_BOX = 6;
    public static final int INQUIRY_PRODUCT_DETAILS = 7;
    public static final int SIMPLE_TEXT_VIEW = 8;
    public static final int FOLLOW_UP_SEPARATOR = 9;

    final long id;


    RowItem item;

    public int getViewType()
    {
        return viewType;
    }

    public int viewType;

    public T getObject()
    {
        return object;
    }

    public T object;

    public Component(T object, int viewType, long id)
    {
        this.object = object;
        this.viewType = viewType;
        this.id = id;
    }

    public void setRowItem(RowItem rowItem)
    {
        item = rowItem;
    }

    public RowItem getRowItem()
    {
        return item;
    }

    public long getId()
    {
        return id;
    }


}
