package com.ssmvc.server;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ssmvc.server.utils.SessionManager;

@Controller
@SessionAttributes("uuid")
public class WebLogoutController {

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String WebLogout(ModelMap model, @ModelAttribute(value="uuid") String uuid, SessionStatus sessionStatus){
		sessionStatus.setComplete();
		SessionManager.deleteSession(uuid);
		return "home";
	}

	
}
