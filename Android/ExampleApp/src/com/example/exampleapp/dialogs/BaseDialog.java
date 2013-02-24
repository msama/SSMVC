package com.example.exampleapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/*
 * Basic dialog used to advice the user about a problematic situation.
 */
public class BaseDialog extends DialogFragment{
	private String title;
	private String message;
	private Context context;
	private AlertDialog alertDialog;
	
	public BaseDialog(String title, String message, Context context){
		this.message=message;
		this.context=context;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle(title);
		alertDialogBuilder
				.setMessage(message)
				.setCancelable(false).setNeutralButton("OK", null);
		alertDialog = alertDialogBuilder.create();
	}
	
	public void show(){
		alertDialog.show();
	}
	
}
