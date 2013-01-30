package com.example.exampleapp.activities;

import com.example.exampleapp.R;
import com.example.exampleapp.R.layout;
import com.example.exampleapp.R.menu;
import com.example.exampleapp.utility.SessionManager;
import com.ssmvc.ssmvc_lib.dbDAO;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.ListView;

public class StatesDigestActivity extends Activity {
	private dbDAO dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_states_digest);
		SessionManager sessionManager = new SessionManager(getApplicationContext());
		
		dao = new dbDAO(this);
		dao.open();
		Cursor c = dao.getAllStateDetails(sessionManager.getDetails().get("id"));
		c.moveToFirst();
//		for(;!c.isAfterLast();c.moveToNext()){
//			System.out.println("letto:"+c.getString(c.getColumnIndex("DESCRIPTION")));
//			System.out.println("letto:"+c.getString(c.getColumnIndex("TIME_DATE")));
//		}
		String[] from = new String[] {"DESCRIPTION","TIME_DATE"};
	    int[] to = new int[] {android.R.id.text1,android.R.id.text2};
	    SimpleCursorAdapter sca=new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, 
	    		c, from, to, 0);
	    ((ListView)findViewById(R.id.StateDigestListView)).setAdapter(sca);
		dao.close();
		

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_states_digest, menu);
		return true;
	}

}
