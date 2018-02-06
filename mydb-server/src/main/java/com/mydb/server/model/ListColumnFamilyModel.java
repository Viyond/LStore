package com.mydb.server.model;

import org.rocksdb.RocksDBException;

import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Tools;
import com.mydb.server.store.MyStore;
import net.minidev.json.JSONArray;

/**
 * 功能描述:column family 列表
 * @createTime: 2018年2月2日 下午1:14:28
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年2月2日 下午1:14:28
 * @updateAuthor: l.sl
 * @changesSum:
 */
public class ListColumnFamilyModel extends BaseModel{

	public ListColumnFamilyModel(CMDMsg cmdMsg) {
		super(cmdMsg);
	}

	@Override
	protected Object process() throws RocksDBException{
		JSONArray jar=Tools.getEmptyJSONArray();
		jar.addAll(MyStore.columnFamilies.keySet());
		return jar;
	}
}
