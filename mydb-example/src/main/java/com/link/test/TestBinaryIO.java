package com.link.test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import com.mydb.client.command.Command;


public class TestBinaryIO {
	public static void main(String[] args) throws IOException {
		Command.get("a");
		write();
//		while(true){
//			long begin=System.currentTimeMillis();
//			write();
//			System.out.println("write cost:"+(System.currentTimeMillis()-begin));
//			begin=System.currentTimeMillis();
//			read();
//			System.out.println("read cost:"+(System.currentTimeMillis()-begin));
//		}
		System.exit(0);
	}
	
	public static void write() throws IOException{
		InputStream io=new FileInputStream("D:/Images/gamersky_05origin_09_201711181746C92.jpg");
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		byte[] b=new byte[2048];
		while(io.read(b)!=-1){
			os.write(b);
		}
		String data=Base64.getEncoder().encodeToString(os.toByteArray());
		Command.set("test", data);
		os.close();
		io.close();
	}
	
	public static void read() throws IOException{
		String data=Command.get("test");
//		FileOutputStream fio=new FileOutputStream("D:/imgs.jpg");
//		fio.write(Base64.getDecoder().decode(data));
//		fio.close();
	}
}