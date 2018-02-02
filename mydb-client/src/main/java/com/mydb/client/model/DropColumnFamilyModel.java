package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.Tools;
import static com.mydb.common.beans.DBConfigs.*;

/**
 * 功能描述:删掉ColumnFamily
 * @createTime: 2018年2月2日 下午2:44:03
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年2月2日 下午2:44:03
 * @updateAuthor: l.sl
 * @changesSum:
 */
public class DropColumnFamilyModel extends BaseModel{

	public DropColumnFamilyModel(String columnFamilyName) {
		super(Consts.CMD.DROPCF);
		setBody(Tools.getJSON(KEY,columnFamilyName).toJSONString());
	}
}
