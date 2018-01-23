package com.mydb.server.model;

import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;
import com.mydb.server.store.MyStore;

public class DeleteRangeModel extends BaseModel{

	//[beginKey,endKey)
	private String beginKey,endKey;
	
	public DeleteRangeModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		beginKey=jbody.getAsString(KEY);
		endKey=jbody.getAsString(VALUE);
	}

	@Override
	protected Object process() throws Exception, DBException {
		MyStore.db.deleteRange(beginKey.getBytes(), endKey.getBytes());
		return jbody;
	}

}
