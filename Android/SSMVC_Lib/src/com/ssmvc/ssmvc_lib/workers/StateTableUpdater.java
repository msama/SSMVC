package com.ssmvc.ssmvc_lib.workers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ssmvc.ssmvc_lib.HTTPRequestManager;
import com.ssmvc.ssmvc_lib.R;
import com.ssmvc.ssmvc_lib.dbDAO;
import com.ssmvc.ssmvc_lib.services.IPersistanceCallbacks;

/**
 * 
 * @author mircobordoni
 * <br><br>
 * Thread used to asynchronously send an HTTP request to the server and process the response.
 * This thread gets new records from the remote table STATE and insert them in the local table STATE.
 */
public class StateTableUpdater extends Thread{
	
	private IPersistanceCallbacks resultProcessor;
	private ArrayList<String[]> paramsList;
	
	public StateTableUpdater(ArrayList<String[]> paramsList,IPersistanceCallbacks resultProcessor){
		this.paramsList=paramsList;
		this.resultProcessor=resultProcessor;
	}
	
	public void run(){
		Context context=resultProcessor.getContext();
		JSONObject result = HTTPRequestManager.sendRequest(paramsList,context.getString(R.string.getNewStatesURI));
		if(result.length()!=0){
			System.out.println("result length="+result.length());
			try {
				if(!result.getBoolean("success"))return;
				JSONArray rows = result.getJSONArray("rows");
				System.out.println("rows.length?"+rows.length());
				JSONObject record;
				for(int i=0; i<rows.length();i++){
					record = rows.getJSONObject(i);
					System.out.println("ROW:"+rows.get(i));
					dbDAO.addState(record.getString("ID"), record.getString("DESCRIPTION"), 
							record.getString("TIME_STAMP"), 0);
				}
				resultProcessor.onPersistanceResult();
			} catch (JSONException e) {
				e.printStackTrace();
				resultProcessor.onPersistanceResult();
			}
		}
	}

}
