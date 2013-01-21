package com.example.exampleapp.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpRequest;
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
import com.example.exampleapp.utility.DecodingUtils;
import com.example.exampleapp.utility.HTTPRequestManager;
import com.example.exampleapp.utility.SessionManager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.webkit.WebView.FindListener;
import android.widget.Button;

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

	
	@Override
	protected JSONObject doInBackground(Void... params) {
//		DefaultHttpClient client = new DefaultHttpClient();
//		HttpResponse response = null;
//		JSONObject finalResult = null;
//
//		HttpPost postMethod = new HttpPost(
//				context.getString(R.string.serverURI)
//						+ context.getString(R.string.loginURI));
//
//		JSONObject json = new JSONObject();
//		try {
//			for(String[] p: this.parameters){
//				json.put(p[0], p[1]);
//			}
//			postMethod.setEntity(new ByteArrayEntity(json.toString().getBytes(
//					"UTF8")));
//			postMethod.setHeader("Content-Type", "application/json");
//		} catch (JSONException e) {
//			context.loginButtonEnabled(true);
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			context.loginButtonEnabled(true);
//			e.printStackTrace();
//		}
//		try {
//			response = client.execute(postMethod);
//			finalResult=DecodingUtils.decodeJson(response.getEntity().getContent());
//		} catch (ClientProtocolException e) {
//			context.loginButtonEnabled(true);
//			e.printStackTrace();
//		} catch (IOException e) {
//			context.loginButtonEnabled(true);
//			e.printStackTrace();
//		}
		HTTPRequestManager requestManager = new HTTPRequestManager(context);
		return requestManager.sendRequest(parameters,context.getString(R.string.loginURI));
	}

	@Override
	protected void onPostExecute(JSONObject finalResult){
		try {
			if (finalResult != null) {
				if (finalResult.getString("success").equals("true")) {
					System.out.println("name:" + finalResult.getString("name")
							+ " " + finalResult.getString("surname") + " "
							+ "ID:" + finalResult.getString("id")+
							" UUID:"+ finalResult.getString("uuid"));
					Intent intent = new Intent(context, WelcomeActivity.class);
					sessionManager.login(finalResult.getString("id"), 
							finalResult.getString("name"), finalResult.getString("surname"),
							finalResult.getString("uuid"));
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.loginButtonEnabled(true);
					context.startActivity(intent);

				} else {
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
