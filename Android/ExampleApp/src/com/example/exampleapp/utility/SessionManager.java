package com.example.exampleapp.utility;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.exampleapp.activities.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author mircobordoni
 * <br><br>
 * 
 * The application needs to know information about the current Http Session. This class provides a way
 * to store these information through SharedPreferences global variables.
 */
public class SessionManager {
	
	// Shared Preferences
    private static SharedPreferences pref;
 
    // Editor for Shared preferences
    private static Editor editor;
 
    // Context
    private static Context context;
    
    private final static String LOGGED_IN = "logged_in";
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String SURNAME = "surname";
    private final static String UUID = "uuid";
    private final static String ADMIN = "admin";
    private final static String USER = "user";

 
    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences("ExampleAppPref", 0);
        editor = pref.edit();
    }
    
    /*
     * Create a Login session
     */
    public static void login(String id, String name, String surname, String uuid, JSONArray roles){
    	System.out.println("editor:"+editor);
    	editor.putBoolean(LOGGED_IN, true);
    	editor.putString(ID, id);
    	editor.putString(NAME, name);
    	editor.putString(SURNAME, surname);
    	editor.putString(UUID, uuid);
    	editor.putBoolean(ADMIN, false);
    	editor.putBoolean(USER, false);
    	for(int i=0; i<roles.length();i++){
    		try {
				if(roles.get(i).equals("Administrator"))editor.putBoolean(ADMIN, true);
				else if(roles.get(i).equals("User"))editor.putBoolean(USER, true);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
    	}
    	editor.commit();
    }
    
    /**
     * Get user data stored during Login initialization
     */
    public static HashMap<String, String> getDetails(){
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put(ID, pref.getString(ID,""));
    	map.put(NAME, pref.getString(NAME, ""));
    	map.put(SURNAME, pref.getString(SURNAME, ""));
    	map.put(UUID, pref.getString(UUID, ""));
    	return map;
    }
    
    /**
     * Check wether the user is logged in or not
     */
    public static boolean isLoggedIn(){
    	return pref.getBoolean(LOGGED_IN, false);
    }
    
    /**
     * Check login status. If no session exists redirect to Login Activity
     */
    public static void checkLogin(){
    	// Check login status
        if(!isLoggedIn()){
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
    
    /**
     * Destroy login session and redirect user to login activity
     */
    public static void logout(){
    	editor.clear();
    	editor.commit();
    	
    	// Redirect user to Loing Activity
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 
        // Staring Main Activity
        context.startActivity(i);
    }
    
    /**
     * Get the current session UUID. 
     */
    public static String getUUID(){
    	return pref.getString(UUID, "");
    }
    
    /**
     * Get the current user ID
     * @return
     */
    public static String getUserId(){
    	return pref.getString(ID, "");
    }
    
    /**
     * Check if the currend logged in user is an Administrator
     * @return true if the user is an Admin, false otherwise
     */
    public static boolean isAdmin(){
    	return pref.getBoolean(ADMIN, false);
    }
}
