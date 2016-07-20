package ComponentFactory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ListView;



import java.util.ArrayList;

import extras.Log;
import extras.AppUtils;
import models.SingleCheckListItem;

/**
 * Created by rutvik on 18-04-2016 at 04:31 PM.
 */
public class CheckListDialog {

    private static final String TAG = AppUtils.APP_TAG + CheckListDialog.class.getSimpleName();

    Context context;

    ArrayList<SingleCheckListItem> singleCheckListItemList;

    CheckListDialogListener listener;

    public static interface CheckListDialogListener {
        void onResult(String data);
        void onNothingSelected();
    }

    public CheckListDialog(Context context, ArrayList<SingleCheckListItem> singleCheckListItemList, CheckListDialogListener listener) {
        this.context = context;
        this.singleCheckListItemList = singleCheckListItemList;
        this.listener = listener;
    }

    public ArrayList<String> getNames() {
        final ArrayList<String> lst = new ArrayList<>();
        for (SingleCheckListItem item : singleCheckListItemList) {
            lst.add(item.getName());
        }

        return lst;

    }

    public void show(String title) {
        // Intialize  readable sequence of char values
        final CharSequence[] dialogList = getNames().toArray(new CharSequence[getNames().size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(context);
        builderDialog.setTitle("Select "+title);
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count]; // set is_checked boolean false;

        for(int i=0;i<singleCheckListItemList.size();i++){
            is_checked[i]=singleCheckListItemList.get(i).isChecked();
        }

        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setMultiChoiceItems(dialogList, is_checked,
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton, boolean isChecked) {

                        singleCheckListItemList.get(whichButton).setChecked(isChecked);

                    }
                });

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < list.getCount(); i++) {


                            if (singleCheckListItemList.get(i).isChecked()) {
                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                stringBuilder.append(singleCheckListItemList.get(i).getId());

                            }
                        }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder.toString().trim().equals("")) {
                            Log.i(TAG, "NONE SELECTED");
                            listener.onNothingSelected();
                            /*((TextView) findViewById(R.id.text)).setText("Click here to open Dialog");
                            stringBuilder.setLength(0);*/

                        } else {

                            Log.i(TAG, stringBuilder.toString());

                            listener.onResult(stringBuilder.toString());

                        }
                    }
                });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();
    }

}
