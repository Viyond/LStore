package com.mydb.client.session;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import com.mydb.client.pool.CtxResource;
import com.mydb.client.pool.DBPoolFactory;
import com.mydb.common.beans.Configs;
import net.minidev.json.JSONObject;

/**
 * 功能描述:服务端session列表
 * @createTime: 2018年1月12日 上午11:33:02
 * @author: liushulin
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月12日 上午11:33:02
 * @updateAuthor: liushulin
 * @changesSum:
 */
public class ServerSessions {
	public static GenericObjectPool<CtxResource> pool;
	static{
		DBPoolFactory factory=new DBPoolFactory(Configs.get("bind"),Configs.getInteger("port"),Configs.getInteger("auth.expire"));
    	GenericObjectPoolConfig confi=new GenericObjectPoolConfig();
    	confi.setMaxIdle(Configs.getInteger("maxidle"));
    	confi.setMaxTotal(Configs.getInteger("maxtotal"));
    	confi.setMinIdle(Configs.getInteger("minidle"));
    	pool=new GenericObjectPool<>(factory, confi);
	}
	public final static Map<String, BlockingQueue<JSONObject>> resultCommandMap=new ConcurrentHashMap<>(50);
}