package com.mydb.client.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import io.netty.channel.ChannelHandlerContext;
import net.minidev.json.JSONObject;

/**
 * 功能描述:线程次连接对象
 * @createTime: 2018年1月18日 下午8:18:21
 * @author: lsl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月18日 下午8:18:21
 * @updateAuthor: lsl
 * @changesSum:
 */
public class CtxResource {
	
	private ChannelHandlerContext ctx;
	private BlockingQueue<JSONObject> resultLock;
	public CtxResource(ChannelHandlerContext ctx) {
		super();
		this.ctx = ctx;
		this.resultLock=new ArrayBlockingQueue<>(1);
	}
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public BlockingQueue<JSONObject> getResultLock() {
		return resultLock;
	}
	
	public void release(){
		try{
			ctx.channel().close();
		}catch(Throwable e){
			e.printStackTrace();
		}
		try{
			ctx.close();
		}catch(Throwable e){
			e.printStackTrace();
		}
		try{
			resultLock=null;
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
}
