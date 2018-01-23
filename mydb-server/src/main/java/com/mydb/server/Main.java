package com.mydb.server;

import com.mydb.server.nio.IOServer;
import com.mydb.server.store.MyStore;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args ){
    	//初始化rocksDB
    	System.out.println(MyStore.db);
    	new IOServer().startIO(args[0], Integer.parseInt(args[1]));
    }
}
