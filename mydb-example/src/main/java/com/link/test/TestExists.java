package com.link.test;

import com.mydb.client.command.Command;

public class TestExists {
	public static void main(String[] args) {
		boolean res=Command.exists("00000007-03ea-4db0-8987-cf27a470d52c");
		System.out.println(res);
	}
}