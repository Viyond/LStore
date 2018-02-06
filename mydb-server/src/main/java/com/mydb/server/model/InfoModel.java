package com.mydb.server.model;

import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;
import com.mydb.server.store.MyStore;
import static com.mydb.common.beans.DBConfigs.*;

import org.rocksdb.RocksDBException;
public class InfoModel extends BaseModel {

	private String key;
	
	public InfoModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		key=jbody.getAsString(KEY);
	}

	@Override
	protected Object process() throws RocksDBException {
		return MyStore.db.getProperty(key);
	}

}
