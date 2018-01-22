package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.Tools;

import net.minidev.json.JSONObject;

public class GetModel extends CommandModel{

	public GetModel(String key) {
		super(Consts.CMD.GET);
		JSONObject json=Tools.getJSON();
		json.put(KEY, key);
		setBody(json.toJSONString());
	}

}
