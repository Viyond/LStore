package com.mydb.common.beans;

public enum Words {
	EX_SERVER_RUNTIME_EXCEPTION(1000,"server errors hadppens!"),
	EX_NULL_EXCEPTION(1001,"params can not be null!"),
	EX_NOT_PAIR(1002,"params not in pair!"),
	EX_NOT_JSON(1003,"can not convert to json!"),
	EX_COLUMNFAMILY_NOTEXISTS(1004,"Column Family no longer exists!"),
	EX_EXECUTE_TIMEOUT(1005,"Execute time-out!!"),
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
}