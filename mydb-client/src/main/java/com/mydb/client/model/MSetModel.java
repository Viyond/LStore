package com.mydb.client.model;

import java.util.Map;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.Tools;
import net.minidev.json.JSONObject;
import static com.mydb.common.beans.DBConfigs.*;

/**
 * 功能描述:MSet Model
 * @createTime: 2018年1月23日 上午11:10:59
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月23日 上午11:10:59
 * @updateAuthor: l.sl
 * @changesSum:
 */
public class MSetModel extends BaseModel{

	public MSetModel(Map<String, String> values,String columnFamilyName) {
		super(Consts.CMD.MSET);
		JSONObject json=Tools.getJSON();
		json.put(VALUE, new JSONObject(values));
		json.put(CF, columnFamilyName);
		setBody(json.toJSONString());
	}
}
