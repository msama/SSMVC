package com.ssmvc.server.model;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name="state_details")
public class State_Details {
	
	private Date Time_Date;
	private int User_Id;
	private int State_Id;
	private User user;
	private State state;
	
	@Id
    @AttributeOverrides({
    @AttributeOverride(name = "User_Id",
    column = @Column(name="USER_ID")),
    @AttributeOverride(name = "State_Id",
    column = @Column(name="STATE_ID")),
    @AttributeOverride(name = "Time_Date",
    column = @Column(name="TIME_DATE"))
    })
	
	@Column(name = "TIME_DATE")
	public Date getTime_Date(){
		return Time_Date;
	}
	
	public void setTime_Date(Date Time_Date){
		this.Time_Date=Time_Date;
	}
	
	@Column(name = "USER_ID")
	public int getUser_Id(){
		return User_Id;
	}

	public void setUser_Id(int User_Id){
		this.User_Id=User_Id;
	}
	
	@Column(name = "STATE_ID")
	public int getState_Id(){
		return State_Id;
	}
	
	public void setState_Id(int State_Id){
		this.State_Id=State_Id;
	}
	
	@ManyToOne
	@JoinColumn(name="USER_ID", insertable = false, updatable = false)
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user=user;
	}
	
	@ManyToOne
	@JoinColumn(name="STATE_ID", insertable = false, updatable = false)
	public State getState(){
		return this.state;
	}
	
	public void setState(State state){
		this.state=state;
	}
	
	public String toString(){
		return "State Details - State_Id:"+this.State_Id+" User_Id:"+this.User_Id+
				" Date:"+this.Time_Date;
	}
}


