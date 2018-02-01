package com.mydb.server.model;

import org.rocksdb.RocksDBException;
import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Tools;
import com.mydb.server.store.MyStore;
import net.minidev.json.JSONObject;
import static com.mydb.common.beans.DBConfigs.*;

public class SetModel extends BaseModel {
	
	private String key;
	private Object value;
	
	public SetModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		this.key=jbody.getAsString(KEY);
		this.value=jbody.get(VALUE);
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public Object process() throws DBException, RocksDBException {
		MyStore.db.put(getColumnFamily(),key.getBytes(), value.toString().getBytes());
		return Consts.STATUS.OK;
	}
}