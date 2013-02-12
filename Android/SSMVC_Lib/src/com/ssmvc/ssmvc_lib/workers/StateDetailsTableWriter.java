package com.ssmvc.ssmvc_lib.workers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.ssmvc.ssmvc_lib.HTTPRequestManager;
import com.ssmvc.ssmvc_lib.R;
import com.ssmvc.ssmvc_lib.dbDAO;
import com.ssmvc.ssmvc_lib.services.IPersistanceCallbacks;

public class StateDetailsTableWriter extends Thread{

	private ArrayList<String[]> paramList;
	private IPersistanceCallbacks resultProcessor;

	public StateDetailsTableWriter(IPersistanceCallbacks resultProcessor, ArrayList<String[]> paramList) {
		this.paramList = paramList;
		this.resultProcessor = resultProcessor;
	}

	public void run() {
		Cursor c = dbDAO.getAllStateDetails(1);
		JSONObject param = new JSONObject();
		try {
			for (String[] s : paramList) {
				param.put(s[0], s[1]);

			}
			JSONArray queryResult = new JSONArray();
			c.moveToFirst();
			JSONObject obj;
			while (!c.isAfterLast()) {
				obj=new JSONObject();
				obj.put("user_id", c.getString(c.getColumnIndex("USER_ID")));
				obj.put("state_id", c.getString(c.getColumnIndex("STATE_ID")));
				obj.put("time_date", c.getString(c.getColumnIndex("TIME_DATE")));
				obj.put("timestamp", c.getString(c.getColumnIndex("TIME_STAMP")));
				queryResult.put(obj);
				c.moveToNext();
			}
			param.put("rows", queryResult);
			JSONObject response = HTTPRequestManager.sendRequest(param, resultProcessor.getContext().getString(R.string.insertNewStateDetailsURI));
			if(response.getBoolean("success")){
				System.out.println("SUCCESS");
				c.moveToFirst();
				while(!c.isAfterLast()){
					dbDAO.updateStateDetails(c.getString(c.getColumnIndex("USER_ID")),
							c.getString(c.getColumnIndex("STATE_ID")),
							c.getString(c.getColumnIndex("TIME_DATE")), 0);
					c.moveToNext();
				}
				resultProcessor.onPersistanceResult();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
