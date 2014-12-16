package com.hcm.zk.client.demo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class MultiWatcher implements Watcher {

	private String connectAddress = null;

	public MultiWatcher(String address) {
		connectAddress = address;
	}

	@Override
	public void process(WatchedEvent event) {
		String outputStr = "";
		if (connectAddress != null) {
			outputStr += "connectIP:" + connectAddress;
		}
		outputStr += ",path:" + event.getPath();
		outputStr += ",state:" + event.getState();
		outputStr += ",type:" + event.getType();

		System.out.println("######## ZK watch : " + outputStr);
	}

}
