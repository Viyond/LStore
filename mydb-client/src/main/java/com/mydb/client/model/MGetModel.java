package com.mydb.client.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Tools;
import net.minidev.json.JSONObject;

/**
 * 功能描述:MGet
 * @createTime: 2018年1月22日 下午2:16:58
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月22日 下午2:16:58
 * @updateAuthor: lsl
 * @changesSum:
 */
public class MGetModel extends BaseModel{

	public MGetModel(String ... keys) {
		super(Consts.CMD.MGET);
		assemble(Arrays.asList(keys));
	}
	
	public MGetModel(List<String> keys){
		super(Consts.CMD.MGET);
		assemble(keys);
	}
	
	public MGetModel(Set<String> keys){
		super(Consts.CMD.MGET);
		assemble(keys);
	}
	
	private void assemble(Collection<String> k){
		JSONObject json=Tools.getJSON();
		json.put(KEYS, k);
		super.setBody(json.toJSONString());
	}
}