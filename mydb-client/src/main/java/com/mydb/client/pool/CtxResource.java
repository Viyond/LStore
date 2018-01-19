package com.mydb.client.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import io.netty.channel.ChannelHandlerContext;

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
	private BlockingQueue<Boolean> runLock;
	private BlockingQueue<Object> resultLock;
	public CtxResource(ChannelHandlerContext ctx) {
		super();
		this.ctx = ctx;
		this.runLock=new ArrayBlockingQueue<>(1);
		this.resultLock=new ArrayBlockingQueue<>(1);
	}
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public BlockingQueue<Boolean> getRunLock() {
		return runLock;
	}
	public BlockingQueue<Object> getResultLock() {
		return resultLock;
	}
	
	public void release(){
		try{
			ctx.close();
		}catch(Throwable e){
			e.printStackTrace();
		}
		runLock=null;
		resultLock=null;
	}
}
