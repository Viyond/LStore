package com.mydb.client;

import java.util.Scanner;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.mydb.client.pool.CtxResource;
import com.mydb.client.pool.DBPoolFactory;

public class TestApp {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		DBPoolFactory factory=new DBPoolFactory("192.168.1.246",6688);
    	GenericObjectPoolConfig confi=new GenericObjectPoolConfig();
    	confi.setMaxIdle(20);
    	confi.setMaxTotal(30);
    	confi.setMinIdle(5);
    	GenericObjectPool<CtxResource> pool=new GenericObjectPool<>(factory, confi);
		for(;;){
			
		}
	}
}