package com.ssmvc.server.dao;

import java.util.List;

import com.ssmvc.server.model.State;
import com.ssmvc.server.model.State_Details;

public interface IStateDao {

	
	public List<State> getAllStates();
	
	public void deleteState(State state);
	
	public void addState(State state);
	
	public void addStateDetails(State_Details state_details);

	public void addStateDetails(State s, long userId);
	
	public List<State_Details> getAllStateDetails();
	
	public List<State_Details> getAllStateDetailsByUserId(String user_id);
	
	public List<State> getStatesFromTimestamp(String timestamp);
	
	public List<State_Details> getStateDetailsByIdFromTimestamp(String user_id,String timestamp);
}
