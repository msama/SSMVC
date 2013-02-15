package com.ssmvc.ssmvc_lib.workers;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ssmvc.ssmvc_lib.HTTPRequestManager;
import com.ssmvc.ssmvc_lib.R;
import com.ssmvc.ssmvc_lib.dbDAO;
import com.ssmvc.ssmvc_lib.services.IPersistanceCallbacks;

public class StateDetailsTableUpdater implements IWorker{

	private IPersistanceCallbacks resultProcessor;
	private ArrayList<String[]> paramsList;
	
	public StateDetailsTableUpdater(ArrayList<String[]> paramsList,IPersistanceCallbacks resultProcessor){
		this.paramsList=paramsList;
		this.resultProcessor=resultProcessor;
	}
	
	public void doJob(Object...params){
		Context context=resultProcessor.getContext();
		JSONObject result = HTTPRequestManager.sendRequest(paramsList,context.getString(R.string.getNewStateDetailsURI));
		if(result.length()!=0){
			System.out.println("result length="+result.length());
			try {
				if(!result.getBoolean("success"))return;
				JSONArray rows = result.getJSONArray("rows");
				JSONObject record;
				for(int i=0; i<rows.length();i++){
					record = rows.getJSONObject(i);
					System.out.println("ROW:"+rows.get(i));
					Date d;
					SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					d=f.parse(record.getString("TIME_STAMP"));
					dbDAO.addStateDetails(record.getString("USER_ID"),record.getString("STATE_ID"), 
							String.valueOf(d.getTime()), 0);
				}
				resultProcessor.onPersistanceResult();
			} catch (JSONException e) {
				e.printStackTrace();
				resultProcessor.onPersistanceResult();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
