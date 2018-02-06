package com.mydb.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDBException;
import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Tools;
import com.mydb.common.beans.Words;
import com.mydb.server.store.MyStore;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import static com.mydb.common.beans.DBConfigs.*;

public class MGetModel extends BaseModel {

	private List<byte[]> keys;
	private List<ColumnFamilyHandle> listCfh;
	private JSONArray strKeys;
	
	public MGetModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		strKeys=(JSONArray)jbody.get(KEYS);
		keys=new ArrayList<>(strKeys.size());
		strKeys.forEach(e ->{
			if(e!=null){
				keys.add((e+"").getBytes());
			}
		});
	}

	@Override
	protected Object process() throws RocksDBException{
		if(keys.size()==0){
			return Tools.getJSON();
		}
		ColumnFamilyHandle cf=getColumnFamily();
		if(cf==null) throw new DBException(Words.EX_COLUMNFAMILY_NOTEXISTS);
		listCfh=new ArrayList<>(strKeys.size());
		ColumnFamilyHandle cfh=getColumnFamily();
		keys.forEach(e -> {
			listCfh.add(cfh);
		});
		Map<byte[], byte[]> res=MyStore.db.multiGet(listCfh,keys);
		JSONObject vals=Tools.getJSON();
		res.forEach((k,v)->{
			vals.put(new String(k),new String(v));
		});
		return vals;
	}

}
