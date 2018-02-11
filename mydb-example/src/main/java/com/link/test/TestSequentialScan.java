package com.link.test;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import com.mydb.client.command.Command;
import com.mydb.client.nio.IOClient;

public class TestSequentialScan {
	
	private static AtomicInteger ind=new AtomicInteger(0);
	private static Random ran=new Random();
	
	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					String key=Long.toHexString(ran.nextInt(Integer.MAX_VALUE));
					String cf="cc"+ran.nextInt(20);
					for(;;){
						List<Map<String, Object>> list=Command.scan(key,1,cf);
						key=list.get(list.size()-1).keySet().iterator().next();
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