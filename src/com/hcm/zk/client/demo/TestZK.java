package com.hcm.zk.client.demo;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestZK {
	
	private ServerConnector serverConnector = null;
	
	@Before
	public void before() {
		MultiWatcher mWatch = new MultiWatcher("127.0.0.1:2181");
		try {
			ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 30000, mWatch);
			serverConnector = new ServerConnector(zk);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void after() {
		try {
			serverConnector.destroy();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreate() {
		try {
			serverConnector.apendPresistentNode("/zkCli", "data001");
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetData() {
		String result = null;
		try {
			result = serverConnector.getData("/zkCli");
			System.out.println(result);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
