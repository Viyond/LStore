package com.mydb.client.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import com.mydb.common.beans.DBException;
import io.netty.channel.ChannelHandlerContext;

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
	public final static Map<String, ChannelHandlerContext> serverMap=new ConcurrentHashMap<>();
	public final static Map<String, BlockingQueue<Object>> resultCommandMap=new ConcurrentHashMap<>(50);
	
	public static ChannelHandlerContext getServer() throws DBException{
		if(serverMap.isEmpty()){
			throw new DBException("server all lost!");
		}
		return serverMap.values().iterator().next();
	}
}