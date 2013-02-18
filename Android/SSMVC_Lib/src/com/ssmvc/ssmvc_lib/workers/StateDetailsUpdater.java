package com.ssmvc.ssmvc_lib.workers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;

import com.ssmvc.ssmvc_lib.HTTPRequestManager;
import com.ssmvc.ssmvc_lib.R;
import com.ssmvc.ssmvc_lib.dbDAO;
import com.ssmvc.ssmvc_lib.services.IPersistanceCallbacks;

public class StateDetailsUpdater implements IWorker {

	private ArrayList<String[]> paramList;
	private IPersistanceCallbacks resultProcessor;

	public StateDetailsUpdater(IPersistanceCallbacks resultProcessor, ArrayList<String[]> paramList) {
		this.paramList = paramList;
		this.resultProcessor = resultProcessor;
	}

	@Override
	public void doJob(Object... params) {
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
				obj = new JSONObject();
				obj.put("user_id", c.getString(c.getColumnIndex("USER_ID")));
				obj.put("state_id", c.getString(c.getColumnIndex("STATE_ID")));
				obj.put("time_date", c.getString(c.getColumnIndex("TIME_DATE")));
				obj.put("timestamp", c.getString(c.getColumnIndex("TIME_STAMP")));
				queryResult.put(obj);
				c.moveToNext();
			}
			param.put("rows", queryResult);
			JSONObject response = HTTPRequestManager.sendRequest(param, resultProcessor
					.getContext().getString(R.string.updateStateDetails));
			if (response.getBoolean("success")) {
				System.out.println("SUCCESS");
				c.moveToFirst();
				while (!c.isAfterLast()) {
					dbDAO.updateStateDetails(c.getString(c.getColumnIndex("USER_ID")),
							c.getString(c.getColumnIndex("STATE_ID")),
							c.getString(c.getColumnIndex("TIME_DATE")), 0);
					c.moveToNext();
				}
				JSONArray rows = response.getJSONArray("rows");
				JSONObject record;
				for (int i = 0; i < rows.length(); i++) {
					record = rows.getJSONObject(i);
					System.out.println("ROW:" + rows.get(i));
					Date d;
					SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					try {
						d = f.parse(record.getString("TIME_STAMP"));
					} catch (ParseException e) {
						e.printStackTrace();
						return;
					}
					dbDAO.addStateDetails(record.getString("USER_ID"),
							record.getString("STATE_ID"), String.valueOf(d.getTime()), 0);
				}
				resultProcessor.onPersistanceResult();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
