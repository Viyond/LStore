package com.link.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import com.mydb.client.command.Command;
import com.mydb.client.model.InfoModel;
import com.mydb.client.nio.IOClient;

public class TestApp {
	public static AtomicInteger ind=new AtomicInteger(0);
    public static void main( String[] args ) throws Exception{
    	Scanner scan=new Scanner(System.in);
    	for(int j=0;j<1;j++){
    		new Thread(new Runnable() {
				@Override
				public void run() {
					for(;;){
		        		try{
		        			System.out.print("请输入指令:");
		    	    		String str=scan.nextLine();
		    	    		//String str=c[ran.nextInt(c.length-1)]+" "+ran.nextInt(50000000)+" "+ran.nextInt(100)+" "+ran.nextInt(50000000);
		    	    		String[] pair=str.split(" ");
		    	    		long begin=System.currentTimeMillis();
		    	    		switch(pair[0]){
		    	    		case "get":
		    	    			System.out.println(Command.get(pair[1]));
		    	    			break;
		    	    		case "set":
		    	    			Command.set(pair[1], pair[2]);
		    	    			break;
		    	    		case "del":
		    	    			Command.delete(pair[1]);
		    	    			break;
		    	    		case "mget":
		    	    			String[] keys=pair[1].split(",");
		    	    			System.out.println(Command.mget(keys));
		    	    			break;
		    	    		case "scan":
		    	    			System.out.println(Command.scan(pair[1],Integer.parseInt(pair[2]),pair[3].equals("1")));
		    	    			break;
		    	    		case "scan2":
		    	    			System.out.println(Command.scan(Integer.parseInt(pair[1]),pair[2].equals("1")));
		    	    			break;
		    	    		case "mset":
		    	    			Map<Object, Object> data=new HashMap<>();
		    	    			for(int i=1;i<pair.length-1;i+=2){
		    	    				data.put(pair[i], pair[i+1]);
		    	    			}
		    	    			Command.mset(data);
		    	    			break;
		    	    		case "rdel":
		    	    			Command.deleleteRange(pair[1], pair[2]);
		    	    			break;
		    	    		case "cf":
		    	    			System.out.println(Command.listColumnFamilies());
		    	    			break;
		    	    		case "dcf":
		    	    			Command.dropColumnFamilies(pair[1]);
		    	    			break;
		    	    		case "exists":
		    	    			System.out.println(Command.exists(pair[1]));
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
		    	    		System.out.println("cost:"+(System.currentTimeMillis()-begin));
		    	    		ind.incrementAndGet();
		        		}catch(Throwable e){
		        			e.printStackTrace();
		        			System.out.println();
		        		}
		        	}
				}
			}).start();
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