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
import com.ssmvc.server.formModel.JSonGetNewStateDetailsReqModel;
import com.ssmvc.server.formModel.JSonGetNewStatesReqModel;
import com.ssmvc.server.formModel.JSonNewStateDetailsModel;
import com.ssmvc.server.formModel.JSonNewStateModel;
import com.ssmvc.server.formModel.JSonStateDetailsModel;
import com.ssmvc.server.formModel.JSonStateModel;
import com.ssmvc.server.model.State;
import com.ssmvc.server.model.State_Details;
import com.ssmvc.server.utils.SessionManager;

@Controller
public class JSonStateDetailsController {
	@Autowired
	private IStateDao stateDao;
	
	
	/**
	 * Controller used to manage State_Details table synchronization. The client can use the URI /JSonGetNewStateDetails 
	 * to fetch new State_Details inserted in the State_Details table from a certain timestamp on. 
	 * The client has to provide its uuid (the session identifier), the last timestamp in its local STATE_DETAILS table 
	 * and its user_id.
	 * @param resp HTTP response
	 * @param newStateDetailsReqModel Object that will contain client uuid, latest timestamp and user_id
	 */
	@RequestMapping(value = "/JSonGetNewStateDetails", method = RequestMethod.POST)
	public void jsonGetNewStateDetails(HttpServletResponse resp,@RequestBody JSonGetNewStateDetailsReqModel newStateDetailsReqModel){
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
		if(newStateDetailsReqModel.getUuid()==null){
			obj.put("success", false);
			out.print(obj);
			return;
		}else // If no session exists associated to client uuid return success=false to the client
			if(!SessionManager.checkSession(newStateDetailsReqModel.getUuid())){
				obj.put("success", false);
				out.print(obj);
				return;
		}
		System.out.println("uuid:"+newStateDetailsReqModel.getUuid());
		System.out.println("timestamp:"+newStateDetailsReqModel.getTimestamp());
		System.out.println("user_id:"+newStateDetailsReqModel.getUser_id());
		List<State_Details> state_details;
		// If no timestamp is provided by the client
		if(newStateDetailsReqModel.getTimestamp()==null){
			if(newStateDetailsReqModel.getUser_id()==null)
				state_details = stateDao.getAllStateDetails();	// get all records from State_Details table
			else
				state_details = stateDao.getAllStateDetailsByUserId(newStateDetailsReqModel.getUser_id());
		}else{
			// else get records from State_Details table having timestamp > latest client timestamp
			state_details= stateDao.getStateDetailsByIdFromTimestamp(newStateDetailsReqModel.getUser_id(),
					newStateDetailsReqModel.getTimestamp());
		}
		
		// Add each record to a JSon Object
		JSONObject resultObject = new JSONObject();
		resultObject.put("success", true);
		JSONArray results =  new JSONArray();
		JSONObject row;
		for(State_Details sd:state_details){
			row = new JSONObject();
			row.put("USER_ID", sd.getUser_Id());
			row.put("STATE_ID", sd.getState_Id());
			row.put("TIME_STAMP", sd.getTime_Stamp().toString());
			results.add(row);
			
			System.out.println("sent user_id:"+sd.getUser_Id() + " state_id:" +sd.getState_Id()+"  time_stamp:"+
			sd.getTime_Stamp() );
		}
		System.out.println("results array size:"+results.size());
		resultObject.put("rows", results);
		
		// Send the http response to the client
		out.print(resultObject);
	}
	
	

	/**
	 * Controller used to manage the insertion of new records in the State_Details table. Each record sent by the client
	 * is stored in the State_Details table and, if everything works as expected, the client receives a positive acknowledge.
	 * 
	 * @param resp HTTP response
	 * @param newStateDetailsModel Object that will contain client's uuid and the list of records to be inserted
	 */
	@RequestMapping(value = "/JSonInsertNewStateDetails", method = RequestMethod.POST)
	public void jsonAddNewStateDetails(HttpServletResponse resp,@RequestBody JSonNewStateDetailsModel newStateDetailsModel){
		System.out.println("JSonInsertNewStateDetails");
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
        if(newStateDetailsModel.getUuid()==null){
        	System.out.println("uuid null");
			obj.put("success", false);
			out.print(obj);
			return;
		}else // If no session exists associated to client uuid return success=false to the client
			if(!SessionManager.checkSession(newStateDetailsModel.getUuid())){
				System.out.println("session expired");
				obj.put("success", false);
				out.print(obj);
				return;
		}
        System.out.println("JSON add new state details- uuid:"+newStateDetailsModel.getUuid());
        State_Details sd;
        System.out.println("ricevute num rows:"+newStateDetailsModel.getRows().size());
        System.out.println("contenuto:\n"+newStateDetailsModel.toString());
        for(JSonStateDetailsModel n : newStateDetailsModel.getRows()){
        	System.out.println("user_id:"+n.getUser_id()+" state_id:"+n.getState_id()+" timestamp:"+n.getTimestamp());
        	sd = new State_Details();
        	sd.setUser_Id(Long.parseLong(n.getUser_id()));
        	sd.setState_Id(n.getState_id());
        	Date d=new Date(Long.parseLong(n.getTimestamp()));
//			try {
//				d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(n.getTimestamp());
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return;
//			}
        	sd.setTime_Date(d);
        	sd.setTime_Stamp(d);
        	stateDao.addStateDetails(sd);
        }
        obj.put("success", true);
        out.print(obj);
	}
}
