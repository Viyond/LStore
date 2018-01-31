package com.mydb.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.mydb.client.command.Command;
import com.mydb.client.nio.IOClient;

public class TestWrite {
	
	public static AtomicInteger ind=new AtomicInteger(0);
	
	public static void main(String[] args) {
		
		for(int i=0;i<10;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(int i=0;i<10000;i++){
						Map<String, String> map=new HashMap<>();
						for(int j=0;j<10000;j++){
							map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
						}
						Command.mset(map);
						ind.incrementAndGet();
						System.out.println("at:"+i);
					}
					System.out.println("done~");
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