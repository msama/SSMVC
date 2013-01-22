package com.ssmvc.ssmvc_lib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class dbDAO {

	private DatabaseCreator dbCreator;
	private Context context;
	private SQLiteDatabase database;
	
	public dbDAO(Context context){
		dbCreator = new DatabaseCreator(context);
		this.context = context;
	}
	
	public void open(){
		database=dbCreator.getWritableDatabase();
	}
	
	public void close(){
		dbCreator.close();
	}
	
	public void initStates(){
		ContentValues content = new ContentValues();
		content.put("DESCRIPTION", "sad");
		content.put("ID", 1);
		long l =database.insert("STATE", null, content);
		content.put("DESCRIPTION", "happy");
		content.put("ID", 2);
		l =database.insert("STATE", null, content);
		content.put("DESCRIPTION", "angry");
		content.put("ID", 3);
		l =database.insert("STATE", null, content);
		System.out.println("Inserted:"+l+" rows");
	}
	
	public void removeStates(){
		int i=database.delete("STATE", null, null);
		System.out.println("Deleted "+i+" rows");
	}
	
	public void addState(String state){
		ContentValues content = new ContentValues();
		content.put("DESCRIPTION", state);
		long l =database.insert("STATE", null, content);
		System.out.println("Inserted, id="+l);
	}
	
	public Cursor getAllStates(){
//		return database.query(true, "STATE", null, null, null, null, null, null, null, null);
		return database.rawQuery("select id as _id,description from state", null);
	}
	
	
}
