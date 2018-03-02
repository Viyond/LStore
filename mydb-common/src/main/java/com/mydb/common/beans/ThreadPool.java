package com.mydb.common.beans;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	private final ExecutorService exe=Executors.newFixedThreadPool(Configs.getInteger("app.thread.size"));
	private static ThreadPool pool;
	
	public static ThreadPool getInstance(){
		if(pool==null){
			pool=new ThreadPool();
		}
		return pool;
	}
	
	public void submit(Runnable run){
		exe.submit(run);
	}
}