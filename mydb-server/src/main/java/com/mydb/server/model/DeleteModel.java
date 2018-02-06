package com.mydb.server.model;

import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Words;
import com.mydb.server.store.MyStore;
import static com.mydb.common.beans.DBConfigs.*;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDBException;

public class DeleteModel extends BaseModel{

	public DeleteModel(CMDMsg cmdMsg) {
		super(cmdMsg);
	}

	@Override
	protected Object process() throws RocksDBException{
		ColumnFamilyHandle cf=getColumnFamily();
		if(cf==null){
			throw new DBException(Words.EX_COLUMNFAMILY_NOTEXISTS);
		}
		MyStore.db.delete(getColumnFamily(),jbody.getAsString(KEY).getBytes());
		return Consts.STATUS.OK;
	}
}