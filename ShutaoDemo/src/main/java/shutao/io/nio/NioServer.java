package shutao.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NioServer {
	int port;
	ServerSocketChannel server;
	Selector selector;
	ByteBuffer receiveBuff = ByteBuffer.allocate(1024);
	ByteBuffer sendBuff = ByteBuffer.allocate(1024);

	Map<SelectionKey, String> sessionMsg = new HashMap<SelectionKey, String>();

	public NioServer(int port) throws IOException {
		/**
		 * start an channel
		 */
		this.port = port;
		server = ServerSocketChannel.open();
		server.socket().bind(new InetSocketAddress(this.port));
		// Manually set the server to Non-blocking (Mandatory)
		server.configureBlocking(false);

		/**
		 * Start a selector
		 */
		selector = Selector.open();
		server.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("NIO server start successful on port: " + port);

		listener();
	}

	public void listener() throws IOException {
		/**
		 * Polling
		 */
		while (true) {
			// check if there is client in queue.
			int i = selector.select();
			if (i == 0) {
				continue;
			}

			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = keys.iterator();
			while (iterator.hasNext()) {
				System.out.println(keys.size());
				// process one by one
				process(iterator.next());

				iterator.remove();
			}
		}
	}

	private void process(SelectionKey key) throws IOException {
		if (key.isAcceptable()) { // check if client is connect to boss

			SocketChannel client = server.accept();
			client.configureBlocking(false);
			client.register(selector, SelectionKey.OP_READ);
		} else if (key.isReadable()) { // check if can read data
			SocketChannel client = (SocketChannel) key.channel();
			receiveBuff.clear();
			int len = client.read(receiveBuff);
			if (len > 0) {
				String msg = new String(receiveBuff.array(), 0, len);
				System.out.println("Receive message from client: " + msg);
				sessionMsg.put(key, msg);
			}
			client.register(selector, SelectionKey.OP_WRITE);
		} else if (key.isWritable()) { // check if can write data
			SocketChannel client = (SocketChannel) key.channel();
			if (sessionMsg.containsKey(key)) {
				sendBuff.clear();
				sendBuff.put(new String(sessionMsg.get(key) + ", Done.").getBytes());
				sendBuff.flip();
				// sendBuff.flip();
				client.write(sendBuff);
			}
			client.register(selector, SelectionKey.OP_READ);
		}
	}

	public static void main(String[] args) throws IOException {
		new NioServer(8000);
	}

}
