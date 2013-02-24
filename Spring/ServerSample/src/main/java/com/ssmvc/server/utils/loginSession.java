package com.ssmvc.server.utils;

import java.io.Serializable;


public class loginSession implements Serializable {

	private Long id;
	private Long sessionTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSessionTime() {
		return sessionTime;
	}
	public void setSessionTime(Long sessionTime) {
		this.sessionTime = sessionTime;
	}
	
	
	
}
