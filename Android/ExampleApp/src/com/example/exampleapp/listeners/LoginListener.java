package com.example.exampleapp.listeners;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.exampleapp.R;
import com.example.exampleapp.activities.MainActivity;
import com.example.exampleapp.activities.WelcomeActivity;
import com.example.exampleapp.dialogs.BaseDialog;
import com.example.exampleapp.tasks.LoginRequestTask;
import com.example.exampleapp.utility.DecodingUtils;
import com.example.exampleapp.utility.SessionManager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoginListener implements OnClickListener {

	EditText username, password;
	MainActivity context;
	SessionManager sessionManager;

	public LoginListener(EditText username, EditText password, MainActivity context) {
		this.username = username;
		this.password = password;
		this.context = context;
		sessionManager=new SessionManager(context);
	}

	/*
	 * Login Button Listener
	 * Activate an Asynctask to send Credentials to the server.
	 */
	@Override
	public void onClick(View arg0) {
		if(username.getText().toString().equals("") || password.getText().toString().equals(""))
			return;
		
		ArrayList<String[]> params = new ArrayList<String[]>();
		params.add(new String[]{"username",username.getText().toString()});
		params.add(new String[]{"password",password.getText().toString()});
		
		LoginRequestTask loginTask = new LoginRequestTask(params, context, sessionManager);
		loginTask.execute();
	}

}
