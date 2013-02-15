package com.ssmvc.ssmvc_lib;

import java.sql.Timestamp;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author mircobordoni
 * <br><br>
 * Database DAO (Data Access Object).
 */
public class dbDAO {

	private static DatabaseCreator dbCreator;
	private static SQLiteDatabase database;
	
	public dbDAO(Context context){
		dbCreator = new DatabaseCreator(context);
		//this.context = context;
		database=dbCreator.getWritableDatabase();
	}

	public static void initStates(){
		ContentValues content = new ContentValues();
		Date date= new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		content.put("DESCRIPTION", "happy");
		content.put("ID", 2);
		content.put("STATE", 1);
		content.put("TIME_STAMP", timestamp.toString());
		database.insert("STATE", null, content);
		
	}
	
	/**
	 * Delete all records from table STATE
	 */
	public static void removeAllStates(){
		database.delete("STATE", null, null);
	}
	
	/**
	 * Delete all records from table STATE_DETAILS
	 */
	public static void removeStatesDetails(){
		database.delete("STATE_DETAILS", null, null);
	}
	
	/**
	 * Add new record to the table STATE
	 * @param id Unique identifier of the record
	 * @param description Description of the new State record
	 * @param timestamp timestamp of the new record
	 * @param state Current state of the record
	 */
	public static void addState(String id, String description, String timestamp, int state ){
		ContentValues content = new ContentValues();
		content.put("ID", id);
		content.put("DESCRIPTION", description);
		content.put("TIME_STAMP", timestamp);
		content.put("STATE", state);
		long l =database.insert("STATE", null, content);
		System.out.println("Inserted, id="+l);
	}
	
	/**
	 * Add a new record to the table STATE_DETAILS
	 * 
	 * @param user_id Unique identifier of the user
	 * @param state_id Unique identifier of the state
	 * @param timestamp timestamp of the new record
	 * @param state Current state of the record
	 */
	public static void addStateDetails(String user_id, String state_id, String timestamp, int state){
		ContentValues content = new ContentValues();
		content.put("USER_ID", user_id);
		content.put("STATE_ID", state_id);
		content.put("TIME_STAMP", timestamp);
		content.put("TIME_DATE", timestamp);
		content.put("STATE", state);
		long l = database.insert("STATE_DETAILS", null, content);
		System.out.println("Inserted, id="+l);
	}
	
	/**
	 * Read all records from table STATE.
	 */
	public static Cursor getAllStates(){
		return database.rawQuery("select id as _id,description from state", null);
	}
	
	/**
	 * Read all records from STATE_DETAILS table given a user_id
	 * @param id User id used in the query
	 * @return All the records of STATE_DETAILS where user_id equals the one passed as parameter
	 */
	public static Cursor getAllStateDetails(String id){
		return database.rawQuery("select s.rowid _id, s.description, sd.time_date from state s " +
				"inner join state_details sd on s.id=sd.state_id where sd.user_id='"+id+"' order by sd.time_date desc", null);
	}
	
	
	/**
	 * Get the most recent timestamp from the STATE table
	 * @return
	 */
	public static String getStateLastTimestamp(){
		Cursor c = database.rawQuery("select max(time_stamp) as maxTime from state", null);
		if(c==null)return null;
		c.moveToFirst();
		if(!c.isAfterLast())return c.getString(c.getColumnIndex("maxTime"));
		return null;
	}
	
	/**
	 * Get all records from table STATE where the current state is equal to the parameter passed
	 * @param state current state of the record that has to be read
	 * @return records with current state equals to the parameter passed
	 */
	public static Cursor getAllStates(int state){
		return database.rawQuery("select * from state s where s.state='"+state+"'", null);
	}
	
	/**
	 * Get all records from table STATE_DETAILS where the current state is equal to the parameter passed
	 * @param state current state of the record that has to be read
	 * @return records with current state equals to the parameter passed
	 */
	public static Cursor getAllStateDetails(int state){
		return database.rawQuery("select * from state_details sd where sd.state='"+state+"'", null);
	}
	
	public static void updateState(String id, int state){
		ContentValues args = new ContentValues();
	    args.put("state", String.valueOf(state));
	    database.update("state", args, "id='" + id+"'", null);
	}

	public static void updateStateDetails(String user_id, String state_id,
			String time_date, int state) {
		ContentValues args = new ContentValues();
		args.put("state", String.valueOf(state));
		database.update("state_details", args, "user_id='"+user_id+"' and state_id='"+state_id+"' and time_date='"+time_date+"'", 
				null);
		
	}

	public static String getStateDetailsLastTimestamp(String user_id) {
		Cursor c = database.rawQuery("select max(sd.time_stamp) as maxTime from state_details sd where sd.user_id='"+user_id+"'", null);
		if(c==null)return null;
		c.moveToFirst();
		if(!c.isAfterLast())return c.getString(c.getColumnIndex("maxTime"));
		return null;
	}
}
