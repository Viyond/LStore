package com.mydb.server.model;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDBException;
import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Words;
import com.mydb.server.store.MyStore;
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
	public Object process() throws RocksDBException{
		ColumnFamilyHandle cf=getColumnFamily();
		if(cf==null){
			throw new DBException(Words.EX_COLUMNFAMILY_NOTEXISTS);
		}
		MyStore.db.put(getColumnFamily(),key.getBytes(), value.toString().getBytes());
		return Consts.STATUS.OK;
	}
}