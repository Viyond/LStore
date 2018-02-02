package com.mydb.server.store;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.DBOptions;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mydb.common.beans.Configs;

/**
 * 功能描述:Rocksdb 处理类
 * @createTime: 2018年2月1日 下午5:08:19
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年2月1日 下午5:08:19
 * @updateAuthor: l.sl
 * @changesSum:
 */
public class MyStore {
	private final static Logger log=LoggerFactory.getLogger(MyStore.class);
	public static RocksDB db;
	public final static Map<String,ColumnFamilyHandle> columnFamilies=new HashMap<>();
	static{
		final DBOptions options = new DBOptions();
		options.setCreateIfMissing(true);
		options.setCreateMissingColumnFamilies(true);
		try {
			String db_path=Configs.get("dbpath");
			List<ColumnFamilyHandle> listFamilyHandler=new ArrayList<>();
			List<ColumnFamilyDescriptor> listDescriptor=getColumnFamilies(db_path);
			db = RocksDB.open(options,db_path,listDescriptor,listFamilyHandler);
			for(int i=0;i<listDescriptor.size();i++){
				columnFamilies.put(new String(listDescriptor.get(i).columnFamilyName()),listFamilyHandler.get(i));
			}
			log.info("DB inited.Base Path:{},column families:{}",db_path,columnFamilies.keySet());
		} catch (RocksDBException e) {
			e.printStackTrace();
			log.error("",e);
			System.exit(-1);
		}
	}
	
	private static List<ColumnFamilyDescriptor> getColumnFamilies(String db_path){
		File file=new File(db_path);
		File[] opfiles=file.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("OPTIONS-");
			}
		});
		Set<String> columns=new HashSet<>();
		for(File opf : opfiles){
			BufferedReader reader=null;
			try {
				reader=new BufferedReader(new FileReader(opf));
				Object[] lines=reader.lines().filter(e -> e.startsWith("[CFOptions \"")).toArray();
				for(Object o : lines){
					String line=o.toString();
					String col=line.substring(line.indexOf("\"")+1,line.lastIndexOf("\""));
					columns.add(col);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(reader!=null){
						reader.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		List<ColumnFamilyDescriptor> list=new ArrayList<>(columns.size());
		for(String s : columns){
			ColumnFamilyDescriptor desc=new ColumnFamilyDescriptor(s.getBytes());
			list.add(desc);
		}
		return list;
	}
	
	/**
	 * 功能描述：创建ColumnFamily
	 * @author:l.sl
	 * @param columnFamilyName
	 * @throws RocksDBException
	 * @return void
	 * 2018年2月1日 下午5:07:40
	 */
	public static synchronized ColumnFamilyHandle createColumnFamily(String columnFamilyName) throws RocksDBException{
		if(columnFamilies.containsKey(columnFamilyName)){
			return columnFamilies.get(columnFamilyName);
		}
		ColumnFamilyDescriptor desc=new ColumnFamilyDescriptor(columnFamilyName.getBytes());
		ColumnFamilyHandle columnHandler=db.createColumnFamily(desc);
		columnFamilies.put(columnFamilyName, columnHandler);
		return columnHandler;
	}
	
	/**
	 * 功能描述：获取默认columnFamily
	 * @author:l.sl
	 * @return
	 * @return ColumnFamilyHandle
	 * 2018年2月1日 下午5:56:09
	 */
	public static ColumnFamilyHandle getDefaultColumnFamilyHandler(){
		return columnFamilies.get("default");
	}
	
	/**
	 * 功能描述：获取指定columnFamily,如果不存在则会创建
	 * @author:l.sl
	 * @param columnFamilyName
	 * @return
	 * @throws RocksDBException
	 * @return ColumnFamilyHandle
	 * 2018年2月1日 下午5:56:25
	 */
	public static ColumnFamilyHandle getAndCreateColumnFamilyHandler(String columnFamilyName) throws RocksDBException{
		if(!columnFamilies.containsKey(columnFamilyName)){
			return createColumnFamily(columnFamilyName);
		}
		return columnFamilies.get(columnFamilyName);
	}
}