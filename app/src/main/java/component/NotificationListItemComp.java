package component;

/**
 * Created by rutvik on 27-04-2016 at 03:13 PM.
 */
public interface NotificationListItemComp {

    interface Type{
        int HEADER=0;
        int CHILD=1;
    }

    int getComponentType();

}
