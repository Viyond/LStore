package com.mydb.client.model;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.Tools;

/**
 * 功能描述:范围删除[begin,end),左闭右开
 * @createTime: 2018年1月23日 下午1:34:12
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月23日 下午1:34:12
 * @updateAuthor: l.sl
 * @changesSum:
 */
public class DeleteRangeModel extends CommandModel {
	/**
	 * [begin,end)
	 * @param begin include
	 * @param end exclude
	 */
	public DeleteRangeModel(String begin,String end) {
		super(Consts.CMD.DELRANGE);
		setBody(Tools.getJSON(KEY,begin,VALUE,end).toJSONString());
	}

}
