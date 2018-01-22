package com.mydb.server.store;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

public class MyStore {
	private final static String dbpath="D://rocksdb";
	//private final static String dbpath="/root/rocks";
	public static RocksDB db;
	static{
		final String db_path = dbpath;
		final Options options = new Options();
		options.setCreateIfMissing(true);
		options.setCreateMissingColumnFamilies(true);
		try {
			db = RocksDB.open(options,db_path);
		} catch (RocksDBException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}