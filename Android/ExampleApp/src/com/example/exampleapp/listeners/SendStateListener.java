package com.example.exampleapp.listeners;

import com.example.exampleapp.activities.WelcomeActivity;
import com.ssmvc.ssmvc_lib.dbDAO;

import android.view.View;
import android.view.View.OnClickListener;

public class SendStateListener implements OnClickListener {
	
	private WelcomeActivity context;
	private dbDAO dao;

	public SendStateListener(WelcomeActivity context, dbDAO dao){
		this.context = context;
		this.dao = dao;
	}
	
	
	@Override
	public void onClick(View v) {
		
		
	}

}
