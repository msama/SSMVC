package com.example.exampleapp.activities;


import java.util.HashMap;

import com.example.exampleapp.R;
import com.example.exampleapp.listeners.LogoutListener;
import com.example.exampleapp.listeners.SendStateListener;
import com.example.exampleapp.utility.SessionManager;
import com.ssmvc.ssmvc_lib.*;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class WelcomeActivity extends Activity {
	
	private SessionManager sessionManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		
		TextView detailsTextView = (TextView)findViewById(R.id.DetailsTextView);
		((Button)findViewById(R.id.LogoutButton)).setOnClickListener(new LogoutListener(this));
		
		sessionManager = new SessionManager(getApplicationContext());
		sessionManager.checkLogin();
		HashMap<String, String> details = sessionManager.getDetails();
		detailsTextView.setText("Welcome "+details.get("name")+" "+details.get("surname"));
		
		dbDAO dao = new dbDAO(this);
		dao.open();
//		dao.removeStates();
//		dao.initStates();
//		dao.addState("Sad");
		Cursor cursor=dao.getAllStates();
		System.out.println("Count:"+cursor.getCount());
		
		 // make an adapter from the cursor
	    String[] from = new String[] {"DESCRIPTION"};
	    int[] to = new int[] {android.R.id.text1};
	    SimpleCursorAdapter sca=new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, from, to, 0);

	    // set layout for activated adapter
	    sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 

	    // get xml file spinner and set adapter 
	    Spinner spin = (Spinner) this.findViewById(R.id.StateListSpinner);
	    spin.setAdapter(sca);
	    dao.close();
	    
	    ((Button)findViewById(R.id.SendStateButton)).setOnClickListener(new SendStateListener(this,dao));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
	    moveTaskToBack(true);
	}

}
