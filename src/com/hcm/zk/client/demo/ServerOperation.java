package com.hcm.zk.client.demo;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;

public interface ServerOperation {
	
	void init(String address, String serverName) throws IOException;

	void destroy() throws InterruptedException;

	List<String> getChilds(String path) throws KeeperException, InterruptedException;

	String getData(String path) throws KeeperException, InterruptedException;

	void changeData(String path, String data) throws KeeperException, InterruptedException;

	void delData(String path) throws KeeperException, InterruptedException;

	void apendTempNode(String path, String data) throws KeeperException, InterruptedException;

	void apendPresistentNode(String path, String data) throws KeeperException, InterruptedException;

	void delNode(String path) throws KeeperException, InterruptedException;

	boolean exist(String path) throws KeeperException, InterruptedException;
}
