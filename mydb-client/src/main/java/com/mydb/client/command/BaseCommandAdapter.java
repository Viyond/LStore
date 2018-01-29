package com.mydb.client.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mydb.client.model.DeleteModel;
import com.mydb.client.model.DeleteRangeModel;
import com.mydb.client.model.GetModel;
import com.mydb.client.model.MGetModel;
import com.mydb.client.model.MSetModel;
import com.mydb.client.model.ScanModel;
import com.mydb.client.model.SetModel;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Tools;
import com.mydb.common.beans.Words;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

/**
 * 功能描述:implemention of command bridge
 * @createTime: 2018年1月24日 下午5:24:35
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月24日 下午5:24:35
 * @updateAuthor: l.sl
 * @changesSum:
 */
public class BaseCommandAdapter implements CommandBridge{

	@Override
	public String get(Object key) {
		GetModel get=new GetModel(key);
		Object result=checkAndReturn(get.run());
		return result==null?null:result.toString();
	}

	@Override
	public boolean set(Object key, Object value) {
		SetModel set=new SetModel(key, value);
		checkAndReturn(set.run());
		return true;
	}

	@Override
	public Map<String, Object> mget(Object... keys) {
		MGetModel mget=new MGetModel(keys);
		Object value=checkAndReturn(mget.run());
		try {
			return (JSONObject)Tools.parseJson(value.toString());
		} catch (ParseException e) {
			throw new DBException(Words.EX_NOT_JSON);
		}
	}

	@Override
	public Map<String, Object> mget(List<Object> keys) {
		MGetModel mget=new MGetModel(keys);
		Object value=checkAndReturn(mget.run());
		return (JSONObject)value;
	}

	@Override
	public Map<String, Object> mget(Set<Object> keys) {
		MGetModel mget=new MGetModel(keys);
		Object value=checkAndReturn(mget.run());
		return (JSONObject)value;
	}

	@Override
	public boolean mset(Map<String, String> values) {
		MSetModel mset=new MSetModel(values);
		checkAndReturn(mset.run());
		return true;
	}

	@Override
	public boolean mset(Object... kvs) {
		//if not in pairs!
		if(kvs.length%2!=0){
			throw new DBException(Words.EX_NOT_PAIR);
		}
		Map<String,String> values=new HashMap<>();
		for(int i=0;i<kvs.length;i+=2){
			if(kvs[i]==null||kvs[i+1]==null){
				throw new DBException(Words.EX_NULL_EXCEPTION);
			}
			String key=kvs[i].toString();
			String value=kvs[i+1].toString();
			values.put(key, value);
		}
		MSetModel mset=new MSetModel(values);
		checkAndReturn(mset.run());
		return true;
	}

	@Override
	public void delete(Object key) {
		DeleteModel del=new DeleteModel(key);
		checkAndReturn(del.run());
	}

	@Override
	public void deleleteRange(Object begin, Object end) {
		DeleteRangeModel rdel=new DeleteRangeModel(begin, end);
		checkAndReturn(rdel.run());
	}

	@Override
	public List<Map<String, Object>> scan(Object begin) {
		return scan(begin,10);
	}

	@Override
	public List<Map<String, Object>> scan(Object begin, int limit) {
		return scan(begin,limit,true);
	}

	@Override
	public List<Map<String, Object>> scan(Object begin, boolean asc) {
		return scan(begin,10,asc);
	}

	@Override
	public List<Map<String, Object>> scan(Object begin, int limit, boolean asc) {
		try{
			ScanModel scan=new ScanModel(begin, limit, asc);
			JSONArray values=(JSONArray)Tools.parseJson(checkAndReturn(scan.run()).toString());
			List<Map<String, Object>> tvalues=new ArrayList<>(values.size());
			for(int i=0;i<values.size();i++){
				tvalues.add((JSONObject)values.get(i));
			}
			return tvalues;
		} catch (ParseException e) {
			throw new DBException(Words.EX_NOT_JSON);
		}
	}
	
	private Object checkAndReturn(JSONObject result) {
		Object value=result.get("v");
		switch(result.getAsNumber("s").intValue()){
		case Consts.STATUS.NOTOK:
			throw new DBException(101,"操作失败!"+value);
		case Consts.STATUS.EXCEPION:
			throw new DBException(102,"操作异常:"+value);
		}
		return value; 
	}

	@Override
	public List<Map<String, Object>> scan(int limit) {
		return scan(null, limit);
	}

	@Override
	public List<Map<String, Object>> scan(boolean asc) {
		return scan(null, asc);
	}

	@Override
	public List<Map<String, Object>> scan(int limit, boolean asc) {
		return scan(null, limit, asc);
	}
}
