package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.Tools;

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
public class InfoModel extends CommandModel {

	public InfoModel(String propertiy) {
		super(Consts.CMD.INFO);
		setBody(Tools.getJSON(KEY,propertiy).toJSONString());
	}
}
