package com.mydb.server.model;

import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.server.store.MyStore;
import static com.mydb.common.beans.DBConfigs.*;

public class DropColumnFamilyModel extends BaseModel{

	private String cfName;
	
	public DropColumnFamilyModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		this.cfName=jbody.getAsString(KEY);
	}

	@Override
	protected Object process() throws Exception, DBException {
		MyStore.dropColumnFamily(cfName);
		return Consts.STATUS.OK;
	}
}
