package com.mydb.common.beans;

import java.util.BitSet;

public interface Consts {
	
	public interface CMD{
		/**
		 * 1-999 SYSTEM
		 */
		public int UNDONE=0,DONE=1,RUN=2;
		public int TO_AUTH=10;
		public int AUTH=11;
		public int AUTH_SUCCESS=12;
		public int AUTH_FAIL=13;
		public int AUTH_DELAY=14;
		/**
		 * 1000-9999 APPLICATION
		 */
		public int SET=1000,GET=1001,MSET=1002,MGET=1003,DEL=1004,SCAN=1005,DELRANGE=1006,INFO=1007,CFS=1008,DROPCF=1009,EXISTS=1010;
		public BitSet CMDS=new BitSet(){{
			set(UNDONE);
			set(DONE);
			set(RUN);
			set(TO_AUTH);
			set(AUTH);
			set(AUTH_SUCCESS);
			set(AUTH_FAIL);
			set(AUTH_DELAY);
			
			set(SET);
			set(GET);
			set(MSET);
			set(MGET);
			set(DEL);
			set(SCAN);
			set(DELRANGE);
			set(INFO);
			set(CFS);
			set(DROPCF);
			set(EXISTS);
		}};
	}
	
	public interface TYPE{
		public int SYS=1;
		public int OP=2;
	}
	
	public interface STATUS{
		public int OK=1;
		public int NOTOK=2;
		public int EXCEPION=3;
	}
}