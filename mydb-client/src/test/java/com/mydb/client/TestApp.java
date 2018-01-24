package com.mydb.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.mydb.client.model.DeleteModel;
import com.mydb.client.model.DeleteRangeModel;
import com.mydb.client.model.GetModel;
import com.mydb.client.model.InfoModel;
import com.mydb.client.model.MGetModel;
import com.mydb.client.model.MSetModel;
import com.mydb.client.model.ScanModel;
import com.mydb.client.model.SetModel;
import com.mydb.client.pool.DBPoolFactory;
import com.mydb.client.session.ServerSessions;
import com.mydb.common.beans.Configs;

public class TestApp {
	public static AtomicInteger ind=new AtomicInteger(0);
    public static void main( String[] args ) throws Exception{
    	DBPoolFactory factory=new DBPoolFactory(Configs.get("bind"),Configs.getInteger("port"),Configs.getInteger("auth.expire"));
    	GenericObjectPoolConfig confi=new GenericObjectPoolConfig();
    	confi.setMaxIdle(20);
    	confi.setMaxTotal(50);
    	confi.setMinIdle(5);
    	ServerSessions.pool=new GenericObjectPool<>(factory, confi);
    	Scanner scan=new Scanner(System.in);
    	for(;;){
    		try{
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
	    		case "mget":
	    			String[] keys=pair[1].split(",");
	    			MGetModel mget=new MGetModel(keys);
	    			Object res=mget.run();
	    			System.out.println(res);
	    			break;
	    		case "scan":
	    			ScanModel sca=new ScanModel(pair[1],Integer.parseInt(pair[2]),pair[3].equals("1"));
	    			Object res2=sca.run();
	    			System.out.println(res2);
	    			break;
	    		case "mset":
	    			Map<String, Object> data=new HashMap<>();
	    			for(int i=1;i<pair.length-1;i+=2){
	    				data.put(pair[i], pair[i+1]);
	    			}
	    			MSetModel mset=new MSetModel(data);
	    			Object msetres=mset.run();
	    			System.out.println(msetres);
	    			break;
	    		case "rdel":
	    			DeleteRangeModel rdel=new DeleteRangeModel(pair[1], pair[2]);
	    			Object rdelres=rdel.run();
	    			System.out.println(rdelres);
	    			break;
	    		case "info":
	    			/*
	    			 	"rocksdb.num-files-at-level<N>" - return the number of files at level <N>, where <N> is an ASCII representation of a level number (e.g. "0"). 
						"rocksdb.stats" - returns a multi-line string that describes statistics about the internal operation of the DB. 
						"rocksdb.sstables" - returns a multi-line string that describes all of the sstables that make up the db contents. 
	    			 */
	    			InfoModel info=new InfoModel(pair[1]);
	    			System.out.println(info.run());
	    			break;
	    			default:
	    				System.out.println("not supported!");
	    				continue;
	    		}
	    		System.out.println("cost "+(System.currentTimeMillis()-begin));
    		}catch(Throwable e){
    			e.printStackTrace();
    			System.out.println();
    		}
    	}
    	
//    	Random ran=new Random();
//    	AtomicLong num=new AtomicLong(Long.parseLong(args[2]));
//    	long end=Long.parseLong(args[3]);
//    	for(int i=0;i<10;i++){
//    		new Thread(new Runnable() {
//    			@Override
//    			public void run() {
//    				while(ind.get()<=end){
//    					long t=num.incrementAndGet();
    					
//    					long t=num.incrementAndGet();
//    					DeleteModel del=new DeleteModel(t+"");
//    					del.run();
//    					ind.incrementAndGet();
    					
    					/*long t=num.incrementAndGet();
    					SetModel set=new SetModel(t+"",UUID.randomUUID().toString());
    					set.run();
    					if(t%10000==0){
    						System.out.println("ind:"+t);
    					}
    					ind.incrementAndGet();*/
    					
//    					String key=ran.nextInt(900000000)+"";
//    					GetModel get=new GetModel(key);
//    					get.run();
//    					ind.incrementAndGet();
    					
//    					ScanModel sca=new ScanModel(ran.nextInt(900000000)+"");
//    					sca.run();
//    					ind.incrementAndGet();
//    					
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