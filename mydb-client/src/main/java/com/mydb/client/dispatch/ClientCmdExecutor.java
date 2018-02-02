package com.mydb.client.dispatch;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.mydb.client.Main;
import com.mydb.client.session.ServerSessions;
import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.Configs;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.MsgBuilder;
import com.mydb.common.beans.Tools;
import com.mydb.common.nio.IOMsgOuterClass.IOMsg;

import net.minidev.json.JSONObject;

/**
 * 功能描述:command runner
 * @createTime: 2018年1月19日 下午3:19:45
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月19日 下午3:19:45
 * @updateAuthor: lsl
 * @changesSum:
 */
public class ClientCmdExecutor implements Runnable{
	
	private final static Logger log=LoggerFactory.getLogger(ClientCmdExecutor.class);
	private CMDMsg cmdMsg;

	public ClientCmdExecutor(CMDMsg cmdMsg) {
		super();
		this.cmdMsg = cmdMsg;
	}

	@Override
	public void run() {
		int type=cmdMsg.getType();
		try{
			switch(type){
				case Consts.TYPE.SYS:{
					doSys();
				}
				break;
				case Consts.TYPE.OP:{
					doOp();
				}
				break;
				default:
					throw new DBException("unknown type!"+type);
			}
		}catch(DBException e){
			log.error("exception: {},{}",e.getReason(),cmdMsg.getMsg());
			if(e.getCode()==-1){
				cmdMsg.getCtx().close();
				System.exit(-1);
			}
		}
	}
	
	private void doSys() throws DBException{
		int cmd=cmdMsg.getCmd();
		switch(cmd){
		case Consts.CMD.RUN:
			run(cmdMsg);
			break;
		case Consts.CMD.TO_AUTH:
			doAuth(cmdMsg);
			break;
		case Consts.CMD.AUTH_FAIL:
			throw new DBException(-1,"auth failed!!");
			default:
				throw new DBException("command not support yet!");
		}
	}
	
	private void doOp() throws DBException{
		IOMsg msg=cmdMsg.getMsg();
		String body=msg.hasField(IOMsg.getDescriptor().findFieldByNumber(IOMsg.BODY_FIELD_NUMBER))?msg.getBODY():null;
		String id=cmdMsg.getCtx().channel().id().asShortText();
		int status=cmdMsg.getMsg().getSTATUS();
		BlockingQueue<JSONObject> resultLock=ServerSessions.resultCommandMap.get(id);
		JSONObject result=Tools.getJSON("v",body,"s",status+"");
		try{
			//存在时才处理
			if(resultLock!=null){
				resultLock.add(result);
			}
			//如果放入失败,则先拿取在放入
		}catch(IllegalStateException e){
			resultLock.poll();
			resultLock.add(result);
		}
	}
	
	private void doAuth(CMDMsg cmdMsg) throws DBException{
		cmdMsg.getCtx().writeAndFlush(MsgBuilder.getMsg(Consts.CMD.AUTH,Configs.getPWD()));
	}
	
	private void run(CMDMsg cmdMsg) throws DBException{
		cmdMsg.getCtx().writeAndFlush(MsgBuilder.getMsg(Consts.CMD.RUN));
	}
}
