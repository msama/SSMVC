package com.ssmvc.server.formModel;


import java.util.Set;

import com.ssmvc.server.model.User_Role;

public class loginResponse {

	private boolean success;
	private String name;
	private String surname;
	private Long ID;
	private String UUID;
	private Set<User_Role> userRoleSet;
	

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}
	
	public void setUserRoleSet(Set<User_Role> userRoleSet){
		this.userRoleSet=userRoleSet;
	}
	
	public Set<User_Role> getUserRoleSet(){
		return this.userRoleSet;
	}
}
