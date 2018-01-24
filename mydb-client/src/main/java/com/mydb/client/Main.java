package com.mydb.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.mydb.client.model.GetModel;
import com.mydb.client.model.MSetModel;
import com.mydb.client.model.ScanModel;
import com.mydb.client.model.SetModel;
import com.mydb.client.nio.IOClient;
import com.mydb.client.pool.DBPoolFactory;
import com.mydb.client.session.ServerSessions;
import com.mydb.common.beans.Configs;

/**
 * Hello world!
 *
 */
public class Main {
	public static AtomicInteger ind=new AtomicInteger(0);
	static int k=0;
    public static void main( String[] args ) throws Exception{
    	AtomicLong num=new AtomicLong(0);
    	Random ran=new Random();
    	long seed=1010000000;
    	for(int i=0;i<10;i++){
    		new Thread(new Runnable() {
    			@Override
    			public void run() {
    				while(true){
    					String key="0000000000000000000000"+(seed+ran.nextInt(19999999));
//    					for(int i=0;i<1000;i++){
//    						long begin=System.currentTimeMillis();
//    						Map<String, Object> res=new HashMap<>();
//    						for(int j=0;j<10000;j++){
//    							String key="0000000000000000000000"+(seed+(k++));
//    							res.put(key, UUID.randomUUID().toString()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID());
//    						}
//    						MSetModel mset=new MSetModel(res);
//    						mset.run();
//    						ind.incrementAndGet();
//    						System.out.println("cost:"+(System.currentTimeMillis()-begin));
//    					}
//    					break;
    					
    					GetModel get=new GetModel(key);
    					get.run();
    					
//    					ScanModel scan=new ScanModel(key,10);
//    					scan.run();
    					ind.incrementAndGet();
    					//System.out.println();
    					/*Map<String, Object> vals=new HashMap<>();
    					for(int i=0;i<100;i++){
    						long tv=num.getAndIncrement();
        					if(tv%50000==0){
        						System.out.println(tv);
        					}
        					if(tv>seed){
        						System.out.println("done~~");
        						System.exit(0);
        					}
    						String key="0000000000000000000000"+(seed+tv);
    						vals.put(key, UUID.randomUUID()+""+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID());
    					}
    					MSetModel mset=new MSetModel(vals);
    					mset.run();
    					ind.incrementAndGet();*/
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
