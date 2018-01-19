package com.mydb.server;

import com.mydb.server.nio.IOServer;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args ){
    	
    	new IOServer().startIO(args[0], Integer.parseInt(args[1]));
    }
}
