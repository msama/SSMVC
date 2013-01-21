package com.ssmvc.server;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ssmvc.server.dao.IUserDao;
import com.ssmvc.server.formModel.loginModel;
import com.ssmvc.server.formModel.loginResponse;
import com.ssmvc.server.model.User;
import com.ssmvc.server.utils.SessionManager;
import com.ssmvc.server.utils.Utility;
import com.ssmvc.server.utils.loginSession;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private IUserDao userDao;
	private HttpSession session;
	
	
	@RequestMapping( value = "/loginJson",method = RequestMethod.POST)
	public @ResponseBody loginResponse login(@RequestBody loginModel login) {
		loginResponse response=userDao.checkCredentials(login.getUsername(),login.getPassword());
		System.out.println("Result="+response.isSuccess());
		System.out.println("Received:"+login.getUsername()+" "+login.getPassword());
		if(response.isSuccess()){
			String uuid = Utility.generateUUID();
			SessionManager.createNewSession(uuid, response.getID());
			response.setUUID(uuid);			
		}
		return response;
		
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		String out;
		//List<Contact> contacts = contactDao.findAllWithDetail();
		//out=listContactsWithDetail(contacts);
		List<User> users = userDao.findAllUsersWithStates();
		//out=listUsersWithStateDetails(users);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
}
