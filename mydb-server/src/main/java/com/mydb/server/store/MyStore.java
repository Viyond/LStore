package com.mydb.server.store;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mydb.common.beans.Configs;

public class MyStore {
	private final static Logger log=LoggerFactory.getLogger(MyStore.class);
	public static RocksDB db;
	static{
		final Options options = new Options();
		options.setCreateIfMissing(true);
		options.setCreateMissingColumnFamilies(true);
		try {
			String db_path=Configs.get("dbpath");
			db = RocksDB.open(options,db_path);
			log.info("DB inited.Base Path:{}",db_path);
		} catch (RocksDBException e) {
			e.printStackTrace();
			log.error("",e);
			System.exit(-1);
		}
	}
}