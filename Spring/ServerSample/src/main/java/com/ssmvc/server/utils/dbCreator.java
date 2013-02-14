package com.ssmvc.server.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbCreator {
	
	public static void main(String[] s){
		
		Connection conn; 
		Statement stat;
		File file1=new File("data.db");
		boolean dbExists=file1.exists();
		if(dbExists)file1.delete();
		
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:data.db"); 
	    	stat = conn.createStatement();
	    	stat.executeUpdate("CREATE TABLE user (" +
	    			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
	    			"first_name VARCHAR(60) NOT NULL, " +
	    			"last_name VARCHAR(60) NOT NULL, " +
	    			"birth_date DATE, " +
	    			"username VARCHAR(50) NOT NULL, " +
	    			"password VARCHAR(50) NOT NULL, " +
	    			"CONSTRAINT C1 UNIQUE (username));");
	    	
	    	stat.executeUpdate("CREATE TABLE state( " +
	    			"id VARCHAR(40) PRIMARY KEY," +
	    			"time_stamp TIMESTAMP NOT NULL," +
	    			"description VARCHAR(100) NOT NULL);");
	    	
	    	stat.executeUpdate("CREATE TABLE state_details(" +
	    			"user_id INT NOT NULL," +
	    			"state_id VARCHAR(40) NOT NULL," +
	    			"time_date TIMESTAMP NOT NULL," +
	    			"time_stamp TIMESTAMP NOT NULL," +
	    			"CONSTRAINT PK PRIMARY KEY (user_id,state_id,time_date)," +
	    			"CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES USER (id)," +
	    			"CONSTRAINT FK_STATE_ID FOREIGN KEY (state_id) REFERENCES STATE (id));");
	    	
	    	stat.executeUpdate("CREATE TABLE role(" +
	    			"id INTEGER PRIMARY KEY AUTOINCREMENT," +
	    			"description VARCHAR(100) NOT NULL);");
	    	
	    	stat.executeUpdate("CREATE TABLE user_role(" +
	    			"user_id INT NOT NULL," +
	    			"role_id INT NOT NULL," +
	    			"from_date DATE NOT NULL," +
	    			"to_date DATE," +
	    			"CONSTRAINT PK PRIMARY KEY (user_id, role_id, from_date)," +
	    			"CONSTRAINT FK_USER FOREIGN KEY (user_id) REFERENCES USER (id)," +
	    			"CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id) REFERENCES ROLE (id));");
	    	
	    	stat.executeUpdate("insert into user (first_name, last_name, birth_date, username, password)" +
	    			"values('Mirco','Bordoni','1987-03-07','mirco','mirco')");
	    	stat.executeUpdate("insert into user (first_name, last_name, birth_date, username, password)" +
	    			"values('Gino','Pilotino','1984-07-10','gino','gino')");
	    	stat.executeUpdate("insert into user (first_name, last_name, birth_date, username, password)" +
	    			"values('Mario','Rossi','1980-04-11','mario','mario')");
	    	
	    	stat.executeUpdate("insert into state (id,description,time_stamp)" +
	    			"values('id1','sad','2013-01-20 12:04:44')");
	    	stat.executeUpdate("insert into state (id,description,time_stamp)" +
	    			"values('id2','happy','2013-01-21 14:44:44')");
	    	stat.executeUpdate("insert into state (id,description,time_stamp)" +
	    			"values('id3','angry','2013-01-20 9:04:44')");
	    	
	    	
	    	stat.executeUpdate("insert into state_details (user_id, state_id, time_date, time_stamp)" +
	    			"values(1,'id1','2013-01-11 12:04:44','2013-01-11 12:04:44')");
	    	stat.executeUpdate("insert into state_details (user_id, state_id, time_date, time_stamp)" +
	    			"values(1,'id2','2013-01-11 15:04:44','2013-01-11 12:04:44')");
	    	stat.executeUpdate("insert into state_details (user_id, state_id, time_date, time_stamp)" +
	    			"values(2,'id1','2013-01-04 10:54:11','2013-01-11 12:04:44')");
	    	
	    	stat.executeUpdate("insert into role (description) values ('Administrator')");
	    	stat.executeUpdate("insert into role (description) values ('User')");
	    	
	    	stat.executeUpdate("insert into user_role (user_id,role_id,from_date,to_date) values (3,1,'2013-01-01','2014-01-01')");
	    	stat.executeUpdate("insert into user_role (user_id,role_id,from_date,to_date) values (1,2,'2013-01-01','2014-01-01')");
	    	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
