package shutao.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NioClient {
	SocketChannel client;
	Selector selector;

	ByteBuffer receiveBuff = ByteBuffer.allocate(1024);
	ByteBuffer sendBuff = ByteBuffer.allocate(1024);

	InetSocketAddress serverAddress = new InetSocketAddress("localhost", 8000);

	public NioClient() throws IOException {
		/**
		 * start an channel
		 */
		client = SocketChannel.open();
		client.configureBlocking(false);
		client.connect(serverAddress);
		/**
		 * Start a selector
		 */
		selector = Selector.open();

		client.register(selector, SelectionKey.OP_CONNECT);
	}

	public void session() throws IOException {
		if (client.isConnectionPending()) {
			client.finishConnect();
			System.out.println("Type your name in console:");
			client.register(selector, SelectionKey.OP_WRITE);
		}

		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine()) {
			String name = scan.nextLine();
			if ("".equals(name)) {
				continue;
			}
			process(name);
			System.out.println("Type your name in console:");
		}

	}

	private void process(String name) throws IOException {
		boolean unFinish = true;
		while (unFinish) {
			int i = selector.select();
			if (i == 0) {
				return;
			}

			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = keys.iterator();

			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				if (key.isWritable()) { // check if can write data
					sendBuff.clear();
					sendBuff.put(name.getBytes());
					sendBuff.flip();
					client.write(sendBuff);
					client.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) { // check if can read data
					receiveBuff.clear();
					int len = client.read(receiveBuff);
					if (len > 0) {
						String msg = new String(receiveBuff.array(), 0, len);
						System.out.println("Receive message from server: " + msg);
						client.register(selector, SelectionKey.OP_WRITE);
						unFinish = false;
					}
				}

				iterator.remove();
			}
		}

	}

	public static void main(String[] args) throws IOException {
		new NioClient().session();
	}
}
