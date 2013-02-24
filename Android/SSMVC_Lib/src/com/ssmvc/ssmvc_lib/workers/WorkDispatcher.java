package com.ssmvc.ssmvc_lib.workers;

import java.util.ArrayList;
import java.util.List;

public class WorkDispatcher extends Thread{
	
	private List<IWorker> queue;
	
	public WorkDispatcher(){
		queue = new ArrayList<IWorker>();
	}
	
	public void addWork(IWorker w){
		synchronized (this) {
			queue.add(w);
			notify();
		}
	}
	
	public void run(){
		IWorker worker;
		while(true){
			while(queue.size()==0)
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						continue;
					}
				}
				
			synchronized (this) {
				worker = queue.remove(0);
			}
			worker.doJob();
		}
	}

}
