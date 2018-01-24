package com.mydb.client.dispatch;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mydb.common.beans.CMDMsg;
import com.mydb.common.beans.Configs;
import com.mydb.common.nio.IOMsgOuterClass.IOMsg;
import io.netty.channel.ChannelHandlerContext;

public class CMDDispatcher {
	private static Logger log=LoggerFactory.getLogger(CMDDispatcher.class);
	private final static BlockingQueue<CMDMsg> quee=new LinkedBlockingDeque<>(Configs.getInteger("msg.queue.size"));
	private final static ExecutorService cmdExe=Executors.newFixedThreadPool(Configs.getInteger("msg.queue.thread"));
	private static boolean on=true;
	
	static{
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(on){
					try {
						CMDMsg cmdMsg=quee.take();
						//log.info("got cmd:{}",cmdMsg.getMsg());
						cmdExe.submit(new ClientCmdExecutor(cmdMsg));
					} catch (InterruptedException e) {
						log.error("",e);
					}
				}
			}
		}).start();
	}
	
	public static void put(int cmd,IOMsg msg,ChannelHandlerContext ctx) throws InterruptedException{
		CMDMsg cmdMsg=new CMDMsg(cmd, ctx, msg);
		quee.put(cmdMsg);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		on=false;
	}
}