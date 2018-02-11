package com.mydb.client.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mydb.client.model.DeleteModel;
import com.mydb.client.model.DeleteRangeModel;
import com.mydb.client.model.DropColumnFamilyModel;
import com.mydb.client.model.ExistsModel;
import com.mydb.client.model.GetModel;
import com.mydb.client.model.ListColumnFamilyModel;
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
import net.minidev.json.parser.ParseException;
import static com.mydb.common.beans.DBConfigs.*;

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

	private Logger log=LoggerFactory.getLogger(getClass());
	
	@Override
	public String get(Object key) {
		return get(key, DEFAULT_COLUMNFAMILY);
	}

	@Override
	public boolean set(Object key, Object value) {
		return set(key, value,DEFAULT_COLUMNFAMILY);
	}

	@Override
	public Map<String, Object> mget(Object... keys) {
		return mget(keys, DEFAULT_COLUMNFAMILY);
	}

	@Override
	public Map<String, Object> mget(List<Object> keys) {
		return mget(keys,DEFAULT_COLUMNFAMILY);
	}

	@Override
	public Map<String, Object> mget(Set<Object> keys) {
		return mget(keys, DEFAULT_COLUMNFAMILY);
	}

	@Override
	public boolean mset(Map<String, String> values) {
		return mset(values, DEFAULT_COLUMNFAMILY);
	}

	@Override
	public boolean mset(Object... kvs) {
		return mset(DEFAULT_COLUMNFAMILY, kvs);
	}

	@Override
	public void delete(Object key) {
		delete(key, DEFAULT_COLUMNFAMILY);
	}

	@Override
	public void deleleteRange(Object begin, Object end) {
		deleleteRange(begin, end, DEFAULT_COLUMNFAMILY);
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
		return scan(begin,limit,asc,DEFAULT_COLUMNFAMILY);
	}
	
	private Object checkAndReturn(JSONObject result) {
		Object value=result.get("v");
		switch(result.getAsNumber("s").intValue()){
		case Consts.STATUS.NOTOK:
			throw new DBException(Words.EX_OP_FAIL.code(),Words.EX_OP_FAIL.msg(value));
		case Consts.STATUS.EXCEPION:
			throw new DBException(Words.EX_OP_EXCEPTION.code(),Words.EX_OP_EXCEPTION.msg(value));
		case Consts.STATUS.NORESOURCE:
			throw new DBException(Words.EX_NO_RESOURCE);
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

	@Override
	public String get(Object key, String columnFamilyName) {
		GetModel get=new GetModel(key,columnFamilyName);
		Object result=checkAndReturn(get.run());
		return result==null?null:result.toString();
	}

	@Override
	public boolean set(Object key, Object value, String columnFamilyName) {
		SetModel set=new SetModel(key, value,columnFamilyName);
		checkAndReturn(set.run());
		return true;
	}

	@Override
	public Map<String, Object> mget(List<Object> keys, String columnFamilyName) {
		MGetModel mget=new MGetModel(columnFamilyName,keys);
		Object value=checkAndReturn(mget.run());
		try {
			return (JSONObject)Tools.parseJson(value.toString());
		} catch (ParseException e) {
			log.error("",e);
			throw new DBException(Words.EX_NOT_JSON);
		}
	}

	@Override
	public Map<String, Object> mget(Set<Object> keys, String columnFamilyName) {
		MGetModel mget=new MGetModel(DEFAULT_COLUMNFAMILY,keys);
		Object value=checkAndReturn(mget.run());
		return (JSONObject)value;
	}

	@Override
	public boolean mset(Map<String, String> values, String columnFamilyName) {
		MSetModel mset=new MSetModel(values,columnFamilyName);
		checkAndReturn(mset.run());
		return true;
	}

	@Override
	public void delete(Object key, String columnFamilyName) {
		DeleteModel del=new DeleteModel(key,columnFamilyName);
		checkAndReturn(del.run());
	}

	@Override
	public void deleleteRange(Object begin, Object end, String columnFamilyName) {
		DeleteRangeModel rdel=new DeleteRangeModel(begin, end,columnFamilyName);
		checkAndReturn(rdel.run());
	}

	@Override
	public List<Map<String, Object>> scan(Object begin, String columnFamilyName) {
		return scan(begin, 10, true, columnFamilyName);
	}

	@Override
	public List<Map<String, Object>> scan(Object begin, int limit, String columnFamilyName) {
		return scan(begin,limit,true,columnFamilyName);
	}

	@Override
	public List<Map<String, Object>> scan(Object begin, boolean asc, String columnFamilyName) {
		return scan(begin,10,asc,DEFAULT_COLUMNFAMILY);
	}

	@Override
	public List<Map<String, Object>> scan(Object begin, int limit, boolean asc, String columnFamilyName) {
		try{
			ScanModel scan=new ScanModel(begin, limit, asc,columnFamilyName);
			JSONArray values=(JSONArray)Tools.parseJson(checkAndReturn(scan.run()).toString());
			List<Map<String, Object>> tvalues=new ArrayList<>(values.size());
			for(int i=0;i<values.size();i++){
				tvalues.add((JSONObject)values.get(i));
			}
			return tvalues;
		} catch (ParseException e) {
			log.error("",e);
			throw new DBException(Words.EX_NOT_JSON);
		}
	}

	@Override
	public List<Map<String, Object>> scan(int limit, String columnFamilyName) {
		return scan(null,limit,true,columnFamilyName);
	}

	@Override
	public List<Map<String, Object>> scan(boolean asc, String columnFamilyName) {
		return scan(null,10,asc,columnFamilyName);
	}

	@Override
	public List<Map<String, Object>> scan(int limit, boolean asc, String columnFamilyName) {
		return scan(null,limit,asc,columnFamilyName);
	}

	@Override
	@SuppressWarnings("all")
	public List<String> listColumnFamiles() {
		ListColumnFamilyModel model=new ListColumnFamilyModel();
		Object obj=checkAndReturn(model.run());
		try {
			return (List)Tools.parseJson(obj.toString());
		} catch (ParseException e) {
			log.error("",e);
			throw new DBException(Words.EX_NOT_JSON);
		}
	}

	@Override
	public void dropColumnFamily(String columnFamilyName) {
		DropColumnFamilyModel model=new DropColumnFamilyModel(columnFamilyName);
		checkAndReturn(model.run());
	}

	@Override
	public boolean exists(String key) {
		return exists(key, DEFAULT_COLUMNFAMILY);
	}

	@Override
	public boolean exists(String key, String columnFamilyName) {
		ExistsModel model=new ExistsModel(key, columnFamilyName);
		Object o=checkAndReturn(model.run());
		return String.valueOf(o).equals("1");
	}
}
