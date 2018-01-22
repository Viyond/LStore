package com.mydb.client;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.mydb.client.model.DeleteModel;
import com.mydb.client.model.GetModel;
import com.mydb.client.model.SetModel;
import com.mydb.client.nio.IOClient;
import com.mydb.client.pool.CtxResource;
import com.mydb.client.pool.DBPoolFactory;

/**
 * Hello world!
 *
 */
public class Main {
	public static GenericObjectPool<CtxResource> pool;
	public static AtomicInteger ind=new AtomicInteger(0);
    public static void main( String[] args ) throws Exception{
    	DBPoolFactory factory=new DBPoolFactory(args[0],Integer.parseInt(args[1]));
    	GenericObjectPoolConfig confi=new GenericObjectPoolConfig();
    	confi.setMaxIdle(20);
    	confi.setMaxTotal(100);
    	confi.setMinIdle(5);
    	pool=new GenericObjectPool<>(factory, confi);
    	Scanner scan=new Scanner(System.in);
    	for(;;){
    		System.out.print("请输入指令:");
    		String str=scan.nextLine();
    		String[] pair=str.split(" ");
    		long begin=System.currentTimeMillis();
    		switch(pair[0]){
    		case "get":
    			GetModel get=new GetModel(pair[1]);
    			System.out.println(get.run());
    			break;
    		case "set":
    			SetModel set=new SetModel(pair[1], pair[2]);
    			set.run();
    			break;
    		case "del":
    			DeleteModel del=new DeleteModel(pair[1]);
    			del.run();
    			break;
    			default:
    				System.out.println("not supported!");
    				continue;
    		}
    		System.out.println("cost "+(System.currentTimeMillis()-begin));
    	}
    	
//    	Random ran=new Random();
//    	AtomicLong num=new AtomicLong(0);
//    	for(int i=0;i<50;i++){
//    		new Thread(new Runnable() {
//    			@Override
//    			public void run() {
//    				while(ind.get()<400000000){
//    					long t=num.incrementAndGet();
//    					SetModel set=new SetModel(t+"",UUID.randomUUID().toString());
//    					set.run();
//    					if(t%10000==0){
//    						System.out.println("ind:"+t);
//    					}
//    					ind.incrementAndGet();
//    					/*String key=ran.nextInt(35000000)+"";
//    					GetModel get=new GetModel(key);
//    					get.run();
//    					ind.incrementAndGet();*/
//    				}
//    			}
//    		}).start();
//    	}
//    	new Timer().schedule(new TimerTask() {
//    		int t=ind.get();
//			@Override
//			public void run() {
//				int tt=ind.get();
//				System.out.println("QPS:"+(tt-t));
//				t=tt;
//			}
//		}, 1000,1000);
//    	
//    	//shutdown hook
//    	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//			@Override
//			public void run() {
//				IOClient.group.shutdownGracefully();
//			}
//		}));
    }
}
