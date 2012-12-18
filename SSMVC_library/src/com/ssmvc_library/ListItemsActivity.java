package com.ssmvc_library;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class ListItemsActivity extends Activity {
	private String l="";
	private TextView text_view;
    public static final String EXTRA_MESSAGE = "com.firstapp.MESSAGE";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		/*text_view=(TextView) findViewById(R.id.list_view);
		Intent intent=getIntent();
		String text = intent.getStringExtra("com.firstapp.MESSAGE");
		l=text;
		text_view.setText(l);*/
	}
	
	@Override
	protected void onResume(){
		Intent intent=getIntent();
		String text = intent.getStringExtra(EXTRA_MESSAGE);
		l+="\n"+text;
		text_view.setText(l);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list, menu);
		return true;
	}

}
