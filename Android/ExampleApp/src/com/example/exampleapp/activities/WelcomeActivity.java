package com.example.exampleapp.activities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import com.example.exampleapp.R;
import com.example.exampleapp.dialogs.BaseDialog;
import com.example.exampleapp.listeners.LogoutListener;
import com.example.exampleapp.utility.SessionManager;
import com.ssmvc.ssmvc_lib.*;
import com.ssmvc.ssmvc_lib.services.*;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 
 * @author mircobordoni
 * <br><br>
 * This activity is the first one showed after a successful login. <br>
 * It gives the chance to send the actual mood of the user, update the list of possible mood states and
 * logout from the system.
 */
public class WelcomeActivity extends Activity implements OnClickListener,
		IPersistanceCallbacks {

	private PersistanceService persistanceService;
	private IPersistanceCallbacks resultProcessor = this;
	private boolean isBound;
	private Cursor cursor;
	private Activity context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);

		TextView detailsTextView = (TextView) findViewById(R.id.DetailsTextView);
		
		// Create a new Session manager
		SessionManager.checkLogin();
		dbDAO.removeStatesDetails();
		dbDAO.removeAllStates();
		HashMap<String, String> details = SessionManager.getDetails();
		detailsTextView.setText("Welcome " + details.get("name") + " "
				+ details.get("surname"));
		
		// Bind this Activity to Persistance Service
		Intent intent = new Intent(this, PersistanceService.class);
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

		System.out.println("persistanceService="+persistanceService);
		// Selecting which view component will be visible or not, depending on user Role
		if(!SessionManager.isAdmin()){
			((Button) findViewById(R.id.SendStateButton)).setVisibility(View.VISIBLE);
			((Button) findViewById(R.id.StatesDigestButton)).setVisibility(View.VISIBLE);
			((Button) findViewById(R.id.SendNewStateButton)).setVisibility(View.INVISIBLE);
			((EditText) findViewById(R.id.NewState)).setVisibility(View.INVISIBLE);
			((Button) findViewById(R.id.SendStateButton))
			.setOnClickListener(this);
			((Button) findViewById(R.id.StatesDigestButton))
			.setOnClickListener(this);
		}else{
			((Button) findViewById(R.id.SendNewStateButton)).setVisibility(View.VISIBLE);
			((EditText) findViewById(R.id.NewState)).setVisibility(View.VISIBLE);
			((Button) findViewById(R.id.SendStateButton)).setVisibility(View.INVISIBLE);
			((Button) findViewById(R.id.StatesDigestButton)).setVisibility(View.INVISIBLE);
			((Button) findViewById(R.id.SendNewStateButton)).setOnClickListener(this);
		}
		
		// Adding the proper listener to each button of the View
		((Button) findViewById(R.id.LogoutButton))
		.setOnClickListener(new LogoutListener(this));
		((Button) findViewById(R.id.UpdateStatesButton))
		.setOnClickListener(this);
		((Button) findViewById(R.id.DeleteAllStatesButton))
		.setOnClickListener(this);
		
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

	/**
	 * Click listener method. 
	 */
	@Override
	public void onClick(View v) {
		ArrayList<String[]> params = new ArrayList<String[]>();
		params.add(new String[]{"uuid",SessionManager.getUUID()});
		if(v==((Button) findViewById(R.id.StatesDigestButton))){ // Start StatesDigestActivity
			Intent intent = new Intent(this, StatesDigestActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}else if(v==((Button) findViewById(R.id.UpdateStatesButton))){	// Start PersistanceService.getAllStates
			if(isBound)persistanceService.getAllStates(resultProcessor,params);
		}else if(v==((Button) findViewById(R.id.SendNewStateButton))){
			EditText newState = (EditText) findViewById(R.id.NewState);
			if(newState.getText().equals("")){
				BaseDialog dialog = new BaseDialog("Missing State Description", 
						"Insert a Description for the State", this);
			}
			Date date= new Date();
			persistanceService.insertNewState(this,UUID.randomUUID().toString(),
					newState.getText().toString(),String.valueOf(date.getTime()),params);
		}else if(v==((Button) findViewById(R.id.DeleteAllStatesButton))){
			dbDAO.removeAllStates();
			persistanceService.getAllStates(resultProcessor,params);
		}else if(v==((Button) findViewById(R.id.SendStateButton))){
			Spinner spinner = (Spinner)findViewById(R.id.StateListSpinner);;
			Cursor item = (Cursor) spinner.getSelectedItem();
			String state_id=item.getString(item.getColumnIndex("_id"));
			System.out.println("state_id="+state_id);
			Date date= new Date();			
			persistanceService.insertNewStateDetails(this, 
					SessionManager.getUserId(), state_id, String.valueOf(date.getTime()), params);
		}
	}

	/**
	 * Callback defined to manage the results provided by PersistanceService.
	 */
	@Override
	public void onPersistanceResult(Object... objects) {
		cursor = dbDAO.getAllStates();
		String[] from = new String[] { "DESCRIPTION" };
		int[] to = new int[] { android.R.id.text1 };
		final SimpleCursorAdapter sca = new SimpleCursorAdapter(
				this, android.R.layout.simple_spinner_item, cursor,
				from, to, 0);

		// set layout for activated adapter
		sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// get xml file spinner and set adapter
		runOnUiThread(new Runnable() {
			public void run() {
				Spinner spin = (Spinner)context 
						.findViewById(R.id.StateListSpinner);
				spin.setAdapter(sca);
			}
		});

	}

	@Override
	public Context getContext() {
		return this;
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		unbindService(serviceConnection);
	}

	// ServiceConnection has to be implemented to manage connection to Services.
	private ServiceConnection serviceConnection = new ServiceConnection() {

		
		public void onServiceConnected(ComponentName className, IBinder service) {
			PersistanceLocalBinder binder = (PersistanceLocalBinder) service;
			persistanceService = (PersistanceService) binder.getService();
			ArrayList<String[]> params = new ArrayList<String[]>();
			params.add(new String[]{"uuid",SessionManager.getUUID()});
			// Call persistance service method to update local states with new ones from server
			persistanceService.getAllStates(resultProcessor,params);
			persistanceService.getAllStateDetails(resultProcessor, params, SessionManager.getUserId());
			isBound = true;
		}

		public void onServiceDisconnected(ComponentName arg0) {
			isBound = false;
		}

	};

}
