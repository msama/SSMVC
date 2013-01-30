package com.ssmvc.ssmvc_lib.services;

import android.app.Service;
import android.os.Binder;

/**
 * 
 * @author mircobordoni
 * <br><br>
 * Binder used to support PersistanceService usage.
 */
public class PersistanceLocalBinder extends Binder {
	private Service service;
    
	public PersistanceLocalBinder(Service service){
		this.service=service;
	}
	
	public Service getService() {           
        return service;
    }
}
