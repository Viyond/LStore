package com.link.test;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import com.mydb.client.command.Command;
import com.mydb.client.nio.IOClient;

@SuppressWarnings("all")
public class TestExists {
		private static AtomicInteger ind=new AtomicInteger(0);
		private static Random ran=new Random();
		
		public static void main(String[] args) {
			for(int i=0;i<80;i++){
				new Thread(new Runnable() {
					@Override
					public void run() {
						for(;;){
							try {
								Long lon=ran.nextLong();
								Command.exists(Long.toHexString(lon));
								ind.incrementAndGet();
							} catch (Throwable e) {
								System.out.println("err");
							}
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