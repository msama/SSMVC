package com.ssmvc.server.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.ssmvc.server.dao.IUserDao;
import com.ssmvc.server.formModel.loginResponse;
import com.ssmvc.server.model.User;

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
		List<User> user=session.createQuery("select distinct u from User u where u.username='"+username+"' and u.password='"+password+"'").list();
		loginResponse res=new loginResponse();
		if(user.size()==0){		// Wrong credentials
			res.setSuccess(false);
		}else{					// Right credentials
			res.setSuccess(true);
			res.setName(user.get(0).getFirstName());
			res.setSurname(user.get(0).getLastName());
			res.setID(user.get(0).getId());
		}
		return res;
		
	}

}
