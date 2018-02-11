package com.mydb.client.nio;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;


/**
 * 功能描述:connection listener to try reconnect
 * @createTime: 2018年2月8日 上午10:50:33
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年2月8日 上午10:50:33
 * @updateAuthor: l.sl
 * @changesSum:
 */
public class IOConnectionListener implements ChannelFutureListener{
	private Logger log=LoggerFactory.getLogger(getClass()); 
	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		if (!future.isSuccess()) {
			log.error("connecting fail,now reconnect.");
			final EventLoop loop = future.channel().eventLoop();  
			loop.schedule(new Runnable() {  
				@Override 
				public void run() {  
					try {
						new IOClient().startIO();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}  
			}, 3L, TimeUnit.SECONDS);  
		}
	}
}