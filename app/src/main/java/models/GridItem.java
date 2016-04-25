package models;

/**
 * Created by rutvik on 25-04-2016 at 02:55 PM.
 */
public class GridItem {


    String label;
    int image;

    public GridItem(String label, int image) {
        this.label = label;
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public int getImage() {
        return image;
    }


}
