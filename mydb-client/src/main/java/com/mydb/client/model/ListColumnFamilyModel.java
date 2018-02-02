package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.Tools;

public class ListColumnFamilyModel extends BaseModel {
	public ListColumnFamilyModel() {
		super(Consts.CMD.CFS);
		setBody(Tools.getJSON().toJSONString());
	}
}
