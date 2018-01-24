package com.mydb.server;

import com.mydb.common.beans.Configs;
import com.mydb.server.nio.IOServer;
import com.mydb.server.store.MyStore;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args ) throws Exception{
    	//初始化rocksDB
    	System.out.println(MyStore.db);
    	new IOServer().startIO(Configs.get("bind"),Configs.getInteger("port"));
    }
}
