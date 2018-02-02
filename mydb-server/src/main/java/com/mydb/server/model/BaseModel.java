package com.mydb.server.model;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.MsgBuilder;
import com.mydb.common.beans.Tools;
import com.mydb.common.nio.IOMsgOuterClass.IOMsg;
import com.mydb.server.store.MyStore;

import io.netty.channel.ChannelHandlerContext;
import net.minidev.json.JSONObject;
import static com.mydb.common.beans.DBConfigs.*;

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
	public BaseModel(CMDMsg cmdMsg){
		try{
			this.cmd=cmdMsg.getCmd();
			this.ctx=cmdMsg.getCtx();
			IOMsg msg=cmdMsg.getMsg();
			this.type=msg.getTYPE();
			this.body=msg.getBODY();
			this.desc=msg.getDES();
			this.jbody=(JSONObject)Tools.parseJson(this.body);
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
			log.error("",e);
			jbody.put("ex",e.getMessage());
			jbody.put("cmd",cmd);
			jbody.put("type",type);
			this.afterUnsuccessProcess();
		}
	}
	
	/**
	 * 功能描述：获取columnFamily,不存在则会创建
	 * @author:l.sl
	 * @return
	 * @throws RocksDBException
	 * @return ColumnFamilyHandle
	 * 2018年2月1日 下午5:58:31
	 */
	protected ColumnFamilyHandle getColumnFamily() throws RocksDBException {
		String cf=jbody.getAsString(CF);
		return cf==null?MyStore.getDefaultColumnFamilyHandler():MyStore.getAndCreateColumnFamilyHandler(cf);
	}
	
	protected void beforeProcess() throws RocksDBException{
	}
	protected abstract Object process() throws Exception,DBException;
	
	protected void afterSuccessProcess(Object res){
		ctx.writeAndFlush(MsgBuilder.getOpMsg(cmd, res==null?null:res.toString()));
	}
	
	protected void afterUnsuccessProcess(){
		ctx.writeAndFlush(MsgBuilder.getExceptionOpMsg(cmd,jbody.toJSONString()));
	}
	
}