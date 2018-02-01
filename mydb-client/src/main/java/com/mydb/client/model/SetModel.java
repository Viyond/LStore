package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Tools;
import com.mydb.common.beans.Words;
import net.minidev.json.JSONObject;
import static com.mydb.common.beans.DBConfigs.*;

public class SetModel extends BaseModel{

	public SetModel(Object key, Object value,String columnFamilyName) {
		super(Consts.CMD.SET);
		if(key==null||value==null){
			throw new DBException(Words.EX_NULL_EXCEPTION);
		}
		JSONObject json=Tools.getJSON();
		json.put(KEY, key);
		json.put(VALUE, value);
		json.put(CF, columnFamilyName);
		super.setBody(json.toJSONString());
	}
}