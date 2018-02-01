package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.Tools;
import net.minidev.json.JSONObject;
import static com.mydb.common.beans.DBConfigs.*;

public class ScanModel extends BaseModel{
	public ScanModel(Object key,int limit,boolean asc,String columnFamilyName) {
		super(Consts.CMD.SCAN);
		if(limit<0){
			limit=10;
		}else if(limit>10000){
			limit=10000;
		}
		JSONObject json=Tools.getJSON(KEY,key==null?null:key.toString(),VALUE,limit+"",OTHER,asc?"1":"0",CF,columnFamilyName);
		super.setBody(json.toJSONString());
	}
}
