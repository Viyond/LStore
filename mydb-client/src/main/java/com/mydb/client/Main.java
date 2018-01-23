package com.mydb.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.mydb.client.model.MSetModel;
import com.mydb.client.nio.IOClient;
import com.mydb.client.pool.DBPoolFactory;
import com.mydb.client.session.ServerSessions;

/**
 * Hello world!
 *
 */
public class Main {
	public static AtomicInteger ind=new AtomicInteger(0);
    public static void main( String[] args ) throws Exception{
    	DBPoolFactory factory=new DBPoolFactory(args[0],Integer.parseInt(args[1]));
    	GenericObjectPoolConfig confi=new GenericObjectPoolConfig();
    	confi.setMaxIdle(20);
    	confi.setMaxTotal(50);
    	confi.setMinIdle(5);
    	ServerSessions.pool=new GenericObjectPool<>(factory, confi);
    	AtomicLong num=new AtomicLong(1000000000);
    	for(int i=0;i<10;i++){
    		new Thread(new Runnable() {
    			@Override
    			public void run() {
    				for(int i=0;i<10000;i++){
    					Map<String, Object> map=new HashMap<>();
        				for(int j=0;j<1000;j++){
        					long t=num.incrementAndGet();
        					map.put("0000000000000000000000"+t, UUID.randomUUID().toString()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID());
        				}
        				long begin=System.currentTimeMillis();
        				MSetModel mset=new MSetModel(map);
        				mset.run();
        				System.out.println("cost:"+(System.currentTimeMillis()-begin));
        				ind.incrementAndGet();
    				}
    			}
    		}).start();
    	}
    	new Timer().schedule(new TimerTask() {
    		int t=ind.get();
			@Override
			public void run() {
				int tt=ind.get();
				System.out.println("QPS:"+(tt-t));
				t=tt;
			}
		}, 1000,1000);
    	
    	//shutdown hook
    	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				IOClient.group.shutdownGracefully();
			}
		}));
    }
}
