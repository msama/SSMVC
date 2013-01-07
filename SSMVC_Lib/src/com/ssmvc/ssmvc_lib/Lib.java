package com.ssmvc.ssmvc_lib;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class Lib extends Activity {
	private TextView text_view;
	String l;
	private static String CONTENT_STRING = "ssmvc_lib.content_string";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lib);
        System.out.println("Lib Activity - ON CREATE");
        l="";
        if(savedInstanceState!=null){
        	System.out.println("SavedInstance !=null");
        	l=savedInstanceState.containsKey(CONTENT_STRING)?
        		savedInstanceState.getString(CONTENT_STRING)
        	:
   	    		"";
        }
    		
		text_view = (TextView) findViewById(R.id.text_view_list);
	}
	
	/*@Override
	public void onRestoreInstanceState(Bundle savedInstanceState){
		l=savedInstanceState.containsKey(CONTENT_STRING)?
			savedInstanceState.getString(CONTENT_STRING)
		:
			"";
		System.out.println("l="+l);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		System.out.println("onSave");
		savedInstanceState.putString(CONTENT_STRING, l);
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onPause(){
		super.onPause();
        System.out.println("Lib Activity - ON PAUSE");
	} */
	
	@Override
	public void onResume(){
		super.onResume();
        System.out.println("Lib Activity - ON RESUME");
        Intent i = getIntent();
		String s=i.getStringExtra("Message");
		l+="\n"+s;
        text_view.setText(l);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lib, menu);
		return true;
	}

}
