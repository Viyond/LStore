package com.mydb.common.beans;

import com.mydb.common.nio.IOMsgOuterClass.IOMsg;
import com.mydb.common.nio.IOMsgOuterClass.IOMsg.Builder;

/**
 * 
 * 功能描述:消息构造
 * @createTime: 2018年1月11日 下午5:13:50
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月11日 下午5:13:50
 * @changesSum:
 */
public class MsgBuilder{
	
	public static IOMsg getMsg(int cmd){
		return getMsg(cmd,Consts.TYPE.SYS,Consts.STATUS.OK,null,null);
	}
	
	public static IOMsg getOpMsg(int cmd){
		return getMsg(cmd,Consts.TYPE.OP,Consts.STATUS.OK,null,null);
	}
	
	public static IOMsg getMsg(int cmd,String body){
		return getMsg(cmd,Consts.TYPE.SYS,Consts.STATUS.OK,body,null);
	}
	
	public static IOMsg getOpMsg(int cmd,String body){
		return getMsg(cmd,Consts.TYPE.OP,Consts.STATUS.OK,body,null);
	}
	
	public static IOMsg getOpMsg(int cmd,String body,String desc){
		return getMsg(cmd,Consts.TYPE.OP,Consts.STATUS.OK,body,desc);
	}
	
	public static IOMsg getMsg(int cmd,int type){
		return getMsg(cmd,type,Consts.STATUS.OK,null,null);
	}
	
	public static IOMsg getMsg(int cmd,int type,String body){
		return getMsg(cmd,type,Consts.STATUS.OK,body,null);
	}
	
	public static IOMsg getMsg(int cmd,int type,int status,String body){
		return getMsg(cmd,type,status,body,null);
	}
	
	public static IOMsg getMsg(int cmd,int type,int status,String  body,String desc){
		Builder builder=IOMsg.newBuilder();
		builder.setCMD(cmd);
		if(body!=null){
			builder.setBODY(body);
		}
		if(desc!=null){
			builder.setDES(desc);
		}
		builder.setTYPE(type);
		builder.setSTATUS(status);
		return builder.build();
	}
	
	public static IOMsg getExceptionMsg(int cmd,String body){
		return getMsg(cmd,Consts.TYPE.SYS,Consts.STATUS.NOTOK,body,null);
	}
	
	public static IOMsg getExceptionOpMsg(int cmd,String body){
		return getMsg(cmd,Consts.TYPE.OP,Consts.STATUS.NOTOK,body,null);
	}
}