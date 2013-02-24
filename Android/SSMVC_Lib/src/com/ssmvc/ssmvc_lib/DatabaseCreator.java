package com.ssmvc.ssmvc_lib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author mircobordoni
 * <br><br>
 * This class manages the creation and update of the client local database.
 *
 */
public class DatabaseCreator extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME="StateDB";
	
	private static final String CREATE_STATE_DETAILS = "" +
			"CREATE TABLE STATE_DETAILS(" +
			"USER_ID INT NOT NULL," +
			"TIME_DATE TIMESTAMP NOT NULL," +
			"STATE_ID VARCHAR(40) NOT NULL," +
			"TIME_STAMP TIMESTAMP NOT NULL,"+
			"STATE INT NOT NULL,"+
			"PRIMARY KEY (TIME_DATE,STATE_ID,USER_ID)," +
			"CONSTRAINT FK_STATE_ID FOREIGN KEY (STATE_ID) REFERENCES STATE (ID))";
	
	private static final String CREATE_STATE= "" +
			"CREATE TABLE STATE(" +
			"ID VARCHAR(40) PRIMARY KEY," +
			"DESCRIPTION VARCHAR(100) NOT NULL," +
			"TIME_STAMP TIMESTAMP NOT NULL,"+
			"STATE INT NOT NULL"+
			")";

	private static final int VERSION=10;
	
	public DatabaseCreator(Context context){
		super(context, DATABASE_NAME, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("Creazione");
		db.execSQL(CREATE_STATE);
		db.execSQL(CREATE_STATE_DETAILS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("Update");
		db.execSQL("DROP TABLE STATE");
		db.execSQL("DROP TABLE STATE_DETAILS");
		db.execSQL(CREATE_STATE);
		db.execSQL(CREATE_STATE_DETAILS);
		
	}

}
