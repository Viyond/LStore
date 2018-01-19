package com.mydb.common.beans;

import com.mydb.common.nio.IOMsgOuterClass.IOMsg;

import io.netty.channel.ChannelHandlerContext;

public class CMDMsg {
	private int cmd,type;
	private ChannelHandlerContext ctx;
	private IOMsg msg;
	
	public CMDMsg(int cmd, ChannelHandlerContext ctx, IOMsg msg) {
		super();
		this.cmd = cmd;
		this.ctx = ctx;
		this.msg = msg;
		this.type=msg.getTYPE();
	}
	
	public int getCmd() {
		return cmd;
	}
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public IOMsg getMsg() {
		return msg;
	}

	public int getType() {
		return type;
	}
}