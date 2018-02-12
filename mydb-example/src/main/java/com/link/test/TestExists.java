package com.link.test;

import com.mydb.client.command.Command;

public class TestExists {
	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<1;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(;;){
						try {
							System.out.println(Command.exists("00000007-03ea-4db0-8987-cf27a470d52c"));
							Thread.sleep(200);
						} catch (Throwable e) {
							System.out.println("err");
						}
					}
				}
			}).start();
		}
	}
}