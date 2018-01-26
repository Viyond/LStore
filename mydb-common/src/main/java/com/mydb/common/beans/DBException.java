package com.mydb.common.beans;

public class DBException extends RuntimeException{
	
	private String reason;
	private int code;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBException(int code,String reason){
		this.code=code;
		this.reason=reason;
	}
	
	public DBException(String reason){
		this.code=-1;
		this.reason=reason;
	}
	
	public DBException(Words words){
		this.code=words.code();
		this.reason=words.msg();
	}
	
	public int getCode(){
		return this.code;
	}
	
	public String getReason(){
		return this.reason;
	}
	
	@Override
	public String toString() {
		return String.format("(%s)%s", this.code,this.reason);
	}
}
