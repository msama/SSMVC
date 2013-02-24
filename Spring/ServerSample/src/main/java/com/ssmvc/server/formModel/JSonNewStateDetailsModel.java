package com.ssmvc.server.formModel;

import java.util.List;

public class JSonNewStateDetailsModel {
	
	private String uuid;
	private String timestamp;
	private List<JSonStateDetailsModel> rows;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public List<JSonStateDetailsModel> getRows() {
		return rows;
	}
	public void setRows(List<JSonStateDetailsModel> rows) {
		this.rows = rows;
	}
	
	

}
