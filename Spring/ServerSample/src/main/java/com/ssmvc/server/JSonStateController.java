package com.ssmvc.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.h2.util.DbDriverActivator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssmvc.server.dao.IStateDao;
import com.ssmvc.server.dao.impl.StateDaoImpl;
import com.ssmvc.server.formModel.GetNewStatesReqModel;
import com.ssmvc.server.formModel.JSonNewStateModel;
import com.ssmvc.server.formModel.JSonStateModel;
import com.ssmvc.server.model.State;
import com.ssmvc.server.utils.SessionManager;

@Controller
public class JSonStateController {
	@Autowired
	private IStateDao stateDao;
	
	/**
	 * Controller used to manage State table synchronization. The client can use the URI /JSonGetNewStates to fetch
	 * new States inserted in the State table from a certain timestamp on. The client has to provide its uuid (the 
	 * session identifier) and the last timestamp in its State table.
	 * @param resp HTTP response
	 * @param newStatesReqModel Object that will contain client uuid and latest timestamp
	 */
	@RequestMapping(value = "/JSonGetNewStates", method = RequestMethod.POST)
	public void jsonGetNewStates(HttpServletResponse resp,@RequestBody GetNewStatesReqModel newStatesReqModel){
		PrintWriter out=null;
		resp.setCharacterEncoding("utf8");
        resp.setContentType("application/json"); 
        try {
			out = resp.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        JSONObject obj = new JSONObject();
        // If no uuid is provided return success=false to the client
		if(newStatesReqModel.getUuid()==null){
			obj.put("success", false);
			out.print(obj);
			return;
		}else // If no session exists associated to client uuid return success=false to the client
			if(!SessionManager.checkSession(newStatesReqModel.getUuid())){
				obj.put("success", true);
				obj.put("loggedin", false);
				out.print(obj);
				return;
		}
		System.out.println("uuid:"+newStatesReqModel.getUuid());
		System.out.println("timestamp:"+newStatesReqModel.getTimestamp());
		List<State> states;
		// If no timestamp is provided by the client
		if(newStatesReqModel.getTimestamp()==null){
			states = stateDao.getAllStates();	// get all records from State table
		}else{
			// else get records from State table having timestamp > latest client timestamp
			states= stateDao.getStatesFrom(newStatesReqModel.getTimestamp());
		}
		
		// Add each record to a JSon Object
		JSONObject resultObject = new JSONObject();
		resultObject.put("success", true);
		JSONArray results =  new JSONArray();
		JSONObject row;
		for(State s:states){
			row = new JSONObject();
			row.put("ID", s.getId());
			row.put("DESCRIPTION", s.getDescription());
			row.put("TIME_STAMP", s.getTime_Stamp().toString());
			results.add(row);
			
			System.out.println(s.getId() + "  " +s.getDescription()+"  "+s.getTime_Stamp() );
		}
		System.out.println("results array size:"+results.size());
		resultObject.put("rows", results);
		
		// Send the http response to the client
		out.print(resultObject);
	}
	

	@RequestMapping(value = "/JSonInsertNewStates", method = RequestMethod.POST)
	public void jsonAddNewStates(HttpServletResponse resp,@RequestBody JSonNewStateModel newStatesModel){
		System.out.println("JSonInsertNewStates");
		PrintWriter out=null;
		resp.setCharacterEncoding("utf8");
        resp.setContentType("application/json"); 
        try {
			out = resp.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        JSONObject obj = new JSONObject();
        if(newStatesModel.getUuid()==null){
        	System.out.println("uuid null");
			obj.put("success", false);
			out.print(obj);
			return;
		}else // If no session exists associated to client uuid return success=false to the client
			if(!SessionManager.checkSession(newStatesModel.getUuid())){
				System.out.println("session expired");
				obj.put("success", true);
				obj.put("loggedin", false);
				out.print(obj);
				return;
		}
        System.out.println("JSON add new state - uuid:"+newStatesModel.getUuid());
        State s;
        System.out.println("ricevute num rows:"+newStatesModel.getRows().size());
        System.out.println("contenuto:\n"+newStatesModel.toString());
        for(JSonStateModel n : newStatesModel.getRows()){
        	System.out.println("id:"+n.getId()+" description:"+n.getDescription()+" timestamp:"+n.getTimestamp());
        	s = new State();
        	s.setId(n.getId());
        	s.setDescription(n.getDescription());
        	Date d;
			try {
				d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(n.getTimestamp());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
        	s.setTime_Stamp(d);
        	stateDao.addState(s);
        }
        obj.put("success", true);
        out.print(obj);
	}
}
