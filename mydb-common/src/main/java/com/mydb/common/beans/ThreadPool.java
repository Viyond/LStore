package com.mydb.common.beans;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool {
	private final ExecutorService exe=Executors.newFixedThreadPool(500);
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