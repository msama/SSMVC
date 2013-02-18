package com.ssmvc.ssmvc_lib.services;

import java.util.ArrayList;

import com.ssmvc.ssmvc_lib.dbDAO;
import com.ssmvc.ssmvc_lib.workers.StateDetailsUpdater;
import com.ssmvc.ssmvc_lib.workers.StateUpdater;
import com.ssmvc.ssmvc_lib.workers.WorkDispatcher;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

/**
 * 
 * @author mircobordoni <br>
 * <br>
 *         This service provides methods to synchronize local database with the
 *         remote one.
 * 
 */
public class PersistanceService extends Service {
	private IBinder binder = new PersistanceLocalBinder(this);
	private ConnectivityManager cm;
	private NetworkInfo networkInfo;
	private WorkDispatcher workDispatcher;

	@Override
	public IBinder onBind(Intent intent) {
		cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		workDispatcher = new WorkDispatcher();
		workDispatcher.start();
		return binder;
	}

	/**
	 * This method is used to synchronize local STATE table with the remote one.
	 * 
	 * @param resultProcessor
	 *            Object that will handle the results of this method
	 * @param params
	 *            List of params to send to the server
	 */
	public void getAllStates(IPersistanceCallbacks resultProcessor, ArrayList<String[]> params) {
		updateState(resultProcessor, params);
	}

	/**
	 * This method is used to insert a new record into the local STATE table. 
	 * Then it synchronizes local STATE table with the remote one.
	 * 
	 * @param resultProcessor
	 *            Object that will handle the results of this method
	 * @param id
	 *            Unique identifier of the new record
	 * @param description
	 *            Description field of the new record
	 * @param timestamp
	 *            timestamp of the new record
	 * @param params
	 *            list of application specific parameters to be sent to the
	 *            server
	 */
	public void insertNewState(IPersistanceCallbacks resultProcessor, String id,
			String description, String timestamp, ArrayList<String[]> params) {
		dbDAO.addState(id, description, timestamp, 1);
		updateState(resultProcessor, params);
	}

	/**
	 * This method is used to insert a new record into the local STATE_DETAILS table. 
	 * Then it synchronizes local STATE_DETAILS table with the remote one.
	 * 
	 * @param resultProcessor
	 *            Object that will handle the results of this method
	 * @param user_id
	 *            Unique identifier of the user
	 * @param state_id
	 *            Unique identifier of the state
	 * @param timestamp
	 *            timestamp of the new record
	 * @param params
	 *            list of application specific parameters to be sent to the
	 *            server
	 */
	public void insertNewStateDetails(IPersistanceCallbacks resultProcessor, String user_id,
			String state_id, String timestamp, ArrayList<String[]> params) {
		dbDAO.addStateDetails(user_id, state_id, timestamp, 1);
		updateStateDetails(resultProcessor, params, user_id);
	}

	/**
	 * This method is used to synchronize local STATE_DETAILS table with the remote one.
	 * 
	 * @param resultProcessor
	 * @param params
	 * @param user_id
	 */
	public void getAllStateDetails(IPersistanceCallbacks resultProcessor,
			ArrayList<String[]> params, String user_id) {
		updateStateDetails(resultProcessor, params, user_id);
	}
	
	/**
	 * If a network connection is available this methods add a new StateUpdater work to the 
	 * WorkDispatcher queue. Otherwise it simply calls the method onPersistanceResult of the
	 * resultProcessor passed.
	 * 
	 * @param resultProcessor Object that will handle the result of this operation
	 * @param params parameters to be sent to the server
	 */
	private void updateState(IPersistanceCallbacks resultProcessor, ArrayList<String[]> params){
		networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null) {
			String last_timestamp = dbDAO.getStateLastTimestamp();
			String[] p = new String[] { "timestamp", last_timestamp == null ? null : last_timestamp };
			params.add(p);
			StateUpdater su = new StateUpdater(resultProcessor, params);
			workDispatcher.addWork(su);
		} else {
			resultProcessor.onPersistanceResult();
		}
	}
	
	/**
	 * If a network connection is available this methods add a new StateDetailsUpdater work to the 
	 * WorkDispatcher queue. Otherwise it simply calls the method onPersistanceResult of the
	 * resultProcessor passed.
	 * 
	 * @param resultProcessor Object that will handle the result of this operation
	 * @param params parameters to be sent to the server
	 * @param user_id Identifier of the user
	 */
	private void updateStateDetails(IPersistanceCallbacks resultProcessor,
			ArrayList<String[]> params, String user_id){
		networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null) {
			String last_timestamp = dbDAO.getStateDetailsLastTimestamp(user_id);
			String[] p = new String[] { "timestamp", last_timestamp };
			params.add(p);
			StateDetailsUpdater sdu = new StateDetailsUpdater(resultProcessor, params);
			workDispatcher.addWork(sdu);
		} else {
			resultProcessor.onPersistanceResult();
		}
	}

}
