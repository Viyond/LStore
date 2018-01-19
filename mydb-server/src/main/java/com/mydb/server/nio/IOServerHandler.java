package com.mydb.server.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.beans.MsgBuilder;
import com.mydb.common.nio.IOMsgOuterClass.IOMsg;
import com.mydb.server.dispatch.CMDDispatcher;
import com.mydb.server.session.ClientSessions;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class IOServerHandler extends SimpleChannelInboundHandler<IOMsg>{
	Logger log=LoggerFactory.getLogger(getClass());
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IOMsg msg) throws Exception {
		int cmd=msg.getCMD();
		try{
			if(!Consts.CMD.CMDS.get(cmd)){
				throw new DBException("Unknown Command!");
			}
			CMDDispatcher.put(cmd, msg, ctx);
		}catch(InterruptedException e){
			ctx.writeAndFlush(MsgBuilder.getExceptionMsg(cmd, "DB IS BUSY,PLEASE RE-TRY LATER!"));
		}catch(DBException e){
			ctx.writeAndFlush(MsgBuilder.getExceptionMsg(cmd, e.getReason()));
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		//设置session到Map
		String id=ctx.channel().id().asShortText();
		ClientSessions.connectionMap.put(id, System.currentTimeMillis());
		ClientSessions.connectionChannelMap.put(id, ctx);
		//发送登录指令
		ctx.writeAndFlush(MsgBuilder.getMsg(Consts.CMD.TO_AUTH));
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}
}
