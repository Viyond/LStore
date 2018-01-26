package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Tools;
import com.mydb.common.beans.Words;

import net.minidev.json.JSONObject;

public class DeleteModel extends BaseModel{

	public DeleteModel(Object key) {
		super(Consts.CMD.DEL);
		if(key==null){
			throw new DBException(Words.EX_NULL_EXCEPTION);
		}
		JSONObject json=Tools.getJSON();
		json.put(KEY, key);
		setBody(json.toJSONString());
	}
	
}
