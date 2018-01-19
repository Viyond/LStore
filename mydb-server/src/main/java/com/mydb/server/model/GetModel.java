package com.mydb.server.model;

import org.rocksdb.RocksDBException;

import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Tools;
import com.mydb.server.store.MyStore;

import net.minidev.json.JSONObject;

public class GetModel extends BaseModel {
	
	private String key;
	private String value;
	
	public GetModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		this.key=jbody.getAsString(KEY);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	@Override
	public Object process() throws DBException, RocksDBException {
		byte[] data=MyStore.db.get(key.getBytes());
		value=data==null?"":new String(data);
		JSONObject json=Tools.getEmptyJSON();
		json.put(KEY, key);
		json.put(VALUE, value);
		return json.toJSONString();
	}
}