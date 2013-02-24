package com.ssmvc.server.dao.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import com.ssmvc.server.dao.IStateDao;
import com.ssmvc.server.model.State;
import com.ssmvc.server.model.State_Details;

public class StateDaoImpl implements IStateDao{
	
	private Session session;
	private SessionFactory sessionFactory;
	private Transaction tx;
	
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Transactional(readOnly = true)
	public List<State> getAllStates() {
		List<State> l;
		openSession();
		l= (List<State>)session.getNamedQuery("State.getAllStates").list();
		closeSession();
		return l;
	}

	@Transactional(readOnly = false)
	public void deleteState(State state) {
		openSession();
		Query query = session.createQuery("delete from State_Details where state_id=:state_id");
		query.setString("state_id", state.getId());
		int rowcount=query.executeUpdate();
		System.out.println("Rows affected in state_details="+rowcount);
		query=session.createQuery("delete from State where id=:state_id");
		query.setString("state_id", state.getId());
		rowcount=query.executeUpdate();
		System.out.println("Rows affected in state="+rowcount);
		closeSession();
	}

	@Transactional(readOnly = false)
	public void addState(State state) {
		openSession();
		if(state.getTime_Stamp()==null){
			Date date= new Date();
			state.setTime_Stamp(date);
		}
		session.saveOrUpdate(state);
		closeSession();
	}

	
	public void addStateDetails(State s, long userId) {
		openSession();
		State_Details sd = new State_Details();
		sd.setState_Id(s.getId());
		sd.setUser_Id(userId);
		Date date= new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		sd.setTime_Date(timestamp);
		sd.setTime_Stamp(timestamp);
		session.saveOrUpdate(sd);
		closeSession();
	}
	
	public void addStateDetails(State_Details state_details){
		openSession();
		session.saveOrUpdate(state_details);
		closeSession();
	}

	public List<State_Details> getAllStateDetails() {
		openSession();
		Query q=session.getNamedQuery("StateDetails.getStateDetails");
		List<State_Details> sd = (List<State_Details>) q.list();
		closeSession();
		return sd;
	}

	public List<State> getStatesFromTimestamp(String timestamp) {
		List<State> res = new ArrayList<State>();
		//SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		openSession();
		Query q=session.getNamedQuery("State.getStatesFromTimestamp");
//		try {
			//Date d= f.parse(timestamp);
			System.out.println("qua timestamp:"+timestamp);
			q.setString("timestamp", timestamp);
			res = q.list();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		closeSession();
		return res;
	}

	@Override
	public List<State_Details> getStateDetailsByIdFromTimestamp(String user_id, String timestamp) {
		List<State_Details> res = new ArrayList<State_Details>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		openSession();
		Query q=session.getNamedQuery("StateDetails.getStateDetailsByIdFromTimestamp");
//		try {
			//Date d= f.parse(timestamp);
			q.setString("timestamp", timestamp);
			q.setString("user_id", user_id);
			res = q.list();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		closeSession();
		return res;
	}

	@Override
	public List<State_Details> getAllStateDetailsByUserId(String user_id) {
		openSession();
		Query q=session.getNamedQuery("StateDetails.getStateDetailsByUserId");
		q.setString("user_id", user_id);
		List<State_Details> res = q.list();
		closeSession();
		return res;
	}
	
	private void openSession(){
		session=sessionFactory.openSession();
		tx=session.beginTransaction();
	}
	
	private void closeSession(){
		tx.commit();
		session.close();
	}

}
