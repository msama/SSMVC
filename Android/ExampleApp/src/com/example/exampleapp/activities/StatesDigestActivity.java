package com.example.exampleapp.activities;

import java.util.ArrayList;

import com.example.exampleapp.R;
import com.example.exampleapp.utility.SessionManager;
import com.ssmvc.ssmvc_lib.dbDAO;
import com.ssmvc.ssmvc_lib.services.IPersistanceCallbacks;
import com.ssmvc.ssmvc_lib.services.PersistanceLocalBinder;
import com.ssmvc.ssmvc_lib.services.PersistanceService;

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
import android.widget.ListView;

public class StatesDigestActivity extends Activity implements
		IPersistanceCallbacks {
	
	private PersistanceService persistanceService;
	private IPersistanceCallbacks resultProcessor = this;
	private boolean isBound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_states_digest);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		// Bind this Activity to Persistance Service
		Intent intent = new Intent(this, PersistanceService.class);
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		unbindService(serviceConnection);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_states_digest, menu);
		return true;
	}

	@Override
	public void onPersistanceResult(Object... objects) {
		Cursor c = dbDAO.getAllStateDetails(SessionManager.getUserId());
		c.moveToFirst();
		// for(;!c.isAfterLast();c.moveToNext()){
		// System.out.println("letto:"+c.getString(c.getColumnIndex("DESCRIPTION")));
		// System.out.println("letto:"+c.getString(c.getColumnIndex("TIME_DATE")));
		// }
		String[] from = new String[] { "DESCRIPTION", "TIME_DATE" };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
		final SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				android.R.layout.two_line_list_item, c, from, to, 0);
		runOnUiThread(new Runnable() {
			public void run() {
				((ListView) findViewById(R.id.StateDigestListView)).setAdapter(sca);
			}
		});
	}

	@Override
	public Context getContext() {
		return this;
	}

	// ServiceConnection has to be implemented to manage connection to Services.
	private ServiceConnection serviceConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder service) {
			PersistanceLocalBinder binder = (PersistanceLocalBinder) service;
			persistanceService = (PersistanceService) binder.getService();
			ArrayList<String[]> params = new ArrayList<String[]>();
			params.add(new String[] { "uuid", SessionManager.getUUID() });
			// Call persistance service method to update local state_details with new
			// ones from server
			persistanceService.getAllStateDetails(resultProcessor, params,SessionManager.getUserId());
			isBound = true;
		}

		public void onServiceDisconnected(ComponentName arg0) {
			isBound = false;
		}

	};

}
