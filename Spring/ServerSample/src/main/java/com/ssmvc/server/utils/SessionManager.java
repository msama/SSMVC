package com.ssmvc.server.utils;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SessionManager {
	
	private static HashMap<String, loginSession> sessionMap = new HashMap<String, loginSession>();
	private static final long expirationTime = 300000;
	
	/*
	 * Create a new session.
	 */
	public static void createNewSession(String uuid, Long id){
		loginSession s = new loginSession();
		s.setId(id);
		s.setSessionTime(System.currentTimeMillis());
		if(sessionMap.containsKey(uuid))sessionMap.remove(uuid);
		sessionMap.put(uuid, s);
	}
	
	/*
	 * Check if a session identified by uuid has already been activated and if it is not expired.
	 */
	public static boolean checkSession(String uuid){
		if(sessionMap.containsKey(uuid)){
			loginSession s = sessionMap.get(uuid);
//			if(System.currentTimeMillis()-s.getSessionTime()<expirationTime){
				sessionMap.remove(uuid);
				s.setSessionTime(System.currentTimeMillis());  // update session time
				sessionMap.put(uuid, s);
				return true;
//			}else{
//				sessionMap.remove(uuid);
//				return false;
//			}
		}
		return false;
	}
	
	public static void deleteSession(String uuid){
		if(sessionMap.containsKey(uuid))sessionMap.remove(uuid);
	}
	
	public static String sessionToString(){
		ArrayList<loginSession> list = new ArrayList<loginSession>();
		Set set = sessionMap.entrySet();
		Iterator i = set.iterator();
		String s="";
		loginSession sessionObj;
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			sessionObj = (loginSession) me.getValue();
			s+=sessionObj.getId() + " Has uuid:"+ me.getKey() +"\n";
		}
		return s;
	}
	
	public static Long getUserId(String uuid){
		if(sessionMap.containsKey(uuid)){
			return sessionMap.get(uuid).getId();
		}
		return null;
	}

}
