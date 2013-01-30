package com.ssmvc.server;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ssmvc.server.dao.IStateDao;
import com.ssmvc.server.dao.IUserDao;
import com.ssmvc.server.formModel.StateFormModel;
import com.ssmvc.server.formModel.loginModel;
import com.ssmvc.server.formModel.loginResponse;
import com.ssmvc.server.model.State;
import com.ssmvc.server.model.User_Role;
import com.ssmvc.server.utils.SessionManager;
import com.ssmvc.server.utils.Utility;

@Controller
@SessionAttributes({"uuid","loggedIn"})
public class WebLoginController {

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IStateDao stateDao;
	
	@RequestMapping(value = "/WebLogin", method = RequestMethod.GET)
	public String WebLoginGet(Locale locale, Model model) {
		model.addAttribute("loggedIn", "false");
		return "login";
	}
	
	
	@RequestMapping(value = "/WebLogin", method = RequestMethod.POST)
	public String WebLoginPost(ModelMap model, @ModelAttribute("loginmodel") loginModel loginM,
			SessionStatus sessionStatus) {
		System.out.println("user:"+loginM.getUsername());
		System.out.println("Pass:"+loginM.getPassword());
		List<State> stateList = stateDao.getAllStates();
		model.put("StateList", stateList);
		loginResponse response=userDao.checkCredentials(loginM.getUsername(), loginM.getPassword());
		if(response.isSuccess()){
			Iterator<User_Role> userRoleIterator = response.getUserRoleSet().iterator();
			User_Role userRole;
			while(userRoleIterator.hasNext()){
				userRole=userRoleIterator.next();
				if(userRole.getRole().getDescription().equals("Administrator")){
					System.out.println("ADMIN");
					model.addAttribute("Admin",true);
					model.put("deleteStateModel", new StateFormModel());
				}else if(userRole.getRole().getDescription().equals("User")){
					model.put("sendStateModel", new StateFormModel());
				}
			}
			String sess = Utility.generateUUID();
			model.put("uuid", sess);
			SessionManager.createNewSession(sess, response.getID());
			System.out.println("GENERATED:"+sess);
			System.out.println(SessionManager.sessionToString());
			
			return "welcome";
		}
		else{
			model.addAttribute("Result", "Wrong Username or Password!");
			System.out.println(SessionManager.sessionToString());
			return "login";
		}
	}
	
	
	@RequestMapping(value = "/testSession", method = RequestMethod.GET)
	public String testSession(Model model,@ModelAttribute(value="uuid") String sess) {
		System.out.println("SESSION:"+sess);
		return "welcome";
	}
	
}
