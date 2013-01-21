package com.ssmvc.server.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="user")
@NamedQueries({
	@NamedQuery(name="User.findWithStateDetails",
			query="select distinct u from User u left join fetch u.stateDetails sd")
})
public class User implements Serializable {
	private Long id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String username;
	private String password;
	
	private Set<State_Details> stateDetails = new HashSet<State_Details>();
	private Set<User_Role> userRole =  new HashSet<User_Role>();
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}
	
	@Column(name = "FIRST_NAME")
	public String getFirstName(){
		return this.firstName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	@Column(name = "LAST_NAME")
	public String getLastName(){
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "BIRTH_DATE")
	public Date getBirthDate() {
		return this.birthDate;
	}
	
	public void setBirthDate(Date birthDate){
		this.birthDate=birthDate;
	}
	
	@Column(name = "USERNAME")
	public String getUsername(){
		return this.username;
	}
	
	public void setUsername(String username){
		this.username=username;
	}
	
	@Column(name = "PASSWORD")
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
	
	public String toString(){
		return "User - Id:"+id+", First name:"+firstName+", Last name:"+lastName+
				", Birthdate:"+birthDate+", Username:"+username+", Password:"+password;
	}
	
	@OneToMany(mappedBy = "user", cascade= CascadeType.ALL, orphanRemoval = true)
	public Set<State_Details> getStateDetails(){
		return this.stateDetails;
	}
	
	public void setStateDetails(Set<State_Details> stateDetails){
		this.stateDetails=stateDetails;
	}
	
	public void addStateDetails(State_Details stateDetails){
		stateDetails.setUser(this);
		getStateDetails().add(stateDetails);
	}
	
	public void removeStateDetails(State_Details stateDetails){
		getStateDetails().remove(stateDetails);
	}
		
	@OneToMany(mappedBy = "user", cascade= CascadeType.ALL, orphanRemoval = true)
	public Set<User_Role> getUserRole(){
		return this.userRole;
	}
	
	public void setUserRole(Set<User_Role> userRole){
		this.userRole=userRole;
	}
	
	public void addUserRole(User_Role userRole){
		userRole.setUser(this);
		getUserRole().add(userRole);
	}
	
	public void removeUserRole(User_Role userRole){
		getUserRole().remove(userRole);
	}
}
