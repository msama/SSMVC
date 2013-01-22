package com.example.exampleapp.listeners;

import com.example.exampleapp.tasks.LogoutRequestTask;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class LogoutListener implements OnClickListener{
	
	private Context context;
	
	public LogoutListener(Context context){
		this.context = context;
	}

	@Override
	public void onClick(View v) {
		LogoutRequestTask logoutTask = new LogoutRequestTask(context);
		logoutTask.execute();
	}

}
