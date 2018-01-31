package com.mydb.client;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import com.mydb.client.command.Command;
import com.mydb.client.nio.IOClient;

public class TestScanFromBeginToEnd {
	
	private static AtomicInteger ind=new AtomicInteger(0);
	private static Random ran=new Random();
	
	public static void main(String[] args) {
		for(int i=0;i<1;i++){
			new Thread(new Runnable() {
				int size=0;
				@Override
				public void run() {
					List<Map<String, Object>> list=Command.scan(10000,true);
					String lastKey=list.get(list.size()-1).keySet().iterator().next();
					size+=list.size();
					do{
						ind.incrementAndGet();
						list=Command.scan(lastKey,10000,true);
						if(list.size()==10000){
							lastKey=list.get(list.size()-1).keySet().iterator().next();
						}else{
							lastKey=null;
						}
						size+=list.size();
						System.out.println("---->scaned:"+size);
					}while(lastKey!=null);
					System.out.println("done~all size:"+size);
					System.exit(0);
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