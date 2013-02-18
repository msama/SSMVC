package com.ssmvc.ssmvc_lib.workers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;

import com.ssmvc.ssmvc_lib.HTTPRequestManager;
import com.ssmvc.ssmvc_lib.R;
import com.ssmvc.ssmvc_lib.dbDAO;
import com.ssmvc.ssmvc_lib.services.IPersistanceCallbacks;

public class StateUpdater implements IWorker {

	private ArrayList<String[]> paramList;
	private IPersistanceCallbacks resultProcessor;

	public StateUpdater(IPersistanceCallbacks resultProcessor, ArrayList<String[]> paramList) {
		this.paramList = paramList;
		this.resultProcessor = resultProcessor;
	}

	@Override
	public void doJob(Object... params) {
		Cursor c = dbDAO.getAllStates(1);
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
				obj.put("id", c.getString(c.getColumnIndex("ID")));
				obj.put("description", c.getString(c.getColumnIndex("DESCRIPTION")));
				obj.put("timestamp", c.getString(c.getColumnIndex("TIME_STAMP")));
				queryResult.put(obj);
				c.moveToNext();
			}
			param.put("rows", queryResult);
			JSONObject response = HTTPRequestManager.sendRequest(param, resultProcessor
					.getContext().getString(R.string.updateState));
			if (response.getBoolean("success")) {
				System.out.println("SUCCESS");
				c.moveToFirst();
				while (!c.isAfterLast()) {
					dbDAO.updateState(c.getString(c.getColumnIndex("ID")), 0);
					c.moveToNext();
				}
				JSONArray rows = response.getJSONArray("rows");
				System.out.println("rows.length?" + rows.length());
				JSONObject record;
				for (int i = 0; i < rows.length(); i++) {
					record = rows.getJSONObject(i);
					System.out.println("ROW:" + rows.get(i));
					dbDAO.addState(record.getString("ID"), record.getString("DESCRIPTION"),
							record.getString("TIME_STAMP"), 0);
				}
				resultProcessor.onPersistanceResult();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
