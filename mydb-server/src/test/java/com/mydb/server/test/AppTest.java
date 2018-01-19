package com.mydb.server.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class AppTest {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket=new Socket("192.168.1.123", 8866);
		System.out.println(socket.isConnected());
		socket.setTcpNoDelay(true);
		InputStream is=socket.getInputStream();
		while(true){
			byte[] b=new byte[8];
			System.out.println(is.read(b));
			System.out.println(b);
			System.out.println(new String(b));
		}
	}
}