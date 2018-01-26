package com.mydb.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.rocksdb.RocksDBException;
import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Tools;
import com.mydb.server.store.MyStore;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class MGetModel extends BaseModel {

	private List<byte[]> keys;
	private JSONArray strKeys;
	
	public MGetModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		strKeys=(JSONArray)jbody.get(KEYS);
		keys=new ArrayList<>();
		strKeys.forEach(e ->{
			if(e!=null){
				keys.add((e+"").getBytes());
			}
		});
	}

	@Override
	protected Object process() throws DBException, RocksDBException {
		if(keys.size()==0){
			return Tools.getJSON();
		}
		Map<byte[], byte[]> res=MyStore.db.multiGet(keys);
		JSONObject vals=Tools.getJSON();
		res.forEach((k,v)->{
			vals.put(new String(k),new String(v));
		});
		return vals;
	}

}
