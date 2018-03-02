package com.mydb.server.model;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Tools;
import com.mydb.common.beans.Words;
import com.mydb.server.store.MyStore;
import net.minidev.json.JSONArray;
import static com.mydb.common.beans.DBConfigs.*;

public class ScanModel extends BaseModel{

	private String key;
	private Integer limit=10;
	private boolean order=true;
	
	public ScanModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		this.key=jbody.getAsString(KEY);
		if(jbody.containsKey(VALUE)){
			limit=jbody.getAsNumber(VALUE).intValue();
		}
		if(jbody.containsKey(OTHER)){
			order=jbody.getAsNumber(OTHER).intValue()==1?true:false;
		}
	}

	@Override
	protected Object process() throws RocksDBException{
		RocksIterator it=null;
		JSONArray jar=Tools.getEmptyJSONArray();
		try{
			ColumnFamilyHandle cf=getColumnFamily();
			if(cf==null){
				throw new DBException(Words.EX_COLUMNFAMILY_NOTEXISTS);
			}
			it=MyStore.db.newIterator(cf);
			if(key!=null){
				it.seek(key.getBytes());
			}else if(order){
				it.seekToFirst();
			}else{
				it.seekToLast();
			}
			if(order){
				for(int i=0;i<limit&&it.isValid();i++){
					jar.add(Tools.getJSON(new String(it.key()),new String(it.value())));
					it.next();
				}
			}else{
				for(int i=0;i<limit&&it.isValid();i++){
					jar.add(Tools.getJSON(new String(it.key()),new String(it.value())));
					it.prev();
				}
			}
		}finally{
			if(it!=null){
				it.close();
			}
		}
		return jar;
	}

}
