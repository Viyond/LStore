package com.mydb.client.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mydb.client.nio.IOClient;
import com.mydb.client.session.ServerSessions;
import com.mydb.common.beans.DBException;
import io.netty.channel.ChannelHandlerContext;

/**
 * 功能描述:用NIO来示范context连接池的创建,实现common-pool2的PooledObjectFactory接口
 * @createTime: 2018年1月18日 下午5:18:21
 * @author: lsl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月18日 下午5:18:21
 * @updateAuthor: lsl
 * @changesSum:
 */
public class DBPoolFactory implements PooledObjectFactory<CtxResource>{
	
	private int logtimeout;
	
	public DBPoolFactory(int logtimeout) {
		super();
		this.logtimeout=logtimeout;
	}

	private Logger log=LoggerFactory.getLogger(getClass());
	
	/**
	 * 这个阻塞队列主要是用于阻塞发起登陆命令后需要异步等待服务器返回登录成功后才可用
	 */
	public final static BlockingQueue<ChannelHandlerContext> loginQuee=new ArrayBlockingQueue<>(10);

	/**
	 * 创建连接对象方法
	 */
	@Override
	public PooledObject<CtxResource> makeObject() throws Exception {
		//发起线程连接到服务器，如果登录成功则会在loginQuee中放入ctx对象
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new IOClient().startIO();
				} catch (InterruptedException e) {
					log.error("",e);
				}
			}
		}).start();
		ChannelHandlerContext ctx=loginQuee.poll(logtimeout,TimeUnit.SECONDS);
		if(ctx==null){
			throw new DBException("获取链接失败:"+IOClient.host+":"+IOClient.port);
		}
		log.debug(ctx.channel().id().asShortText()+"\t created");
		CtxResource resource=new CtxResource(ctx);
		return new DefaultPooledObject<CtxResource>(resource);
	}

	/**
	 * 销毁连接对象方法
	 */
	@Override
	public void destroyObject(PooledObject<CtxResource> p) throws Exception {
		p.getObject().release();
	}

	/**
	 * 功能描述：判断资源对象是否有效，有效返回 true，无效返回 false
	 * 什么时候会调用此方法
	 * 1：从资源池中获取资源的时候，参数 testOnBorrow 或者 testOnCreate 中有一个 配置 为 true 时，则调用  factory.validateObject() 方法
	 * 2：将资源返还给资源池的时候，参数 testOnReturn，配置为 true 时，调用此方法
	 * 3：资源回收线程，回收资源的时候，参数 testWhileIdle，配置为 true 时，调用此方法
	 */
	@Override
	public boolean validateObject(PooledObject<CtxResource> p) {
		return p.getObject().getCtx().channel().isActive();
	}

	/**
	 * 功能描述：激活资源对象
	 * 什么时候会调用此方法
	 * 1：从资源池中获取资源的时候
	 * 2：资源回收线程，回收资源的时候，根据配置的 testWhileIdle 参数，判断 是否执行 factory.activateObject()方法，true 执行，false 不执行
	 * @param arg0 
	 */
	@Override
	public void activateObject(PooledObject<CtxResource> p) throws Exception {
	}

	/**
	 * 功能描述：钝化资源对象
	 * 什么时候会调用此方法
	 * 1：将资源返还给资源池时，调用此方法。
	 */
	@Override
	public void passivateObject(PooledObject<CtxResource> p) throws Exception {
		String id=p.getObject().getCtx().channel().id().asShortText();
		ServerSessions.resultCommandMap.remove(id);
		p.getObject().getResultLock().poll();
	}
}
