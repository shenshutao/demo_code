package shutao.io.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;
import java.util.concurrent.Future;

public class AioClient {
	AsynchronousSocketChannel client;

	ByteBuffer sendBuff = ByteBuffer.allocate(1024);

	public AioClient(String host, int port) throws Exception {
		client = AsynchronousSocketChannel.open();
		Future<?> f = client.connect(new InetSocketAddress(host, port));
	}

	public void send(String content) {
		sendBuff.clear();
		sendBuff.put(content.getBytes());
		sendBuff.flip();
		client.write(sendBuff);
	}

	public static void main(String[] args) throws Exception {
		AioClient client = new AioClient("127.0.0.1", 8000);

		System.out.println("Type in Message in console:");
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine()) {
			String msg = scan.nextLine();
			if ("".equals(msg)) {
				continue;
			}
			client.send(msg);
			System.out.println("Type in Message in console:");
		}
	}
}
