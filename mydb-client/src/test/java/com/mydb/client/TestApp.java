package com.mydb.client;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import com.mydb.client.pool.DBPoolFactory;
import com.mydb.client.session.Connections;

public class TestApp {
	public static void main(String[] args) {
		DBPoolFactory factory=new DBPoolFactory(args[0],Integer.parseInt(args[1]));
    	GenericObjectPoolConfig confi=new GenericObjectPoolConfig();
    	confi.setMaxIdle(20);
    	confi.setMaxTotal(100);
    	confi.setMinIdle(5);
    	Connections.pool=new GenericObjectPool<>(factory, confi);
	}
}