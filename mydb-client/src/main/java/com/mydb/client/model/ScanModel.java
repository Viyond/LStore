package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Tools;
import com.mydb.common.beans.Words;

import net.minidev.json.JSONObject;

public class ScanModel extends BaseModel{
	public ScanModel(Object key,int limit,boolean asc) {
		super(Consts.CMD.SCAN);
		assemble(key, limit, asc);
	}
	
	public ScanModel(Object key,int limit){
		super(Consts.CMD.SCAN);
		assemble(key, limit, true);
	}
	
	public ScanModel(Object key,boolean asc){
		super(Consts.CMD.SCAN);
		assemble(key, 10, asc);
	}
	
	public ScanModel(Object key){
		super(Consts.CMD.SCAN);
		assemble(key, 10, true);
	}
	
	private void assemble(Object key,int limit,boolean asc){
		if(key==null){
			throw new DBException(Words.EX_NULL_EXCEPTION);
		}
		if(limit<0){
			limit=10;
		}else if(limit>10000){
			limit=10000;
		}
		JSONObject json=Tools.getJSON(KEY,key.toString(),VALUE,limit+"",OTHER,asc?"1":"0");
		super.setBody(json.toJSONString());
	}
}
