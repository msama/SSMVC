package com.ssmvc.ssmvc_lib.services;

import android.content.Context;

/**
 * 
 * @author mircobordoni
 * <br><br>
 * This interface has to be implemented from each Activity that wants to use a PersistanceService.
 *
 */
public interface IPersistanceCallbacks {
	
	/**
	 * Callback executed when the Service has completed its work
	 */
	public void onPersistanceResult(Object...objects);
	
	/**
	 * Get the actual context of the Service.
	 */
	public Context getContext();

}
