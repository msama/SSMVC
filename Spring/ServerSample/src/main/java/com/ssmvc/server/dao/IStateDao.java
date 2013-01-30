package com.ssmvc.server.dao;

import java.util.List;

import com.ssmvc.server.model.State;
import com.ssmvc.server.model.State_Details;

public interface IStateDao {

	
	public List<State> getAllStates();
	
	public void deleteState(State state);
	
	public void addState(State state);

	public void addStateDetails(State s, long userId);
	
	public List<State_Details> getStateDetails();
	
	public List<State> getStatesFrom(String timestamp);
}
