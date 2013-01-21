package com.ssmvc.server.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class Utility {

	
	public static String generateUUID(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
