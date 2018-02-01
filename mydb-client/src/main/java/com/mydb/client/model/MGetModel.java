package com.mydb.client.model;

import java.util.Collection;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.Tools;
import net.minidev.json.JSONObject;
import static com.mydb.common.beans.DBConfigs.*;

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

	public MGetModel(String columnFamilyName,Collection<Object> keys) {
		super(Consts.CMD.MGET);
		JSONObject json=Tools.getJSON();
		json.put(KEYS, keys);
		json.put(CF, columnFamilyName);
		super.setBody(json.toJSONString());
	}
}