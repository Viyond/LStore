package com.mydb.client.session;

import org.apache.commons.pool2.impl.GenericObjectPool;
import com.mydb.client.pool.CtxResource;

public class Connections {
	public static GenericObjectPool<CtxResource> pool;
}