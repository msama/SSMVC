package com.ssmvc.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name="state_details")
@NamedQueries({
	@NamedQuery(name="StateDetails.getStateDetails",
			query="select sd from State_Details sd left join fetch sd.state left join fetch sd.user"),
	@NamedQuery(name="StateDetails.getStateDetailsByUserId",
	query="select sd from State_Details sd left join fetch sd.user where user_id=:user_id"),
	@NamedQuery(name="StateDetails.getStateDetailsFromTimestamp",
	query="select distinct sd from State_Details sd left join fetch sd.state left join fetch sd.user " +
			"where time_stamp>:timestamp"),
	@NamedQuery(name="StateDetails.getStateDetailsByIdFromTimestamp",
	query="select distinct sd from State_Details sd " +
			"where time_stamp>:timestamp and user_id=:user_id")
})
public class State_Details implements Serializable{
	
	private Date Time_Date;
	private long User_Id;
	private String State_Id;
	private User user;
	private State state;
	private Date time_stamp;

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
	public long getUser_Id(){
		return User_Id;
	}

	public void setUser_Id(long User_Id){
		this.User_Id=User_Id;
	}
	
	@Column(name = "STATE_ID")
	public String getState_Id(){
		return State_Id;
	}
	
	public void setState_Id(String State_Id){
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
	
	@Column(name="TIME_STAMP")
	public Date getTime_Stamp(){
		return time_stamp;
	}
	
	public void setTime_Stamp(Date time_stamp){
		this.time_stamp=time_stamp;
	}
	
	
}


