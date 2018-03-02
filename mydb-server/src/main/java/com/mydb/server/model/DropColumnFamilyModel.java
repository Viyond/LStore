package com.mydb.server.model;

import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.Consts;
import com.mydb.server.store.MyStore;
import static com.mydb.common.beans.DBConfigs.*;
import org.rocksdb.RocksDBException;

public class DropColumnFamilyModel extends BaseModel{

	private String cfName;
	
	public DropColumnFamilyModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		this.cfName=jbody.getAsString(KEY);
	}

	@Override
	protected Object process() throws RocksDBException{
		MyStore.dropColumnFamily(cfName);
		return Consts.STATUS.OK;
	}
}
