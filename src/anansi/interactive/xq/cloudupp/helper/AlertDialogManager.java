package anansi.interactive.xq.cloudupp.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogManager {
	/**
	 * Function to display simple Alert Dialog
	 * 
	 * @param context
	 *            - application context
	 * @param title
	 *            - alert dialog title
	 * @param message
	 *            - alert message
	 * @param status
	 *            - success/failure (used to set icon) - pass null if you don't
	 *            want icon
	 * */
	public void showAlertDialogs(Context konteks, String judul, String pesan) {
		new AlertDialog.Builder(konteks)
				.setTitle(judul)
				.setMessage(pesan)
				.setNeutralButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							};
						}).show();
	}

}
