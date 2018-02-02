package com.mydb.client.command;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public String get(Object key,String columnFamilyName);
	public boolean set(Object key,Object value);
	public boolean set(Object key,Object value,String columnFamilyName);
	public Map<String, Object> mget(Object ... keys);
	public Map<String, Object> mget(List<Object> keys);
	public Map<String, Object> mget(List<Object> keys,String columnFamilyName);
	public Map<String, Object> mget(Set<Object> keys);
	public Map<String, Object> mget(Set<Object> keys,String columnFamilyName);
	public boolean mset(Map<String, String> values);
	public boolean mset(Map<String, String> values,String columnFamilyName);
	public boolean mset(Object ... kvs);
	public void delete(Object key);
	public void delete(Object key,String columnFamilyName);
	public void deleleteRange(Object begin,Object end);
	public void deleleteRange(Object begin,Object end,String columnFamilyName);
	public List<Map<String, Object>> scan(Object begin);
	public List<Map<String, Object>> scan(Object begin,String columnFamilyName);
	public List<Map<String, Object>> scan(Object begin,int limit);
	public List<Map<String, Object>> scan(Object begin,int limit,String columnFamilyName);
	public List<Map<String, Object>> scan(Object begin,boolean asc);
	public List<Map<String, Object>> scan(Object begin,boolean asc,String columnFamilyName);
	public List<Map<String, Object>> scan(Object begin,int limit,boolean asc);
	public List<Map<String, Object>> scan(Object begin,int limit,boolean asc,String columnFamilyName);
	public List<Map<String, Object>> scan(int limit);
	public List<Map<String, Object>> scan(int limit,String columnFamilyName);
	public List<Map<String, Object>> scan(boolean asc);
	public List<Map<String, Object>> scan(boolean asc,String columnFamilyName);
	public List<Map<String, Object>> scan(int limit,boolean asc);
	public List<Map<String, Object>> scan(int limit,boolean asc,String columnFamilyName);
	public List<String> listColumnFamiles();
	public void dropColumnFamily(String columnFamilyName);
}