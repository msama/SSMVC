package com.example.exampleapp.activities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.exampleapp.R;
import com.example.exampleapp.R.layout;
import com.example.exampleapp.R.menu;
import com.example.exampleapp.listeners.LogoutListener;
import com.example.exampleapp.utility.SessionManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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
