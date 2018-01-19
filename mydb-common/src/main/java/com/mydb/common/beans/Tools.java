package com.mydb.common.beans;

import java.security.MessageDigest;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class Tools {
	
	public static JSONObject getEmptyJSON(){
		return new JSONObject();
	}
	
	public static JSONArray getEmptyJSONArray(){
		return new JSONArray();
	}
	
	public static JSONObject parseJson(String json) throws ParseException{
		JSONParser jsonParser=new JSONParser(JSONParser.MODE_PERMISSIVE);
		return jsonParser.parse(json,JSONObject.class);
	}
	
	public static String Md5(String str) {
		if (str == null) {
			return "";
		} else {
			String value = null;
			try {
				MessageDigest md5 = MessageDigest.getInstance("MD5");

				byte[] results = md5.digest(str.getBytes());
				StringBuffer resultSb = new StringBuffer();
				for (int i = 0; i < results.length; i++) {
					resultSb.append(byteToHexString(results[i]));
				}
				value = resultSb.toString();
				return value;// .substring(8, 24);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return value;
		}
	}
	
	private static String byteToHexString(byte b) {
		String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}
