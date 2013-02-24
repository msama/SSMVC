package com.example.exampleapp.tasks;

import java.util.ArrayList;

import org.json.JSONObject;

import com.example.exampleapp.R;
import com.example.exampleapp.activities.MainActivity;
import com.example.exampleapp.activities.WelcomeActivity;
import com.example.exampleapp.dialogs.BaseDialog;
import com.example.exampleapp.utility.SessionManager;
import com.ssmvc.ssmvc_lib.HTTPRequestManager;

import android.content.Intent;
import android.os.AsyncTask;

/**
 * @author mircobordoni
 * <br><br>
 * Asynchronous task used to send login information to the server and wait for a response.
 */
public class LoginRequestTask extends AsyncTask<Void, Void, JSONObject>{
	// List of Request parameters
	private ArrayList<String[]> parameters; 
	
	private MainActivity context;
	private SessionManager sessionManager;
	
	public LoginRequestTask(ArrayList<String[]> parameters, MainActivity context, SessionManager sessionManager){
		this.parameters=parameters;
		this.context=context;
		this.sessionManager = sessionManager;
	}
	
	@Override
	public void onPreExecute(){
		context.loginButtonEnabled(false);
	}

	/*
	 * Asynchronous task: start a new thread and send a request to the server. The response is a
	 * JSONObject and it is automatically passed to the onPostExecute method.
	 * 
	 */
	@Override
	protected JSONObject doInBackground(Void... params) {
		return HTTPRequestManager.sendRequest(parameters,context.getString(R.string.loginURI));
	}

	/*
	 * Method executed in the main thread and handling the result of doInBackground. 
	 * If login succeeded it initializes a new Session using the SessionManager and start a new 
	 * WelcomeActivity. Otherwise it shows an error dialog.
	 */
	@Override
	protected void onPostExecute(JSONObject finalResult){
		try {
			System.out.println("finalResult:"+finalResult);
			if (finalResult != null) {
				// If the user has provided correct credentials start the WelcomeActivity
				
				if (finalResult.getString("success").equals("true")) {
					System.out.println("name:" + finalResult.getString("name")
							+ " " + finalResult.getString("surname") + " "
							+ "ID:" + finalResult.getString("id")+
							" UUID:"+ finalResult.getString("uuid"));
					System.out.println(finalResult.getJSONArray("roles").get(0));
					Intent intent = new Intent(context, WelcomeActivity.class);
					sessionManager.login(finalResult.getString("id"), 
							finalResult.getString("name"), finalResult.getString("surname"),
							finalResult.getString("uuid"),finalResult.getJSONArray("roles"));
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.loginButtonEnabled(true);
					context.startActivity(intent);
				} else {  // Otherwise create a new BaseDialog to inform the user
					BaseDialog dialog=new BaseDialog("Credentials Error", "Wrong Username or Password", context);
					dialog.show();
					context.loginButtonEnabled(true);
				}
			}
		} catch (Exception ex) {
			context.loginButtonEnabled(true);
			ex.printStackTrace();
		}
	}
	
}
