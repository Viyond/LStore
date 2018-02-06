package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.Tools;
import static com.mydb.common.beans.DBConfigs.*;

public class ExistsModel extends BaseModel{
	public ExistsModel(String key,String columnFamilyName) {
		super(Consts.CMD.EXISTS);
		setBody(Tools.getJSON(KEY,key,CF,columnFamilyName).toJSONString());
	}
}
