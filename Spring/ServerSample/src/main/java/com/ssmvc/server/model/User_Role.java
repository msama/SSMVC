package com.ssmvc.server.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_role")
public class User_Role {
	private int Role_Id;
	private int User_Id;
	private Date fromDate;
	private Date toDate;
	
	private Role role;
	private User user;
	
	@Id
    @AttributeOverrides({
    @AttributeOverride(name = "User_Id",
    column = @Column(name="USER_ID")),
    @AttributeOverride(name = "Role_Id",
    column = @Column(name="ROLE_ID")),
    @AttributeOverride(name = "Time_Date",
    column = @Column(name="FROM_DATE"))
    })
	
	@Column(name = "ROLE_ID")
	public int getRole_Id() {
		return Role_Id;
	}
	
	public void setRole_Id(int role_Id) {
		Role_Id = role_Id;
	}
	
	@Column(name = "USER_ID")
	public int getUser_Id() {
		return User_Id;
	}
	
	public void setUser_Id(int user_Id) {
		User_Id = user_Id;
	}
	
	@Column(name = "FROM_DATE")
	public Date getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	@Column(name = "TO_DATE")
	public Date getToDate() {
		return toDate;
	}
	
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	@ManyToOne
	@JoinColumn(name="ROLE_ID", insertable = false, updatable = false)
	public Role getRole(){
		return this.role;
	}
	
	public void setRole(Role role){
		this.role=role;
	}
	
	@ManyToOne
	@JoinColumn(name="USER_ID", insertable = false, updatable = false)
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user=user;
	}
}
