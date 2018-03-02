package com.mydb.server.model;

import com.mydb.common.beans.CMDMsg;
import com.mydb.server.store.MyStore;
import static com.mydb.common.beans.DBConfigs.*;
import org.rocksdb.RocksDBException;

public class ExistsModel extends BaseModel{

	private String key;
	
	public ExistsModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		this.key=jbody.getAsString(KEY);
	}

	@Override
	protected Object process() throws RocksDBException{
		//keymayexists这个方法不能确切的告诉存不存在，道理同bloomfilter,不存在时不存在，存在时不一定存在
		return MyStore.db.keyMayExist(getColumnFamily(),key.getBytes(),new StringBuilder())?(MyStore.db.get(getColumnFamily(), key.getBytes())==null?0:1):0;
	}

}
