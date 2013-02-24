package com.ssmvc.server;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ssmvc.server.dao.IUserDao;
import com.ssmvc.server.model.User;

@Controller
@SessionAttributes("uuid")
public class HomeController {

	@Autowired
	private IUserDao userDao;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		String out;
		//List<Contact> contacts = contactDao.findAllWithDetail();
		//out=listContactsWithDetail(contacts);
		List<User> users = userDao.findAllUsersWithStates();
		//out=listUsersWithStateDetails(users);
		String s = userDao.findAllUser_Roles();
		System.out.println(s);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("uuid", "");
		
		return "home";
	}
}

