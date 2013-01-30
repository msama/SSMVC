package com.ssmvc.server.dao.impl;

import java.sql.Timestamp;
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
		session=sessionFactory.openSession();
		l= (List<State>)session.getNamedQuery("State.getAllStates").list();
		session.close();
		return l;
	}

	@Transactional(readOnly = false)
	public void deleteState(State state) {
		System.out.println("id:"+state.getId());
		System.out.println("state_details:"+state.getStateDetails());
		session=sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		Query query = session.createQuery("delete from State_Details where state_id=:state_id");
		query.setString("state_id", state.getId());
		int rowcount=query.executeUpdate();
		System.out.println("Rows affected in state_details="+rowcount);
		query=session.createQuery("delete from State where id=:state_id");
		query.setString("state_id", state.getId());
		rowcount=query.executeUpdate();
		System.out.println("Rows affected in state="+rowcount);
		tx.commit();
		session.close();
	}

	@Transactional(readOnly = false)
	public void addState(State state) {
		session=sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		if(state.getTime_Stamp()==null){
			Date date= new Date();
			state.setTime_Stamp(date);
		}
		session.saveOrUpdate(state);
		tx.commit();
		session.close();
	}

	
	public void addStateDetails(State s, long userId) {
		session=sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		State_Details sd = new State_Details();
		sd.setState_Id(s.getId());
		sd.setUser_Id(userId);
		Date date= new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		sd.setTime_Date(timestamp);
		session.saveOrUpdate(sd);
		tx.commit();
		session.close();
	}

	public List<State_Details> getStateDetails() {
		session=sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		Query q=session.getNamedQuery("StateDetails.getStateDetails");
		List<State_Details> sd = (List<State_Details>) q.list();
		tx.commit();
		session.close();
		return sd;
	}

	public List<State> getStatesFrom(String timestamp) {
		session=sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		Query q=session.getNamedQuery("State.getStatesFromTimestamp");
		q.setString("timestamp", timestamp);
		List<State> res = q.list();
		tx.commit();
		session.close();
		return res;
	}

}
