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
import com.ssmvc.server.formModel.JSonNewStateDetailsModel;
import com.ssmvc.server.formModel.JSonStateDetailsModel;
import com.ssmvc.server.model.State_Details;
import com.ssmvc.server.utils.SessionManager;

@Controller
public class JSonStateDetailsController {
	@Autowired
	private IStateDao stateDao;
	
	
	/**
	 * Controller used to manage State_Details table synchronization. 
	 * @param resp
	 * @param newStateDetailsReqModel
	 */
	@RequestMapping(value = "/JSonUpdateStateDetails", method = RequestMethod.POST)
	public void jsonUpdateStateDetails(HttpServletResponse resp,@RequestBody JSonNewStateDetailsModel newStateDetailsModel){
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
		if(newStateDetailsModel.getUuid()==null){
			obj.put("success", false);
			out.print(obj);
			return;
		}else // If no session exists associated to client uuid return success=false to the client
			if(!SessionManager.checkSession(newStateDetailsModel.getUuid())){
				obj.put("success", false);
				out.print(obj);
				return;
		}
		System.out.println("uuid:"+newStateDetailsModel.getUuid());
		System.out.println("timestamp:"+newStateDetailsModel.getTimestamp());
		System.out.println("user_id:"+SessionManager.getUserId(newStateDetailsModel.getUuid()));
		List<State_Details> state_details;
		// If no timestamp is provided by the client
		if(newStateDetailsModel.getTimestamp()==null){
			if(SessionManager.getUserId(newStateDetailsModel.getUuid())==null)
				state_details = stateDao.getAllStateDetails();	// get all records from State_Details table
			else
				state_details = stateDao.getAllStateDetailsByUserId(String.valueOf(SessionManager.getUserId(newStateDetailsModel.getUuid())));
		}else{
			// else get records from State_Details table having timestamp > latest client timestamp
			state_details= stateDao.getStateDetailsByIdFromTimestamp(String.valueOf(SessionManager.getUserId(newStateDetailsModel.getUuid())),
					newStateDetailsModel.getTimestamp());
		}
		
		// Add each record to a JSon Object
		JSONObject resultObject = new JSONObject();
		JSONArray results =  new JSONArray();
		JSONObject row;
		for(State_Details sd:state_details){
			row = new JSONObject();
			row.put("USER_ID", sd.getUser_Id());
			row.put("STATE_ID", sd.getState_Id());
			row.put("TIME_STAMP", sd.getTime_Stamp().toString());
			results.add(row);
			
			System.out.println("sent user_id:"+sd.getUser_Id() + " state_id:" +sd.getState_Id()+"  time_stamp:"+
			sd.getTime_Stamp().getTime() );
		}
		System.out.println("results array size:"+results.size());
		resultObject.put("rows", results);
		
		State_Details sd;
        System.out.println("ricevute num rows:"+newStateDetailsModel.getRows().size());
        System.out.println("contenuto:\n"+newStateDetailsModel.toString());
        for(JSonStateDetailsModel n : newStateDetailsModel.getRows()){
        	System.out.println("user_id:"+n.getUser_id()+" state_id:"+n.getState_id()+" timestamp:"+n.getTimestamp());
        	sd = new State_Details();
        	sd.setUser_Id(Long.parseLong(n.getUser_id()));
        	sd.setState_Id(n.getState_id());
        	Date d=new Date(Long.parseLong(n.getTimestamp()));
        	sd.setTime_Date(d);
        	sd.setTime_Stamp(d);
        	stateDao.addStateDetails(sd);
        }
        
		resultObject.put("success", true);

		// Send the http response to the client
		out.print(resultObject);
	}
}
