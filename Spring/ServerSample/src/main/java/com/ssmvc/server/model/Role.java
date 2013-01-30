package com.ssmvc.server.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="ROLE")
public class Role implements Serializable {

	private Long id;
	private String description;
	
	private Set<User_Role> userRole = new HashSet<User_Role>();
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description=description;
	}
	
	@OneToMany(mappedBy = "role", cascade= CascadeType.ALL, orphanRemoval = true)
	public Set<User_Role> getUserRole(){
		return this.userRole;
	}
	
	public void setUserRole(Set<User_Role> userRole){
		this.userRole=userRole;
	}
	
	public void addUserRole(User_Role userRole){
		userRole.setRole(this);
		getUserRole().add(userRole);
	}
	
	public void removeUserRole(User_Role userRole){
		getUserRole().remove(userRole);
	}
	
}
