package com.mydb.common.beans;

public enum Words {
	EX_NULL_EXCEPTION(1001,"params can not be null!"),
	EX_NOT_PAIR(1002,"params not in pair!"),
	EX_NOT_JSON(1003,"can not convert to json!")
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