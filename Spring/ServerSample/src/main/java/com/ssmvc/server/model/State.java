package com.ssmvc.server.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;;

@Entity
@Table(name="state")
public class State {
	private Long id;
	private String description;
	private Set<State_Details> stateDetails = new HashSet<State_Details>();
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description=description;
	}
	
	@OneToMany(mappedBy = "state", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<State_Details> getStateDetails(){
		return stateDetails;
	}
	
	public void setStateDetails(Set<State_Details> stateDetails){
		this.stateDetails=stateDetails;
	}
	
	public void addStateDetails(State_Details stateDetails){
		stateDetails.setState(this);
		getStateDetails().add(stateDetails);
	}
	
	public void removeStateDetails(State_Details stateDetails){
		getStateDetails().remove(stateDetails);
	}
	
}
