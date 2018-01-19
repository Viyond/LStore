package com.mydb.server.session;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.mydb.common.beans.ThreadPool;

import io.netty.channel.ChannelHandlerContext;

public class ClientSessions{
	private static boolean on=true;
	public final static Map<String,Long> connectionMap=new ConcurrentHashMap<>(100);
	public final static Map<String,ChannelHandlerContext> connectionChannelMap=new ConcurrentHashMap<>(100);
	public final static Map<String, ChannelHandlerContext> sessionMap=new ConcurrentHashMap<>();
	static{
		ThreadPool.getInstance().submit(new Runnable() {
			@Override
			public void run() {
				while(on){
					try{
						long now=System.currentTimeMillis();
						for(Entry<String, Long> e : connectionMap.entrySet()){
							if(now-e.getValue()>60000){
								String key=e.getKey();
								//从登录map中移除
								connectionMap.remove(key);
								//关闭channel
								ChannelHandlerContext ctx=connectionChannelMap.get(key);
								//从channelMap中移除
								connectionChannelMap.remove(key);
								if(ctx!=null){
									ctx.close();
								}
							}
						}
						Thread.sleep(3000);
					}catch(Throwable e){
						e.printStackTrace();
						continue;
					}
				}
			}
		});
	}
	
	@Override
	protected void finalize() throws Throwable {
		on=false;
		super.finalize();
	}
}