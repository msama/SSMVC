package com.ssmvc.server.formModel;

import java.util.List;

public class JSonNewStateModel {

	private String uuid;
	private String timestamp;
	
	private List<JSonStateModel> rows;
	
	public List<JSonStateModel> getRows() {
		return rows;
	}
	public void setRows(List<JSonStateModel> rows) {
		this.rows = rows;
	}
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
	
	
}
