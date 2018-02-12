package com.mydb.client.nio;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mydb.client.dispatch.CMDDispatcher;
import com.mydb.client.pool.DBPoolFactory;
import com.mydb.common.beans.Consts;
import com.mydb.common.beans.DBException;
import com.mydb.common.nio.IOMsgOuterClass.IOMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;

public class IOClientHandler extends SimpleChannelInboundHandler<IOMsg>{
	Logger log=LoggerFactory.getLogger(getClass());

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IOMsg msg) throws Exception {
		int cmd=msg.getCMD();
		try{
			if(!Consts.CMD.CMDS.get(cmd)){
				throw new DBException("Unknown Command!");
				//为了使用链接次，登录需要在这里做特殊处理
			}else if(Consts.CMD.AUTH_SUCCESS==cmd){
				authSuccess(ctx);
				return;
			}
			CMDDispatcher.put(cmd, msg, ctx);
		}catch(InterruptedException e){
			log.error("DB IS BUSY,PLEASE RE-TRY LATER!",e);
			log.error("",e);
		}catch(DBException e){
			log.error("",e);
		}
	}

	private void authSuccess(ChannelHandlerContext ctx) throws InterruptedException{
		//登录完成后放入登录队列
		DBPoolFactory.loginQuee.put(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}
}
