package com.mydb.common.beans;

public class Configs {
	public static String getPWD(){
		return Tools.Md5("123");
	}
}