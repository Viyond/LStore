package com.mydb.server.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.MsgBuilder;
import com.mydb.common.beans.Tools;
import com.mydb.common.nio.IOMsgOuterClass.IOMsg;
import io.netty.channel.ChannelHandlerContext;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;

/**
 * 功能描述:server's baseModel
 * @createTime: 2018年1月22日 下午12:28:41
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月22日 下午12:28:41
 * @updateAuthor: lsl
 * @changesSum:
 */
public abstract class BaseModel {
	
	protected Logger log=LoggerFactory.getLogger(getClass());
			
	int cmd;
	int type;
	String body;
	JSONObject jbody;
	String desc;
	ChannelHandlerContext ctx;
	
	final String KEY="k",KEYS="ks",VALUE="v",VALUES="vs",KEYANDVALUES="kvs",OTHER="o";
	
	public BaseModel(CMDMsg cmdMsg){
		try{
			this.cmd=cmdMsg.getCmd();
			this.ctx=cmdMsg.getCtx();
			IOMsg msg=cmdMsg.getMsg();
			this.type=msg.getTYPE();
			this.body=msg.getBODY();
			this.desc=msg.getDES();
			this.jbody=Tools.parseJson(this.body);
		} catch (Throwable e) {
			log.error("",e);
			this.ctx.writeAndFlush(MsgBuilder.getExceptionMsg(this.cmd,"body can not transform to json"));
		}
	}
	
	public void run(){
		try{
			this.beforeProcess();
			this.afterSuccessProcess(process());
		}catch(Throwable e){
			this.afterUnsuccessProcess(e.getMessage());
		}
	}
	
	protected void beforeProcess(){
		
	}
	protected abstract Object process() throws Exception,DBException;
	
	protected void afterSuccessProcess(Object res){
		ctx.writeAndFlush(MsgBuilder.getOpMsg(cmd, res.toString()));
	}
	
	protected void afterUnsuccessProcess(String exmsg){
		ctx.writeAndFlush(MsgBuilder.getExceptionOpMsg(cmd, exmsg));
	}
	
}