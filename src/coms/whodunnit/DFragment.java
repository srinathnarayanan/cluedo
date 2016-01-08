package coms.whodunnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DFragment extends DialogFragment 
{

	MyDialogCloseListener   closeListener   = new MyDialogCloseListener() {

        @Override
        public void handleDialogClose(DialogInterface dialog) {

                //do here whatever you want to do on Dialog dismiss
            }
        };
        

	public static int result;
	
	public static DFragment newInstance(String title) {
	    
	DFragment frag = new DFragment();
    Bundle args = new Bundle();
    args.putString("title", title);
    args.putInt("result", result);
    frag.setArguments(args);
    return frag;
}

@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
    String title = getArguments().getString("title");

    return new AlertDialog.Builder(getActivity())
            .setTitle("Are you sure you want to choose "+title)
            .setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	result=1;
                    	dialog.dismiss();
                    }
                }
            )
            .setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	result=0;
                    	dialog.dismiss();
                    }
                }
            )
            .create();
    
}

@Override
public void onDismiss(final DialogInterface dialog) {
    super.onDismiss(dialog);
    final Activity activity = getActivity();
    if (activity instanceof DialogInterface.OnDismissListener) {
        ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
    }
}

}