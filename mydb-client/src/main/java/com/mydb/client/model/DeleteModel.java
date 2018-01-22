package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.Tools;

import net.minidev.json.JSONObject;

public class DeleteModel extends CommandModel{

	public DeleteModel(String key) {
		super(Consts.CMD.DEL);
		JSONObject json=Tools.getJSON();
		json.put(KEY, key);
		setBody(json.toJSONString());
	}
	
}
