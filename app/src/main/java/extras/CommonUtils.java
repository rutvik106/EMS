package extras;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by rutvik on 22-04-2016 at 02:36 PM.
 */
public final class CommonUtils
{

    public static final String MANDATORY_FIELD = "MaNdAtOrY";

    public static void promptForMakingCall(final Context context, final Intent intent)
    {
        new AlertDialog.Builder(context)
                .setTitle("Call")
                .setMessage("Are you sure you want to call?")
                .setPositiveButton("CALL", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        context.startActivity(intent);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }

    public static void hideSoftKeyboard(Activity activity)
    {
        if (activity.getCurrentFocus() != null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

}
