package com.ssmvc.ssmvc_lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/*
 * This class provides static methods to decode JSon objects
 */
public class DecodingUtils {

	public static JSONObject decodeJson(InputStream input){
		String jsonElement="";
	    JSONObject finalResult=null;
	    BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			jsonElement = reader.readLine();
			JSONTokener tokener = new JSONTokener(jsonElement);
			finalResult = new JSONObject(tokener);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return finalResult;
	}
}
