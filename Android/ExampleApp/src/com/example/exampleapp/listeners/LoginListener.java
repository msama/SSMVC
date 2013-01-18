package com.example.exampleapp.listeners;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.exampleapp.R;
import com.example.exampleapp.activities.WelcomeActivity;
import com.example.exampleapp.dialogs.BaseDialog;
import com.example.exampleapp.utility.DecodingUtils;
import com.example.exampleapp.utility.SessionManager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoginListener implements OnClickListener {

	EditText username, password;
	Context context;
	SessionManager sessionManager;

	public LoginListener(EditText username, EditText password, Context context) {
		this.username = username;
		this.password = password;
		this.context = context;
		sessionManager=new SessionManager(context);
	}

	/*
	 * Login Button Listener
	 * Sends Username and Password to the Server and waits for an HTTP Response.
	 */
	@Override
	public void onClick(View arg0) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = null;

		HttpPost postMethod = new HttpPost(
				context.getString(R.string.serverURI)
						+ context.getString(R.string.loginURI));

		JSONObject json = new JSONObject();
		try {
			json.put("username", username.getText());
			json.put("password", password.getText());
			postMethod.setEntity(new ByteArrayEntity(json.toString().getBytes(
					"UTF8")));
			postMethod.setHeader("Content-Type", "application/json");
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			response = client.execute(postMethod);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject finalResult = null;
		try {
			finalResult=DecodingUtils.decodeJson(response.getEntity().getContent());
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
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);

				} else {
					BaseDialog dialog=new BaseDialog("Credentials Error", "Wrong Username or Password", context);
					dialog.show();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
