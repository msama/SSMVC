package com.ssmvc.server;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssmvc.server.dao.IUserDao;
import com.ssmvc.server.formModel.loginModel;
import com.ssmvc.server.formModel.loginResponse;
import com.ssmvc.server.model.User_Role;
import com.ssmvc.server.utils.SessionManager;
import com.ssmvc.server.utils.Utility;


@Controller
public class JSonLoginController {

	private static final Logger logger = LoggerFactory.getLogger(JSonLoginController.class);
	
	@Autowired
	private IUserDao userDao;
	
	@RequestMapping( value = "/loginJson",method = RequestMethod.POST)
	public /*@ResponseBody loginResponse*/ void JsonLogin(HttpServletResponse resp,@RequestBody loginModel login) {
		loginResponse response=userDao.checkCredentials(login.getUsername(),login.getPassword());
		System.out.println("Result="+response.isSuccess());
		System.out.println("Received:"+login.getUsername()+" "+login.getPassword());
		if(response.isSuccess()){
			String uuid = Utility.generateUUID();
			SessionManager.createNewSession(uuid, response.getID());
			response.setUUID(uuid);			
		}
		JSONObject jsonObject =  new JSONObject();
		jsonObject.put("success", response.isSuccess());
		jsonObject.put("name", response.getName());
		jsonObject.put("surname", response.getSurname());
		jsonObject.put("id", response.getID());
		jsonObject.put("uuid", response.getUUID());
		JSONArray list = new JSONArray();
		Iterator<User_Role> iterator=response.getUserRoleSet().iterator();
		User_Role ur;
		while(iterator.hasNext()){
			ur=iterator.next();
			list.add(ur.getRole().getDescription());
		}
		jsonObject.put("roles", list);
		resp.setCharacterEncoding("utf8");
        resp.setContentType("application/json"); 
        try {
			PrintWriter out = resp.getWriter();
			out.print(jsonObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
	}
	
}
