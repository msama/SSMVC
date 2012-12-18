package com.ssmvc.ssmvc_lib;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class Lib extends Activity {
	private TextView text_view;
	String l="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lib);
		text_view = (TextView) findViewById(R.id.text_view_list);
		Intent i = getIntent();
		String s=i.getStringExtra("Message");
		System.out.println("Message="+s);
		l+=s;
		text_view.setText(l);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lib, menu);
		return true;
	}

}
