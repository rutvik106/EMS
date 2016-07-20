package models;

/**
 * Created by rutvik on 18-04-2016 at 03:29 PM.
 */
public class SingleCheckListItem {

    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    String name;

    boolean isChecked=false;

    public SingleCheckListItem(String id,String name){
        this.id=id;
        this.name=name;
    }

}
