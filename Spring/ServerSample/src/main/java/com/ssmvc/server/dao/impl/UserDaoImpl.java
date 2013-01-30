package com.ssmvc.server.dao.impl;


import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.ssmvc.server.dao.IUserDao;
import com.ssmvc.server.formModel.loginResponse;
import com.ssmvc.server.model.User;
import com.ssmvc.server.model.User_Role;

public class UserDaoImpl implements IUserDao{
	private Log log = LogFactory.getLog(UserDaoImpl.class);

	private Session session;
	private SessionFactory sessionFactory;
	
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		session=sessionFactory.openSession();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Transactional(readOnly = true)
	public List<User> findAllUsers() {
		return session.createQuery("from User c")
				.list();
	}

	public List<User> findAllUsersWithStates() {
		return session.getNamedQuery("User.findWithStateDetails").list();
	}

	
	@Transactional(readOnly = true)
	public loginResponse checkCredentials(String username, String password) {
		Query q=session.getNamedQuery("User.checkCredentials");
		q.setParameter("username", username);
		q.setParameter("password", password);
		List<User> user = (List<User>)q.list();
		loginResponse res=new loginResponse();
		if(user.size()==0){		// Wrong credentials
			res.setSuccess(false);
		}else{					// Right credentials
			res.setSuccess(true);
			res.setName(user.get(0).getFirstName());
			res.setSurname(user.get(0).getLastName());
			res.setID(user.get(0).getId());
			Set<User_Role> userRoleSet = user.get(0).getUserRole();
			res.setUserRoleSet(userRoleSet);
		}
		return res;
	}
	
	@Transactional(readOnly = true)
	public String findAllUser_Roles(){
		List<User_Role> list = session.createQuery("select distinct u from User_Role u left join fetch u.role r" +
				"left join fetch u.user us").list();
		String s="";
		System.out.println("DONE");
		for(User_Role ur:list){
			s+="USer:"+ur.getUser().getFirstName()+" Role:"+ur.getRole().getDescription()+" from:"+ur.getFromDate()+"\n";
		}
		return s;
	}

}
