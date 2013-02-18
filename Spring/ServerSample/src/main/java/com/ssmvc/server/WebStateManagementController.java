package com.ssmvc.server;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ssmvc.server.dao.IStateDao;
import com.ssmvc.server.formModel.StateFormModel;
import com.ssmvc.server.formModel.NewStateModel;
import com.ssmvc.server.model.State;
import com.ssmvc.server.model.State_Details;
import com.ssmvc.server.utils.SessionManager;

@Controller
@SessionAttributes({"uuid"})
public class WebStateManagementController {
	
	@Autowired
	private IStateDao stateDao;

	/**
	 * Add new state to the database.
	 * @param model
	 * @param newStateModel
	 * @param sess
	 * @param sessionStatus
	 * @return
	 */
	@RequestMapping(value = "/addNewState", method = RequestMethod.POST)
	public ModelAndView addNewState(@ModelAttribute("newstate") NewStateModel newStateModel,
			@ModelAttribute(value="uuid") String sess, SessionStatus sessionStatus){
		boolean stateDoesNotExist = true;
		System.out.println("New state description:"+newStateModel.getDescription());
		System.out.println("Session:"+sess);
		System.out.println(SessionManager.sessionToString());
		ModelAndView modelAndView;
		if(!SessionManager.checkSession(sess)){
			sessionStatus.setComplete();
			modelAndView = new ModelAndView("redirect:login");
			modelAndView.addObject("Success", "Session Expired. Please Log In again!");
			return modelAndView;
		}
		modelAndView = new ModelAndView("redirect:AdminStatesManagement");
		List<State> stateList = stateDao.getAllStates();
		for(State s:stateList){
			if(s.getDescription().equals(newStateModel.getDescription())){
				//modelAndView.addObject("StateAlreadyExist", true);
				stateDoesNotExist=false;
			}
		}
		if(stateDoesNotExist){
			State s = new State();
			s.setId(UUID.randomUUID().toString());
			s.setDescription(newStateModel.getDescription());
			s.setTime_Stamp(null);
			stateDao.addState(s);
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/DeleteState", method = RequestMethod.POST)
	public ModelAndView deleteState(ModelMap model, @ModelAttribute("deleteStateModel") StateFormModel deleteStateModel,
			@ModelAttribute(value="uuid") String sess, SessionStatus sessionStatus){
		System.out.println("delete state description:"+deleteStateModel.getDescription()+" id:"+deleteStateModel.getId());
		System.out.println("Session:"+sess);
		if(!SessionManager.checkSession(sess)){
			sessionStatus.setComplete();
			model.put("Success", "Session Expired. Please Log In again!");
			return new ModelAndView("login", model);
		}
		System.out.println(SessionManager.sessionToString());
		List<State> stateList = stateDao.getAllStates();
		for(State s:stateList){
			if(s.getId().equals(deleteStateModel.getId())){
				System.out.println("Trovato id="+s.getId());
				stateDao.deleteState(s);
			}
				
		}
		return new ModelAndView("redirect:AdminStatesManagement");
	}
	
	
	/**
	 * Add a user state details to State_Details table
	 * @param model
	 * @param sendStateModel
	 * @param sess
	 * @param sessionStatus
	 * @return
	 */
	@RequestMapping(value = "/SendState", method = RequestMethod.POST)
	public String sendState(ModelMap model, @ModelAttribute("sendStateModel") StateFormModel sendStateModel,
			@ModelAttribute(value="uuid") String sess, SessionStatus sessionStatus){
		System.out.println("send state description:"+sendStateModel.getDescription()+" id:"+sendStateModel.getId());
		System.out.println("Session:"+sess);
		if(!SessionManager.checkSession(sess)){
			sessionStatus.setComplete();
			model.put("Success", "Session Expired. Please Log In again!");
			return "login";
		}
		System.out.println(SessionManager.sessionToString());
		List<State> stateList = stateDao.getAllStates();
		for(State s:stateList){
			if(s.getId().equals(sendStateModel.getId())){
				System.out.println("Trovato id="+s.getId());
				stateDao.addStateDetails(s,SessionManager.getUserId(sess));
				break;
			}
				
		}
		stateList = stateDao.getAllStates();
		model.put("StateList", stateList);
		model.put("sendStateModel", new StateFormModel());
		return "welcome";
	}
	
	/**
	 * Get all records from State_Details table. The id of the user is taken from the sessionManager using uuid session parameter
	 * @param model
	 * @param sess
	 * @param sessionStatus
	 * @return
	 */
	@RequestMapping(value = "/ShowStates", method = RequestMethod.GET)
	public String showState(ModelMap model,
			@ModelAttribute(value="uuid") String sess, SessionStatus sessionStatus){
		if(!SessionManager.checkSession(sess)){
			sessionStatus.setComplete();
			model.put("Success", "Session Expired. Please Log In again!");
			return "login";
		}
		List<State_Details> stateDetails = stateDao.getAllStateDetails();
		List<State_Details> resultStates = new LinkedList<State_Details>() ;
		long userid=SessionManager.getUserId(sess);
		System.out.println("userid:"+userid);
		System.out.println("size stateDetails:"+stateDetails.size());
		for(State_Details sd:stateDetails){
			if(sd.getUser_Id()==userid)resultStates.add(sd);
		}
		System.out.println("size resultStates:"+resultStates.size());
		for(State_Details s: resultStates){
			System.out.println(s.toString());
		}
		model.put("StateDetailsList", resultStates);
		return "StateDetails";
	}
	
	
	@RequestMapping(value = "/SendStateForm", method = RequestMethod.GET)
	public String sendStateForm(ModelMap model){
		List<State> stateList = stateDao.getAllStates();
		model.put("StateList", stateList);
		return "welcome";
	}
	
	@RequestMapping(value = "/AdminStatesManagement", method = RequestMethod.GET)
	public String adminStateManagement(ModelMap model, @ModelAttribute(value="uuid") String sess){
		model.addAttribute("Admin", true);
		List<State> stateList = stateDao.getAllStates();
		model.put("StateList", stateList);
		model.put("deleteStateModel", new StateFormModel());
		model.put("newstate", new NewStateModel());
		return "welcome";
		
	}
}
