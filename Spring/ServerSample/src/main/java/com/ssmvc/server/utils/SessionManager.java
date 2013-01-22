package com.ssmvc.server.utils;

import java.security.Timestamp;
import java.util.HashMap;

public class SessionManager {
	
	private static HashMap<String, loginSession> sessionMap = new HashMap<String, loginSession>();
	private static final long expirationTime = 3600000;
	
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
			//if(System.currentTimeMillis()-s.getSessionTime()<expirationTime){
				s.setSessionTime(System.currentTimeMillis());  // update session time
				sessionMap.put(uuid, s);
//				return true;
//			}
		}
		return false;
	}
	
	public static void deleteSession(String uuid){
		if(sessionMap.containsKey(uuid))sessionMap.remove(uuid);
	}

}
