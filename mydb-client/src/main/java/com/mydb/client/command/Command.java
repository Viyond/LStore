package com.mydb.client.command;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import com.mydb.client.pool.DBPoolFactory;
import com.mydb.client.session.ServerSessions;
import com.mydb.common.beans.Configs;

/**
 * 功能描述:command,it gather all the commands.
 * @createTime: 2018年1月26日 下午2:17:18
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月26日 下午2:17:18
 * @updateAuthor: l.sl
 * @changesSum:
 */
public class Command{
	private static CommandBridge cmd=new BaseCommandAdapter();
	
	static{
		//initializing the connection pool.
		DBPoolFactory factory=new DBPoolFactory(Configs.get("bind"),Configs.getInteger("port"),Configs.getInteger("auth.expire"));
    	GenericObjectPoolConfig confi=new GenericObjectPoolConfig();
    	confi.setMaxIdle(Configs.getInteger("maxidle",20));
    	confi.setMaxTotal(Configs.getInteger("maxtotal",50));
    	confi.setMinIdle(Configs.getInteger("minidle",2));
    	ServerSessions.pool=new GenericObjectPool<>(factory, confi);
	}
	
	
	public static String get(Object key) {
		return cmd.get(key);
	}

	public static boolean set(Object key, Object value) {
		return cmd.set(key, value);
	}

	public static Map<String, Object> mget(Object... keys) {
		return cmd.mget(keys);
	}

	public static Map<String, Object> mget(List<Object> keys) {
		return cmd.mget(keys);
	}

	public static Map<String, Object> mget(Set<Object> keys) {
		return cmd.mget(keys);
	}

	public static boolean mset(Map<Object, Object> values) {
		return cmd.mset(values);
	}

	public static boolean mset(Object... kvs) {
		return cmd.mset(kvs);
	}

	public static void delete(Object key) {
		cmd.delete(key);
	}

	public static void deleleteRange(Object begin, Object end) {
		cmd.deleleteRange(begin, end);
	}

	public static List<Map<String, Object>> scan(Object begin) {
		return cmd.scan(begin);
	}

	public static List<Map<String, Object>> scan(Object begin, int limit) {
		return cmd.scan(begin, limit);
	}

	public static List<Map<String, Object>> scan(Object begin, boolean asc) {
		return cmd.scan(begin, asc);
	}

	public static List<Map<String, Object>> scan(Object begin, int limit, boolean asc) {
		return cmd.scan(begin, limit, asc);
	}
}