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
import org.springframework.web.servlet.ModelAndView;

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
@SessionAttributes({"uuid"})
public class WebLoginController {

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IStateDao stateDao;
	
	@RequestMapping(value = "/WebLogin", method = RequestMethod.GET)
	public String WebLoginGet( Model model) {
		return "login";
	}
	
	
	@RequestMapping(value = "/WebLogin", method = RequestMethod.POST)
	public ModelAndView WebLoginPost(ModelMap model, @ModelAttribute("loginmodel") loginModel loginM,
			SessionStatus sessionStatus) {
		System.out.println("user:"+loginM.getUsername());
		System.out.println("Pass:"+loginM.getPassword());
		loginResponse response=userDao.checkCredentials(loginM.getUsername(), loginM.getPassword());
		ModelAndView modelAndView=new ModelAndView("login");
		if(response.isSuccess()){
			Iterator<User_Role> userRoleIterator = response.getUserRoleSet().iterator();
			User_Role userRole;
			while(userRoleIterator.hasNext()){
				userRole=userRoleIterator.next();
				if(userRole.getRole().getDescription().equals("Administrator")){
					System.out.println("ADMIN");
					modelAndView = new ModelAndView("redirect:Admin");
				}else if(userRole.getRole().getDescription().equals("User")){
					modelAndView = new ModelAndView("redirect:User");
				}
			}
			String sess = Utility.generateUUID();
			modelAndView.addObject("uuid", sess);
			SessionManager.createNewSession(sess, response.getID());
			System.out.println("GENERATED:"+sess);
			System.out.println(SessionManager.sessionToString());
			return modelAndView;
		}
		else{
			modelAndView = new ModelAndView("login");
			modelAndView.addObject("Result", "Wrong Username or Password!");
			System.out.println(SessionManager.sessionToString());
			return modelAndView;
		}
	}
	
	
	@RequestMapping(value = "/User", method = RequestMethod.GET)
	public String userLogin(ModelMap model) {
		List<State> stateList = stateDao.getAllStates();
		model.put("StateList", stateList);
		model.put("sendStateModel", new StateFormModel());
		return "welcome";
	}
	
	@RequestMapping(value = "/Admin", method = RequestMethod.GET)
	public String adminLogin(ModelMap model) {
		List<State> stateList = stateDao.getAllStates();
		model.put("StateList", stateList);
		model.addAttribute("Admin",true);
		model.put("deleteStateModel", new StateFormModel());
		return "welcome";
	}
	
}
