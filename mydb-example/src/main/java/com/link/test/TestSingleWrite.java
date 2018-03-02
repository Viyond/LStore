package com.link.test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import com.mydb.client.command.Command;
import com.mydb.client.nio.IOClient;

public class TestSingleWrite {
	
	public static AtomicInteger ind=new AtomicInteger(0),seed=new AtomicInteger(0);
	
	public static void main(String[] args) {
		
		for(int i=0;i<100;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {                                                                      
					for(;;){
						int sed=seed.incrementAndGet();
						Command.set("tttt"+sed, UUID.randomUUID().toString()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID()+UUID.randomUUID());
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