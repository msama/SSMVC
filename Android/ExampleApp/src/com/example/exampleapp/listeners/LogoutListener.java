package com.example.exampleapp.listeners;

import com.example.exampleapp.tasks.LogoutRequestTask;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @author mircobordoni <br><br>
 * 
 * LogoutListener implements the OnClickListener of the button Logout in the MainActivity. <br>
 * Its main purpose is sending a logout request to the server using the proper asynchronous task, in this
 * case LogoutRequestTask
 *
 */
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
