package com.mydb.server;

import com.mydb.common.beans.Configs;
import com.mydb.server.nio.IOServer;
import com.mydb.server.store.MyStore;


/**
 * 功能描述:The Main class
 * @createTime: 2018年1月24日 下午3:19:00
 * @author: l.sl
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 2018年1月24日 下午3:19:00
 * @updateAuthor: l.sl
 * @changesSum:
 */
public class Main {
    public static void main( String[] args ) throws Exception{
    	new Thread(new Runnable() {
			@Override
			public void run() {
				MyStore.init();
			}
		}).start();
    	new IOServer().startIO(Configs.get("bind"),Configs.getInteger("port"));
    }
}
