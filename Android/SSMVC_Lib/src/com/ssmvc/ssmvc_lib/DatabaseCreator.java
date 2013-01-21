package com.ssmvc.ssmvc_lib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseCreator extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME="StateDB";
	
	private static final String CREATE_STATE_DETAILS = "" +
			"CREATE TABLE STATE_DETAILS(" +
			"TIME_DATE DATE NOT NULL," +
			"STATE_ID INT NOT NULL," +
			"PRIMARY KEY (TIME_DATE,STATE_ID)" +
			"CONSTRAINT FK_STATE_ID FOREIGN KEY (STATE_ID) REFERENCES STATE (ID))";
	
	private static final String CREATE_STATE= "" +
			"CREATE TABLE STATE(" +
			"ID INT NOT NULL AUTO_INCREMENT," +
			"DESCRIPTION VARCHAR(100) NOT NULL," +
			"PRIMARY KEY (ID)" +
			")";

	private static final int VERSION=1;
	
	public DatabaseCreator(Context context){
		super(context, DATABASE_NAME, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_STATE);
		db.execSQL(CREATE_STATE_DETAILS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
