package com.ssmvc.server.dao;

import java.util.List;

import com.ssmvc.server.formModel.loginResponse;
import com.ssmvc.server.model.User;

public interface IUserDao {
	
	public List<User> findAllUsers();
	
	public List<User> findAllUsersWithStates();
	
	public loginResponse checkCredentials(String username, String password);
	
	public String findAllUser_Roles();

}
