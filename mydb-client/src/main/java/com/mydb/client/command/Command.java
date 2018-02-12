package com.mydb.client.command;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private final static Logger log=LoggerFactory.getLogger(Command.class);
	static{
		try{
			//initializing the connection pool.
			DBPoolFactory factory=new DBPoolFactory(Configs.getInteger("auth.expire"));
	    	GenericObjectPoolConfig confi=new GenericObjectPoolConfig();
	    	confi.setMaxIdle(Configs.getInteger("maxidle",20));
	    	confi.setMaxTotal(Configs.getInteger("maxtotal",50));
	    	confi.setMinIdle(Configs.getInteger("minidle",2));
	    	confi.setTestOnBorrow(true);
	    	ServerSessions.pool=new GenericObjectPool<>(factory, confi);
		}catch(Throwable e){
			log.error("connection pool initializing error!");
			log.error("",e);
			System.exit(0);
		}
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

	public static boolean mset(Map<String, String> values) {
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
	
	public static List<Map<String, Object>> scan(int limit) {
		return cmd.scan(limit);
	}

	public static List<Map<String, Object>> scan(boolean asc) {
		return cmd.scan(asc);
	}

	public static List<Map<String, Object>> scan(int limit, boolean asc) {
		return cmd.scan(limit, asc);
	}

	public static String get(Object key, String columnFamilyName) {
		return cmd.get(key, columnFamilyName);
	}

	public static boolean set(Object key, Object value, String columnFamilyName) {
		return cmd.set(key, value, columnFamilyName);
	}

	public static Map<String, Object> mget(List<Object> keys, String columnFamilyName) {
		return cmd.mget(keys, columnFamilyName);
	}

	public static Map<String, Object> mget(Set<Object> keys, String columnFamilyName) {
		return cmd.mget(keys, columnFamilyName);
	}

	public static boolean mset(Map<String, String> values, String columnFamilyName) {
		return cmd.mset(values, columnFamilyName);
	}

	public static void delete(Object key, String columnFamilyName) {
		cmd.delete(key, columnFamilyName);
	}

	public static void deleleteRange(Object begin, Object end, String columnFamilyName) {
		cmd.deleleteRange(begin, end, columnFamilyName);
	}

	public static List<Map<String, Object>> scan(Object begin, String columnFamilyName) {
		return cmd.scan(begin, columnFamilyName);
	}

	public static List<Map<String, Object>> scan(Object begin, int limit, String columnFamilyName) {
		return cmd.scan(begin, limit, columnFamilyName);
	}

	public static List<Map<String, Object>> scan(Object begin, boolean asc, String columnFamilyName) {
		return cmd.scan(begin, asc, columnFamilyName);
	}

	public static List<Map<String, Object>> scan(Object begin, int limit, boolean asc, String columnFamilyName) {
		return cmd.scan(begin, limit, asc, columnFamilyName);
	}

	public static List<Map<String, Object>> scan(int limit, String columnFamilyName) {
		return cmd.scan(limit, columnFamilyName);
	}

	public static List<Map<String, Object>> scan(boolean asc, String columnFamilyName) {
		return cmd.scan(asc, columnFamilyName);
	}

	public static List<Map<String, Object>> scan(int limit, boolean asc, String columnFamilyName) {
		return cmd.scan(limit, asc, columnFamilyName);
	}
	
	public static List<String> listColumnFamilies(){
		return cmd.listColumnFamiles();
	}
	
	public static void dropColumnFamilies(String columnFamilyName){
		cmd.dropColumnFamily(columnFamilyName);
	}
	
	public static boolean exists(String key){
		return cmd.exists(key);
	}
	
	public static boolean exists(String key,String columnFamilyName){
		return cmd.exists(key, columnFamilyName);
	}
}