package com.mydb.client.command;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;

import net.minidev.json.JSONObject;

/**
 * 功能描述:the bridge of commands
 * @createTime: 2018年1月24日 下午5:16:47
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月24日 下午5:16:47
 * @updateAuthor: l.sl
 * @changesSum:
 */
public interface CommandBridge {
	public String get(Object key);
	public boolean set(Object key,Object value);
	public Map<String, Object> mget(Object ... keys);
	public Map<String, Object> mget(List<Object> keys);
	public Map<String, Object> mget(Set<Object> keys);
	public boolean mset(Map<Object, Object> values);
	public boolean mset(Object ... kvs);
	public void delete(Object key);
	public void deleleteRange(Object begin,Object end);
	public List<Map<String, Object>> scan(Object begin);
	public List<Map<String, Object>> scan(Object begin,int limit);
	public List<Map<String, Object>> scan(Object begin,boolean asc);
	public List<Map<String, Object>> scan(Object begin,int limit,boolean asc);
}