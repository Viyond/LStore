package com.link.test;

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
		//String[] str=new String[]{"1234","234","23423","23232","234234","234234"};
		for(int i=0;i<1000;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(;;){
						String key=Long.toHexString(ran.nextInt(Integer.MAX_VALUE));
						//String key=str[ran.nextInt(str.length-1)];
						//System.out.println(key+":"+Command.scan(key,20));
						Command.scan(key,1,"cc"+ran.nextInt(20));
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