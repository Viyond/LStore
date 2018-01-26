package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.Tools;
import com.mydb.common.beans.Words;

/**
 * 功能描述:info model to get info from db
 * @createTime: 2018年1月23日 下午1:57:41
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月23日 下午1:57:41
 * @updateAuthor: l.sl
 * @changesSum:
 */
public class InfoModel extends BaseModel {

	public InfoModel(String property) {
		super(Consts.CMD.INFO);
		if(property==null){
			throw new DBException(Words.EX_NULL_EXCEPTION);
		}
		setBody(Tools.getJSON(KEY,property).toJSONString());
	}
}
