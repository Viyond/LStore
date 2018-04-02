package com.mydb.common.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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
	private final static String PROPERTIY_FILE=System.getProperty("file.separator")+"lstore.properties";
	
	private static String lstoreConfigFile;
	static{
		prop=new Properties();
		try {
			if(lstoreConfigFile==null){
				boolean loaded=false;
				List<String> classpaths=Arrays.asList(System.getProperty("java.class.path").split(";")).stream().filter(e -> !e.endsWith(".jar")).collect(Collectors.toList());
				for(String path : classpaths){
					log.info("try loading lstore.properties from {}",path);
					File file=new File(path+PROPERTIY_FILE);
					if(file.exists()){
						log.info("find lstore.properties,load it from {}",path+PROPERTIY_FILE);
						prop.load(new FileInputStream(path+PROPERTIY_FILE));
						loaded=true;
						break;
					}else{
						log.info("not found,try another directory");
					}
				}
				if(!loaded){
					throw new FileNotFoundException("CAN NOT FOUND THE lstore.properties");
				}
			}else{
				prop.load(new FileInputStream(lstoreConfigFile));
			}
		} catch (IOException e) {
			log.error("",e);
			System.exit(0);
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
	
	public static Double getDouble(String key,Double defaultvalue){
		if(!prop.containsKey(key)) return defaultvalue;
		return Double.parseDouble(prop.getProperty(key));
	}
	
	public static String getPWD(){
		return Tools.Md5(get("auth"));
	}
}