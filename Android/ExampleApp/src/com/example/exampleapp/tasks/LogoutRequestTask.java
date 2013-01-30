package com.example.exampleapp.tasks;

import java.util.ArrayList;

import org.json.JSONObject;

import com.example.exampleapp.R;
import com.example.exampleapp.activities.MainActivity;
import com.example.exampleapp.dialogs.BaseDialog;
import com.example.exampleapp.utility.SessionManager;
import com.ssmvc.ssmvc_lib.HTTPRequestManager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * 
 * @author mircobordoni
 * <br><br>
 * Asynchronous task used to handle a logout request. If the logout succeeded it starts the MainActivity.
 * Otherwise it shows an error dialog.
 *
 */
public class LogoutRequestTask extends AsyncTask<Void, Void, JSONObject>{

	private Context context;
	private SessionManager sessionManager;
	
	public LogoutRequestTask(Context context){
		this.context=context;
		sessionManager = new SessionManager(context);
	}
	
	@Override
	protected JSONObject doInBackground(Void... params) {
		ArrayList<String[]> parameters = new ArrayList<String[]>();
		parameters.add(new String[]{"uuid",sessionManager.getUUID()});
		return HTTPRequestManager.sendRequest(parameters,context.getString(R.string.logoutURI));
	}
	
	@Override
	protected void onPostExecute(JSONObject finalResult){
		try {
			if (finalResult != null) {
				if(finalResult.getString("success").equals("true")){
					Intent intent = new Intent(context, MainActivity.class);
					sessionManager.logout();
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(intent);
				} else {
					BaseDialog dialog=new BaseDialog("Logout Error", "Logout Failed", context);
					dialog.show();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
