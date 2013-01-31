package com.ssmvc.ssmvc_lib.services;

import java.util.ArrayList;

import com.ssmvc.ssmvc_lib.dbDAO;
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
		System.out.println("NetworkInfo:"+networkInfo);
		networkInfo=cm.getActiveNetworkInfo();
		if(networkInfo!=null){
			String last_timestamp = dbDAO.getStateLastTimestamp();
			String[] p = new String[]{"timestamp",last_timestamp};
			ArrayList<String[]> paramsList = new ArrayList<String[]>();
			paramsList.add(p);
			for(String[] s:params){
				paramsList.add(s);
			}
			StateTableUpdater stu = new StateTableUpdater(paramsList, resultProcessor);
			stu.start();
		}else{
			resultProcessor.onPersistanceResult();
		}
	}
	
	public void insertNewState(IPersistanceCallbacks resultProcessor, String id, String description,
			String timestamp, ArrayList<String[]> params){
		dbDAO.addState(id, description, timestamp, 1);
		networkInfo=cm.getActiveNetworkInfo();
		if(networkInfo!=null){
			System.out.println("Connected");
			ArrayList<String[]> paramsList = new ArrayList<String[]>();
			for(String[] s:params){
				paramsList.add(s);
			}
			StateTableWriter stw = new StateTableWriter(resultProcessor,paramsList);
			stw.start();
		}else{
			System.out.println("Not connected");
			resultProcessor.onPersistanceResult();
		}
	}
	
	
	

}


