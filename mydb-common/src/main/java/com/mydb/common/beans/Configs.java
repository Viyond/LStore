package com.mydb.common.beans;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述:配置相关
 * @createTime: 2018年1月24日 下午1:54:45
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月24日 下午1:54:45
 * @updateAuthor: l.sl
 * @changesSum:
 */
public class Configs {
	private static Properties prop;
	private final static Logger log=LoggerFactory.getLogger(Configs.class);
	static{
		prop=new Properties();
		try {
			prop.load(new FileInputStream("./conf/config.properties"));
		} catch (IOException e) {
			log.error("",e);
		}
	}
	
	public static String get(String key){
		return get(key,null);
	}
	
	public static String get(String key,String defaultvalue){
		return prop.getProperty(key,defaultvalue);
	}
	
	public static Integer getInteger(String key,Integer defaultvalue){
		if(!prop.containsKey(key)) return defaultvalue;
		return Integer.parseInt(prop.getProperty(key));
	}
	
	public static Integer getInteger(String key){
		return getInteger(key,null);
	}
	
	public static String getPWD(){
		return Tools.Md5(get("auth"));
	}
}