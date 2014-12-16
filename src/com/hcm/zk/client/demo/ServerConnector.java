package com.hcm.zk.client.demo;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ServerConnector implements ServerOperation {
	// 创建一个Zookeeper实例，第一个参数为目标服务器地址和端口，第二个参数为Session超时时间，第三个为节点变化时的回调方法
	private ZooKeeper zk = null;

	public ServerConnector() {
	}
	
	public ServerConnector(ZooKeeper zk) {
		if (zk != null) {
			this.zk = zk;
		}
	}
	
	public ServerConnector(String address, String serverName) throws IOException {
		if (zk == null) {
			init(address, serverName);
		}
	}
	
	public ServerConnector(String address, int sessionTimeout, Watcher watcher) throws IOException {
		if (zk == null) {
			zk = new ZooKeeper(address, sessionTimeout, watcher);
		}
	}
	
	public void init(String address, String serverName) throws IOException {
		zk = new ZooKeeper(address, 500000, new MultiWatcher(serverName));
	}

	public void destroy() throws InterruptedException {
		if (zk != null) {
			zk.close();
		}
	}

	public List<String> getChilds(String path) throws KeeperException, InterruptedException {
		if (zk != null) {
			return zk.getChildren(path, true);
		}
		return null;
	}

	public String getData(String path) throws KeeperException, InterruptedException {
		if (zk != null) {
			// 取得/root/childone节点下的数据,返回byte[]
			byte[] b = zk.getData(path, true, null);
			return new String(b);
		}
		return null;
	}

	public void changeData(String path, String data) throws KeeperException, InterruptedException {
		if (zk != null) {
			// 修改节点/root/childone下的数据，第三个参数为版本，如果是-1，那会无视被修改的数据版本，直接改掉
			zk.setData(path, data.getBytes(), -1);
		}
	}

	public void delData(String path) throws InterruptedException, KeeperException {
		if (zk != null) {
			// 删除/root/childone这个节点，第二个参数为版本，－1的话直接删除，无视版本
			zk.delete(path, -1);
		}
	}

	public void delNode(String path) throws InterruptedException, KeeperException {
		if (zk != null) {
			zk.delete(path, -1);
		}
	}

	public boolean exist(String path) throws KeeperException, InterruptedException {
		if (zk != null) {
			return zk.exists(path, true) != null;
		}
		return false;
	}

	public void apendTempNode(String path, String data) throws KeeperException, InterruptedException {
		if (zk != null) {
			// 创建一个节点root，数据是mydata,不进行ACL权限控制，节点为永久性的(即客户端shutdown了也不会消失)
			/*
			 * 创建一个给定的目录节点 path, 并给它设置数据， CreateMode 标识有四种形式的目录节点,分别是
			 * PERSISTENT：持久化目录节点，这个目录节点存储的数据不会丢失；
			 * PERSISTENT_SEQUENTIAL：顺序自动编号的目录节点，这种目录节点会根据当前已近存在的节点数自动加
			 * 1，然后返回给客户端已经成功创建的目录节点名； EPHEMERAL：临时目录节点，一旦创建这个节点的客户端与服务器端口也就是
			 * session 超时，这种节点会被自动删除； EPHEMERAL_SEQUENTIAL：临时自动编号节点
			 */
			zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		}
	}

	public void apendPresistentNode(String path, String data) throws KeeperException, InterruptedException {
		if (zk != null) {
			// 创建一个节点root，数据是mydata,不进行ACL权限控制，节点为永久性的(即客户端shutdown了也不会消失)
			/*
			 * 创建一个给定的目录节点 path, 并给它设置数据， CreateMode 标识有四种形式的目录节点,分别是
			 * PERSISTENT：持久化目录节点，这个目录节点存储的数据不会丢失；
			 * PERSISTENT_SEQUENTIAL：顺序自动编号的目录节点，这种目录节点会根据当前已近存在的节点数自动加
			 * 1，然后返回给客户端已经成功创建的目录节点名； EPHEMERAL：临时目录节点，一旦创建这个节点的客户端与服务器端口也就是
			 * session 超时，这种节点会被自动删除； EPHEMERAL_SEQUENTIAL：临时自动编号节点
			 */
			zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
	}
}
