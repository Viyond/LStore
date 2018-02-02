package com.mydb.server.dispatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.Configs;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.MsgBuilder;
import com.mydb.server.model.DeleteModel;
import com.mydb.server.model.DeleteRangeModel;
import com.mydb.server.model.DropColumnFamilyModel;
import com.mydb.server.model.GetModel;
import com.mydb.server.model.InfoModel;
import com.mydb.server.model.ListColumnFamilyModel;
import com.mydb.server.model.MGetModel;
import com.mydb.server.model.MSetModel;
import com.mydb.server.model.ScanModel;
import com.mydb.server.model.SetModel;
import com.mydb.server.session.ClientSessions;
import io.netty.channel.ChannelHandlerContext;

/**
 * 功能描述:命令执行者
 * @createTime: 2018年1月12日 上午11:02:32
 * @author: liushulin
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月12日 上午11:02:32
 * @updateAuthor: liushulin
 * @changesSum:
 */
public class ServerCmdExecutor implements Runnable{
	
	private final static Logger log=LoggerFactory.getLogger(ServerCmdExecutor.class);
	
	private CMDMsg cmdMsg;
	
	private String serverpwd=Configs.getPWD();
	
	public ServerCmdExecutor(CMDMsg cmdMsg) {
		super();
		this.cmdMsg = cmdMsg;
	}

	@Override
	public void run() {
		int type=cmdMsg.getType(),cmd=cmdMsg.getCmd();
		try{
			switch(type){
			case Consts.TYPE.SYS:
				doSys();
				break;
			case Consts.TYPE.OP:
				doOp();
				break;
			default:
				throw new DBException("not support yet!");
			}
		}catch(DBException e){
			cmdMsg.getCtx().writeAndFlush(MsgBuilder.getExceptionMsg(cmd,e.getReason()));
		}
	}
	
	private void doSys() throws DBException{
		int cmd=cmdMsg.getCmd();
		switch(cmd){
		case Consts.CMD.AUTH:
			doAuth(cmdMsg);
			break;
		case Consts.CMD.RUN:
			run(cmdMsg);
			break;
		default:
			throw new DBException("not support yet!");
		}
	}
	
	private void doOp() throws DBException{
		int cmd=cmdMsg.getCmd();
		switch(cmd){
		case Consts.CMD.SET:
			new SetModel(cmdMsg).run();
			break;
		case Consts.CMD.GET:
			new GetModel(cmdMsg).run();
			break;
		case Consts.CMD.MSET:
			new MSetModel(cmdMsg).run();
			break;
		case Consts.CMD.MGET:
			new MGetModel(cmdMsg).run();
			break;
		case Consts.CMD.DEL:
			new DeleteModel(cmdMsg).run();
			break;
		case Consts.CMD.SCAN:
			new ScanModel(cmdMsg).run();
			break;
		case Consts.CMD.DELRANGE:
			new DeleteRangeModel(cmdMsg).run();
			break;
		case Consts.CMD.INFO:
			new InfoModel(cmdMsg).run();
			break;
		case Consts.CMD.CFS:
			new ListColumnFamilyModel(cmdMsg).run();
			break;
		case Consts.CMD.DROPCF:
			new DropColumnFamilyModel(cmdMsg).run();
			break;
		default:
			throw new DBException("not support yet!");
		}
	}
	
	private void doAuth(CMDMsg cmdMsg) throws DBException{
		String pwd=cmdMsg.getMsg().getBODY();
		//return auth_success
		if(serverpwd.equals(pwd)){
			ChannelHandlerContext ctx=cmdMsg.getCtx();
			String id=ctx.channel().id().asShortText();
			//处理clientSessions
			ClientSessions.connectionChannelMap.remove(id);
			ClientSessions.connectionMap.remove(id);
			ClientSessions.sessionMap.put(id,ctx);
			ctx.writeAndFlush(MsgBuilder.getMsg(Consts.CMD.AUTH_SUCCESS,"yo yeah,congratulations!"));
			log.debug("{} login success",cmdMsg.getMsg());
		}else{
			cmdMsg.getCtx().writeAndFlush(MsgBuilder.getMsg(Consts.CMD.AUTH_FAIL));
		}
	}
	
	private void run(CMDMsg cmdMsg) throws DBException{
		cmdMsg.getCtx().writeAndFlush(MsgBuilder.getMsg(Consts.CMD.RUN));
	}
}
