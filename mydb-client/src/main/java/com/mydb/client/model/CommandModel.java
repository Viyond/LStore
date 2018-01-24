package com.mydb.client.model;

import java.util.concurrent.TimeUnit;
import com.mydb.client.pool.CtxResource;
import com.mydb.client.session.ServerSessions;
import com.mydb.common.beans.Configs;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.MsgBuilder;

/**
 * 功能描述:客户端command发送
 * @createTime: 2018年1月18日 下午8:28:38
 * @author: lsl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月18日 下午8:28:38
 * @updateAuthor: lsl
 * @changesSum:
 */
public class CommandModel {
	
	private int cmd;
	private String body,desc;
	private final static int borrowTimeout=Configs.getInteger("borrow.timeout"),executeTimeout=Configs.getInteger("execute.timeout");
	final String KEY="k",KEYS="ks",VALUE="v",VALUES="vs",KEYANDVALUES="kvs",OTHER="o";
	
	public CommandModel(int cmd) {
		super();
		this.cmd = cmd;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private void beforeProcess(CtxResource resource) throws InterruptedException{
		//利用队列来形成阻塞
		resource.getRunLock().put(true);
		ServerSessions.resultCommandMap.put(resource.getCtx().channel().id().asShortText(), resource.getResultLock());
	}
	
	private Object afterProcess(CtxResource resource) throws InterruptedException, DBException{
		Object res=resource.getResultLock().poll(executeTimeout, TimeUnit.SECONDS);
		if(res==null){
			throw new DBException("获取失败!");
		}
		return res;
	}
	
	private Object afterFailedProcess(){
		return null;
	}
	
	private void process(CtxResource resource) throws Exception {
		//能够设置成功则说明发送命令锁已经可用
		resource.getCtx().writeAndFlush(MsgBuilder.getOpMsg(cmd,body==null?"":body, desc==null?"":desc));
	}
	
	/**
	 * 功能描述：利用CtxResource中的putLock和resultLock来实现并发访问隔离
	 * @author:lsl
	 * @return
	 * @return Object
	 * 2018年1月18日 下午8:28:57
	 */
	public Object run(){
		CtxResource resource=null;
		try {
			resource=ServerSessions.pool.borrowObject(borrowTimeout);
			beforeProcess(resource);
			process(resource);
			return afterProcess(resource);
		} catch (Exception e) {
			e.printStackTrace();
			return afterFailedProcess();
		}finally{
			if(resource!=null){
				//在归还资源时统一在里面去释放资源
				ServerSessions.pool.returnObject(resource);
			}
		}
	}
}