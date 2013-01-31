package com.ssmvc.ssmvc_lib;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

/**
 * 
 * @author mircobordoni
 * <br><br>
 * 
 * This class provides static methods to send an HTTP request to the server. The response is decoded
 * and returned as a JSONObject.
 */
public class HTTPRequestManager {
	private static Context context;
	
	public HTTPRequestManager(Context context){
		this.context=context;
	}
	
	public static JSONObject sendRequest(ArrayList<String[]> parameters, String relativeURI){
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
		JSONObject finalResult = null;

		HttpPost postMethod = new HttpPost(
				context.getString(R.string.serverURI)
						+ relativeURI);

		JSONObject json = new JSONObject();
		try {
			for(String[] p: parameters){
				json.put(p[0], p[1]);
			}
			postMethod.setEntity(new ByteArrayEntity(json.toString().getBytes(
					"UTF8")));
			postMethod.setHeader("Content-Type", "application/json");
		} catch (JSONException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		try {
			response = client.execute(postMethod);
			finalResult=DecodingUtils.decodeJson(response.getEntity().getContent());
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return finalResult;
	}

	public static JSONObject sendRequest(JSONObject jsonObj, String relativeURI){
		DefaultHttpClient client = new DefaultHttpClient();
		JSONObject finalResult = null;
		HttpResponse response = null;
		HttpPost postMethod = new HttpPost(
				context.getString(R.string.serverURI)
						+ relativeURI);
		try {
			postMethod.setEntity(new ByteArrayEntity(jsonObj.toString().getBytes(
					"UTF8")));
			postMethod.setHeader("Content-Type", "application/json");
			response = client.execute(postMethod);
			finalResult=DecodingUtils.decodeJson(response.getEntity().getContent());
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return finalResult;
	}
}
