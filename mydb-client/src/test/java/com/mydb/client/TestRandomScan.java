package com.mydb.client;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import com.mydb.client.command.Command;
import com.mydb.client.nio.IOClient;

public class TestRandomScan {
	
	private static AtomicInteger ind=new AtomicInteger(0);
	private static Random ran=new Random();
	
	public static void main(String[] args) {
		for(int i=0;i<20;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(;;){
						String key=Long.toHexString(ran.nextInt(Integer.MAX_VALUE));
						//System.out.println(key+":"+Command.scan(key,20));
						Command.scan(key,20);
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