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
	
	public static void open(){
		database=dbCreator.getWritableDatabase();
	}
	
	public static void close(){
		dbCreator.close();
	}
	
	public static void initStates(){
		ContentValues content = new ContentValues();
		Date date= new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		content.put("DESCRIPTION", "happy");
		content.put("ID", 2);
		content.put("STATE", 1);
		content.put("TIME_STAMP", timestamp.toString());
		long l =database.insert("STATE", null, content);
		
	}
	
	/**
	 * Delete all records from table STATE
	 */
	public static void removeAllStates(){
		int i=database.delete("STATE", null, null);
	}
	
	/**
	 * Delete all records from table STATE_DETAILS
	 */
	public static void removeStatesDetails(){
		int i = database.delete("STATE_DETAILS", null, null);
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
				"inner join state_details sd on s.id=sd.state_id where sd.user_id='"+id+"'", null);
	}
	
	public static void initStateDetails(){
		database.delete("STATE_DETAILS", null, null);
		Date date= new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		ContentValues content = new ContentValues();
		content.put("USER_ID", 1);
		content.put("STATE_ID", 1);
		content.put("TIME_DATE", timestamp.toString() );
		long l = database.insert("STATE_DETAILS", null, content);
		
		timestamp = new Timestamp(date.getTime());
		content = new ContentValues();
		content.put("USER_ID", 2);
		content.put("STATE_ID", 2);
		content.put("TIME_DATE", timestamp.toString() );
		l = database.insert("STATE_DETAILS", null, content);
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
}
