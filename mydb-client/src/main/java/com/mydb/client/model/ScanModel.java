package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.Tools;
import net.minidev.json.JSONObject;

public class ScanModel extends BaseModel{
	public ScanModel(String key,int limit,boolean asc) {
		super(Consts.CMD.SCAN);
		assemble(key, limit, asc);
	}
	
	public ScanModel(String key,int limit){
		super(Consts.CMD.SCAN);
		assemble(key, limit, true);
	}
	
	public ScanModel(String key,boolean asc){
		super(Consts.CMD.SCAN);
		assemble(key, 10, asc);
	}
	
	public ScanModel(String key){
		super(Consts.CMD.SCAN);
		assemble(key, 10, true);
	}
	
	private void assemble(String key,int limit,boolean asc){
		if(limit<0){
			limit=10;
		}else if(limit>10000){
			limit=10000;
		}
		JSONObject json=Tools.getJSON(KEY,key,VALUE,limit+"",OTHER,asc?"1":"0");
		super.setBody(json.toJSONString());
	}
}
