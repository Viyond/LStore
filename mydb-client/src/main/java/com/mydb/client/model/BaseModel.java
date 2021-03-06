package com.mydb.client.model;

import java.util.concurrent.TimeUnit;
import com.mydb.client.pool.CtxResource;
import com.mydb.client.session.ServerSessions;
import com.mydb.common.beans.Configs;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.MsgBuilder;
import com.mydb.common.beans.Tools;
import com.mydb.common.beans.Words;

import net.minidev.json.JSONObject;

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
public class BaseModel {
	
	private int cmd;
	private String body,desc;
	private final static int borrowTimeout=Configs.getInteger("borrow.timeout"),executeTimeout=Configs.getInteger("execute.timeout");
	
	public BaseModel(int cmd) {
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
		ServerSessions.resultCommandMap.put(resource.getCtx().channel().id().asShortText(), resource.getResultLock());
	}
	
	private JSONObject afterProcess(CtxResource resource) throws InterruptedException, DBException{
		JSONObject res=resource.getResultLock().poll(executeTimeout, TimeUnit.SECONDS);
		if(res==null){
			throw new DBException(Words.EX_EXECUTE_TIMEOUT);
		}
		return res;
	}
	
	private JSONObject afterFailedProcess(){
		return Tools.getJSON("s",Consts.STATUS.EXCEPION+"");
	}
	
	private JSONObject afterGetResourceFailedProcess(){
		return Tools.getJSON("s",Consts.STATUS.NORESOURCE+"");
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
	public JSONObject run(){
		CtxResource resource=null;
		try {
			resource=ServerSessions.pool.borrowObject(borrowTimeout);
			beforeProcess(resource);
			process(resource);
			return afterProcess(resource);
		} catch(DBException e){
			return afterGetResourceFailedProcess();
		}catch (Exception e) {
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