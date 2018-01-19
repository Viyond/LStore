package com.mydb.common.beans;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class Result {
	public interface TYPE{
		public int STRING=0,JSON=1,JSONARRAY=2;
	}
	
	private int type;
	private Object value;
	
	public Result(int type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return type==Result.TYPE.STRING?String.valueOf(value):type==Result.TYPE.JSON?((JSONObject)value).toJSONString():((JSONArray)value).toJSONString();
	};
	
}