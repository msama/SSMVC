package com.ssmvc.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssmvc.server.dao.IStateDao;
import com.ssmvc.server.formModel.JSonNewStateModel;
import com.ssmvc.server.formModel.JSonStateModel;
import com.ssmvc.server.model.State;
import com.ssmvc.server.utils.SessionManager;

@Controller
public class JSonStateController {
	@Autowired
	private IStateDao stateDao;


	@RequestMapping(value = "/JSonUpdateState", method = RequestMethod.POST)
	public void jsonUpdateState(HttpServletResponse resp,
			@RequestBody JSonNewStateModel newStatesModel) {
		System.out.println("JSON update state");
		PrintWriter out = null;
		resp.setCharacterEncoding("utf8");
		resp.setContentType("application/json");
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		if (newStatesModel.getUuid() == null) {
			System.out.println("uuid null");
			obj.put("success", false);
			out.print(obj);
			return;
		} else // If no session exists associated to client uuid return
				// success=false to the client
		if (!SessionManager.checkSession(newStatesModel.getUuid())) {
			System.out.println("session expired");
			obj.put("success", false);
			out.print(obj);
			return;
		}

		System.out.println("uuid:" + newStatesModel.getUuid());
		System.out.println("timestamp:" + newStatesModel.getTimestamp());
		List<State> states;
		// If no timestamp is provided by the client
		if (newStatesModel.getTimestamp() == null) {
			states = stateDao.getAllStates(); // get all records from State
												// table
		} else {
			// else get records from State table having timestamp > latest
			// client timestamp
			states = stateDao.getStatesFromTimestamp(newStatesModel
					.getTimestamp());
			System.out.println("Timestamp received:"
					+ newStatesModel.getTimestamp());
		}

		// Add each record to a JSon Object
		JSONObject resultObject = new JSONObject();
		JSONArray results = new JSONArray();
		JSONObject row;
		for (State s : states) {
			row = new JSONObject();
			row.put("ID", s.getId());
			row.put("DESCRIPTION", s.getDescription());
			row.put("TIME_STAMP", s.getTime_Stamp().getTime());
			results.add(row);

			System.out.println(s.getId() + "  " + s.getDescription() + "  "
					+ s.getTime_Stamp());
		}
		System.out.println("results array size:" + results.size());
		resultObject.put("rows", results);

		State s;
		System.out.println("ricevute num rows:"
				+ newStatesModel.getRows().size());
		System.out.println("contenuto:\n" + newStatesModel.toString());
		for (JSonStateModel n : newStatesModel.getRows()) {
			System.out.println("id:" + n.getId() + " description:"
					+ n.getDescription() + " timestamp:" + n.getTimestamp());
			s = new State();
			s.setId(n.getId());
			s.setDescription(n.getDescription());
			Date d = new Date(Long.parseLong(n.getTimestamp()));
			s.setTime_Stamp(d);
			stateDao.addState(s);
		}
		resultObject.put("success", true);

		out.print(resultObject);
	}
}
