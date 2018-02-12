package com.mydb.common.beans;

public enum Words {
	
	EX_NO_RESOURCE(100,"cant't get connection!"),
	EX_OP_FAIL(101,"operation failed!"),
	EX_OP_EXCEPTION(102,"operation throw excepions"),
	EX_RESOURCE_UNVIABLE(103,"connection is not viable,will lose this and create a new one!"),
	
	EX_SERVER_RUNTIME_EXCEPTION(1000,"server errors hadppens!"),
	EX_NULL_EXCEPTION(1001,"params can not be null!"),
	EX_NOT_PAIR(1002,"params not in pair!"),
	EX_NOT_JSON(1003,"can not convert to json!"),
	EX_COLUMNFAMILY_NOTEXISTS(1004,"Column Family no longer exists!"),
	EX_EXECUTE_TIMEOUT(1005,"Execute time-out!!"),
	EX_SERVER_NOTREADY(1006,"server is not ready,re-try later!"),
	;
	
	private int code;
	private String msg;
	
	private Words(int code,String msg){
		this.code=code;
		this.msg=msg;
	}
	
	public int code(){
		return this.code;
	}
	
	public String msg(){
		return this.msg;
	}
	
	public String msg(Object reason){
		return this.msg+":"+reason.toString();
	}
}