package com.mydb.server.model;

import java.util.Set;
import com.google.common.collect.Sets;
import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;

import net.minidev.json.JSONArray;

public class MGetModel extends BaseModel {

	private Set<String> keys;
	private final static String[] ARY_MODEL=new String[]{};
	
	public MGetModel(CMDMsg cmdMsg) {
		super(cmdMsg);
		this.keys=Sets.newHashSet(((JSONArray)jbody.get(KEYS)).toArray(ARY_MODEL));
	}

	@Override
	protected Object process() throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

}
