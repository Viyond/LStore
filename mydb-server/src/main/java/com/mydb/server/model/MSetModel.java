package com.mydb.server.model;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDBException;
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;
import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Words;
import com.mydb.server.store.MyStore;
import net.minidev.json.JSONObject;
import static com.mydb.common.beans.DBConfigs.*;

public class MSetModel extends BaseModel{
	private JSONObject kvs;
	public MSetModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		kvs=(JSONObject)jbody.get(VALUE);
	}

	@Override
	protected Object process() throws RocksDBException{
		WriteOptions options=new WriteOptions();
		WriteBatch batch=new WriteBatch();
		try{
			ColumnFamilyHandle cf=getColumnFamily();
			if(cf==null){
				throw new DBException(Words.EX_COLUMNFAMILY_NOTEXISTS);
			}
			kvs.forEach((k,v)->{
				batch.put(cf,k.getBytes(),v.toString().getBytes());
			});
			MyStore.db.write(options, batch);
		}finally{
			batch.close();
			options.close();
		}
		return Consts.STATUS.OK;
	}

}
