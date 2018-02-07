package com.link.test;

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
					for(int k=5;k<20;k++){
						String col="cc"+k;
						for(int i=0;i<1000;i++){
							try{
								long begin=System.currentTimeMillis();
								Map<String, String> map=new HashMap<>(10000);
								for(int j=0;j<10000;j++){
									map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString());
								}
								Command.mset(map,col);
								ind.incrementAndGet();
								long time=System.currentTimeMillis()-begin;
								System.out.println(col+"\tat:"+i+" cost:"+(time));
								if(time>=1000){
									time=time*3;
								}
								Thread.sleep(time);
							}catch(Throwable e){
								e.printStackTrace();
								try {
									Thread.sleep(300000l);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								continue;
							}
						}
					}
					System.out.println("done~");
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