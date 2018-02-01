package com.mydb.server.model;

import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.Consts;
import com.mydb.server.store.MyStore;
import static com.mydb.common.beans.DBConfigs.*;

public class DeleteModel extends BaseModel{

	public DeleteModel(CMDMsg cmdMsg) {
		super(cmdMsg);
	}

	@Override
	protected Object process() throws Exception {
		MyStore.db.delete(getColumnFamily(),jbody.getAsString(KEY).getBytes());
		return Consts.STATUS.OK;
	}
}