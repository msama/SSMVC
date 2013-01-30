package com.ssmvc.server.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;;

@Entity
@Table(name="state")
@NamedQueries({
	@NamedQuery(name="State.getAllStates",
			query="select distinct s from State s left join fetch s.stateDetails"),
	@NamedQuery(name="State.getStatesFromTimestamp",
			query="select distinct s from State s where time_stamp>:timestamp")	
})
public class State implements Serializable{
	private String id;
	private String description;
	//private int version;
	private Date time_stamp;

	private Set<State_Details> stateDetails = new HashSet<State_Details>();
	
	@Id
	//@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	public String getId() {
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}
	
	@Column(name="DESCRIPTION")
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
	
//	@Column(name="VERSION")
//	public int getVersion() {
//		return version;
//	}
//
//	public void setVersion(int version) {
//		this.version = version;
//	}
	
	@Column(name="TIME_STAMP")
	public Date getTime_Stamp(){
		return time_stamp;
	}
	
	public void setTime_Stamp(Date time_stamp){
		this.time_stamp=time_stamp;
	}
	
}
