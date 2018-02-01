package com.mydb.server.model;

import org.rocksdb.RocksDBException;

import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;
import com.mydb.server.store.MyStore;
import static com.mydb.common.beans.DBConfigs.*;

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
		byte[] data=MyStore.db.get(getColumnFamily(),key.getBytes());
		return data==null?null:new String(data);
	}
}