package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.Tools;

import net.minidev.json.JSONObject;

public class SetModel extends CommandModel{

	public SetModel(String key, String value) {
		super(Consts.CMD.SET);
		JSONObject json=Tools.getJSON();
		json.put(super.KEY, key);
		json.put(super.VALUE, value);
		super.setBody(json.toJSONString());
	}
}