package com.mydb.client.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mydb.common.beans.Configs;
import com.mydb.common.nio.IOMsgOuterClass.IOMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class IOClient {
	private final static Logger log=LoggerFactory.getLogger(IOClient.class);
	public static Bootstrap b=null;
	public static EventLoopGroup group=null;
	public final static String host=Configs.get("bind");
	public final static int port=Configs.getInteger("port");
	static{
		EventLoopGroup group = new NioEventLoopGroup();
        try {
            b = new Bootstrap();
            b.group(group)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new ProtobufVarint32FrameDecoder());
                    p.addLast(new ProtobufDecoder(IOMsg.getDefaultInstance()));
                    p.addLast(new ProtobufVarint32LengthFieldPrepender());
                    p.addLast(new ProtobufEncoder());
                    p.addLast(new IOClientHandler());
                }
            });
        }catch(Throwable e){
        	log.error("",e);
        }
	}
        
	public void startIO() throws InterruptedException{
		ChannelFuture f=b.connect(host, port).sync();;
        log.info("client connect to host:{}, port:{}", host, port);
        f.channel().closeFuture().sync();
	}
}