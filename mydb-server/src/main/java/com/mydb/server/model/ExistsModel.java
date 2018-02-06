package com.mydb.server.model;

import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;
import com.mydb.server.store.MyStore;
import static com.mydb.common.beans.DBConfigs.*;

import org.rocksdb.RocksDBException;

public class ExistsModel extends BaseModel{

	public ExistsModel(CMDMsg cmdMsg) {
		super(cmdMsg);
	}

	@Override
	protected Object process() throws RocksDBException{
		return MyStore.db.keyMayExist(getColumnFamily(),jbody.getAsString(KEY).getBytes(),new StringBuilder())?1:0;
	}

}
