package com.example.exampleapp.utility;

import java.util.HashMap;

import com.example.exampleapp.activities.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	
	// Shared Preferences
    SharedPreferences pref;
 
    // Editor for Shared preferences
    Editor editor;
 
    // Context
    Context context;
    private final static String LOGGED_IN = "logged_in";
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String SURNAME = "surname";
    private final static String UUID = "uuid";
 
    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences("ExampleAppPref", 0);
        editor = pref.edit();
    }
    
    /*
     * Create a Login session
     */
    public void login(String id, String name, String surname, String uuid){
    	System.out.println("editor:"+editor);
    	editor.putBoolean(LOGGED_IN, true);
    	editor.putString(ID, id);
    	editor.putString(NAME, name);
    	editor.putString(SURNAME, surname);
    	editor.putString(UUID, uuid);
    	editor.commit();
    }
    
    /*
     * Get user data stored during Login initialization
     */
    public HashMap<String, String> getDetails(){
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put(ID, pref.getString(ID,""));
    	map.put(NAME, pref.getString(NAME, ""));
    	map.put(SURNAME, pref.getString(SURNAME, ""));
    	map.put(UUID, pref.getString(UUID, ""));
    	return map;
    }
    
    /*
     * Check wether the user is logged in or not
     */
    public boolean isLoggedIn(){
    	return pref.getBoolean(LOGGED_IN, false);
    }
    
    /*
     * Check login status. If no session exists redirect to Login Activity
     */
    public void checkLogin(){
    	// Check login status
        if(!isLoggedIn()){
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
    
    /*
     * Destroy login session and redirect user to login activity
     */
    public void logout(){
    	editor.clear();
    	editor.commit();
    	
    	// Redirect user to Loing Activity
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 
        // Staring Main Activity
        context.startActivity(i);
    }
    
    public String getUUID(){
    	return pref.getString(UUID, "");
    }
}
