package com.example.exampleapp.listeners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoginListener implements OnClickListener {
	
	EditText username,password;
	Context context;
	
	public LoginListener(EditText username, EditText password,Context context){
		this.username=username;
		this.password=password;
		this.context=context;
	}

	
	public void login(View view){
		
	}
	
	/*
	 * Login Button Listener
	 */
	@Override
	public void onClick(View arg0) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response=null;
	    HttpPost postMethod = new HttpPost("http://10.16.101.155:8080/server/loginJson");
	    JSONObject json = new JSONObject();
	    try {
			json.put("username", username.getText());
			json.put("password", password.getText());
		    postMethod.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
		    postMethod.setHeader( "Content-Type", "application/json" );
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    try {
			response=client.execute(postMethod);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    String jsonElement="";
	    JSONObject finalResult=null;
	    try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			jsonElement = reader.readLine();
			JSONTokener tokener = new JSONTokener(jsonElement);
			finalResult = new JSONObject(tokener);
		    if(finalResult.getString("success").equals("true")){
		    	
		    }else {
		    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);					
		    	alertDialogBuilder.setTitle("Credentials Error");
		    	alertDialogBuilder
						.setMessage("Wrong Username or Password!")
						.setCancelable(false)
						.setNeutralButton("OK", null);						
		    	AlertDialog alertDialog = alertDialogBuilder.create();
		    	alertDialog.show();
		    }

	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
