package com.ssmvc.ssmvc_lib.services;

import java.util.ArrayList;

import com.ssmvc.ssmvc_lib.dbDAO;
import com.ssmvc.ssmvc_lib.workers.StateDetailsTableUpdater;
import com.ssmvc.ssmvc_lib.workers.StateDetailsTableWriter;
import com.ssmvc.ssmvc_lib.workers.StateTableUpdater;
import com.ssmvc.ssmvc_lib.workers.StateTableWriter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

/**
 * 
 * @author mircobordoni
 * <br><br>
 * This service provides methods to synchronize local database with the remote one.
 * 
 */
public class PersistanceService extends Service{
	private IBinder binder = new PersistanceLocalBinder(this);
	private ConnectivityManager cm;
	private NetworkInfo networkInfo;

	@Override
	public IBinder onBind(Intent intent) {
		cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		return binder;
	}
	
	/**
	 * This method is used to read new STATE table records from the remote database and insert them
	 * in the local STATE table.
	 * @param resultProcessor Object that will handle the results of this method
	 * @param params List of params to send to the server
	 */
	public void getAllStates(IPersistanceCallbacks resultProcessor, ArrayList<String[]> params ){
		networkInfo=cm.getActiveNetworkInfo();
		if(networkInfo!=null){
			String last_timestamp = dbDAO.getStateLastTimestamp();
			String[] p = new String[]{"timestamp",last_timestamp};
			params.add(p);
			StateTableUpdater stu = new StateTableUpdater(params, resultProcessor);
			stu.start();
		}else{
			resultProcessor.onPersistanceResult();
		}
	}
	
	/**
	 * This method is used to insert a new record into the local STATE table. If a network connection is available
	 * it pushes the inserted records to the remote database.
	 * 
	 * @param resultProcessor Object that will handle the results of this method
	 * @param id Unique identifier of the new record
	 * @param description Description field of the new record
	 * @param timestamp timestamp of the new record
	 * @param params list of application specific parameters to be sent to the server
	 */
	public void insertNewState(IPersistanceCallbacks resultProcessor, String id, String description,
			String timestamp, ArrayList<String[]> params){
		dbDAO.addState(id, description, timestamp, 1);
		networkInfo=cm.getActiveNetworkInfo();
		if(networkInfo!=null){
			StateTableWriter stw = new StateTableWriter(resultProcessor,params);
			stw.start();
		}else{
			resultProcessor.onPersistanceResult();
		}
	}
	
	
	/**
	 * This method is used to insert a new record into the local STATE_DETAILS table. If a network connection is available
	 * it pushes the inserted records to the remote database.
	 * @param resultProcessor Object that will handle the results of this method
	 * @param user_id Unique identifier of the user
	 * @param state_id Unique identifier of the state
	 * @param timestamp timestamp of the new record
	 * @param params list of application specific parameters to be sent to the server
	 */
	public void insertNewStateDetails(IPersistanceCallbacks resultProcessor, String user_id, String state_id,
			String timestamp, ArrayList<String[]> params){
		dbDAO.addStateDetails(user_id, state_id, timestamp, 1);
		networkInfo=cm.getActiveNetworkInfo();
		if(networkInfo!=null){
			StateDetailsTableWriter sdtw = new StateDetailsTableWriter(resultProcessor,params);
			sdtw.start();
		}else{
			resultProcessor.onPersistanceResult();
		}
	}
	
	
	public void getAllStateDetails(IPersistanceCallbacks resultProcessor, ArrayList<String[]> params, String user_id){
		networkInfo=cm.getActiveNetworkInfo();
		if(networkInfo!=null){
			String last_timestamp = dbDAO.getStateDetailsLastTimestamp(user_id);
			String[] p = new String[]{"timestamp",last_timestamp};
			params.add(p);
			p = new String[]{"user_id",user_id};
			params.add(p);
			StateDetailsTableUpdater sdtu = new StateDetailsTableUpdater(params, resultProcessor);
			sdtu.start();
		}else{
			resultProcessor.onPersistanceResult();
		}
	}
	

}


