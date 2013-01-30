package com.ssmvc.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssmvc.server.formModel.logoutModel;
import com.ssmvc.server.formModel.logoutResponse;
import com.ssmvc.server.utils.SessionManager;

@Controller
public class JSonLogoutController {
	
	@RequestMapping( value = "/logoutJson",method = RequestMethod.POST)
	public @ResponseBody logoutResponse logout(@RequestBody logoutModel logout){
		System.out.println("Received:"+logout.getUuid());
		logoutResponse response = new logoutResponse();
		response.setSuccess(true);
		SessionManager.deleteSession(logout.getUuid());
		return response;
	}
}
