package com.ssmvc.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class State_Details_key implements Serializable {
	private Date Time_Date;
	private int User_Id;
	private int State_Id;
	
	public State_Details_key(){}
	
	public State_Details_key(Date Time_Date,int User_Id,int State_Id){
		this.Time_Date=Time_Date;
		this.User_Id=User_Id;
		this.State_Id=State_Id;
	}
	
	public void setTime_Date(Date Time_Date){
		this.Time_Date=Time_Date;
	}
	
	public Date getTime_Date(){
		return Time_Date;
	}
	
	public void setUser_Id(int User_Id){
		this.User_Id=User_Id;
	}
	
	public int getUser_Id(){
		return User_Id;
	}
	
	public void setState_Id(int State_Id){
		this.State_Id=State_Id;
	}
	
	public int getState_Id(){
		return State_Id;
	}
	
	public boolean equals(Object obj){
		if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;
        
        State_Details_key s = (State_Details_key) obj;
        if(this.State_Id == s.getState_Id() &&
        	this.User_Id == s.getUser_Id() &&
        	this.Time_Date.equals(s.getTime_Date())){
        	return true;
        }
        return false;
	}
	
	public int hashCode (){
		int h = (this.State_Id + 
				this.User_Id + 
				this.Time_Date.toString()).hashCode();
		return h;			
	}
}
