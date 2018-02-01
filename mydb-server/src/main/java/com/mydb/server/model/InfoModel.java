package com.mydb.server.model;

import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;
import com.mydb.server.store.MyStore;
import static com.mydb.common.beans.DBConfigs.*;
public class InfoModel extends BaseModel {

	private String key;
	
	public InfoModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		key=jbody.getAsString(KEY);
	}

	@Override
	protected Object process() throws Exception, DBException {
		return MyStore.db.getProperty(key);
	}

}
