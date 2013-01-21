package com.ssmvc.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Embeddable
public class User_Role_key implements Serializable{
	private int Role_Id;
	private int User_Id;
	private Date fromDate;
	
	public User_Role_key(int role_Id, int user_Id, Date fromDate) {
		Role_Id = role_Id;
		User_Id = user_Id;
		this.fromDate = fromDate;
	}

	
	public int getRole_Id() {
		return Role_Id;
	}

	public void setRole_Id(int role_Id) {
		Role_Id = role_Id;
	}

	public int getUser_Id() {
		return User_Id;
	}

	public void setUser_Id(int user_Id) {
		User_Id = user_Id;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	public boolean equals(Object obj){
		if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;
        
        User_Role_key s = (User_Role_key) obj;
        if(this.Role_Id == s.getRole_Id() &&
        	this.User_Id == s.getUser_Id() &&
        	this.fromDate.equals(s.getFromDate())){
        	return true;
        }
        return false;
	}
	
	public int hashCode (){
		int h = (this.Role_Id + 
				this.User_Id + 
				this.fromDate.toString()).hashCode();
		return h;		
	}
	
}
