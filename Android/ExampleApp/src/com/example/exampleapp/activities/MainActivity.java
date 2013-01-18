package com.example.exampleapp.activities;


import com.example.exampleapp.R;
import com.example.exampleapp.listeners.LoginListener;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private EditText username, password;
	private StrictMode.ThreadPolicy policy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		username = (EditText) findViewById(R.id.UsernameField);
		password = (EditText) findViewById(R.id.PasswordField);
		((Button)findViewById(R.id.loginButton)).setOnClickListener(new LoginListener(username, password, getApplicationContext()));
		
		policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity, menu);
		return true;
	}
	
	

}
