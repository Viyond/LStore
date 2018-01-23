package com.mydb.server.store;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyStore {
	//private final static String dbpath="D://rocksdb";
	private final static Logger log=LoggerFactory.getLogger(MyStore.class);
	private final static String dbpath="/root/rocks";
	public static RocksDB db;
	static{
		final String db_path = dbpath;
		final Options options = new Options();
		options.setCreateIfMissing(true);
		options.setCreateMissingColumnFamilies(true);
		try {
			db = RocksDB.open(options,db_path);
			log.info("DB inited.Base Path:{}",dbpath);
		} catch (RocksDBException e) {
			e.printStackTrace();
			log.error("",e);
			System.exit(-1);
		}
	}
}