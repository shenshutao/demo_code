
package shutao.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AioServer {
	AsynchronousServerSocketChannel server;
	ByteBuffer receiveBuff = ByteBuffer.allocate(1024);
	int port = 8000; // default

	public AioServer(int port) throws IOException {
		this.port = port;
		server = AsynchronousServerSocketChannel.open();
		server.bind(new InetSocketAddress("localhost", this.port));
	}

	public void listener() {
		new Thread() {
			public void run() {
				System.out.println("AIO Server start listening on port: " + port);
				server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

					public void completed(AsynchronousSocketChannel client, Void attachment) {
						server.accept(null, this);
						process(client);
					}

					public void failed(Throwable exc, Void attachment) {
						System.out.println("Async IO failed.");
					}

					private void process(AsynchronousSocketChannel client) {
						try {
							receiveBuff.clear();
							int len = client.read(receiveBuff).get();
							receiveBuff.flip();
							String msg = new String(receiveBuff.array(), 0, len);
							System.out.println("Receive Message from Client: " + msg);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				// keep alive
				while (true) {
				}
			}
		}.run();
	}

	public static void main(String[] args) throws IOException {
		new AioServer(8000).listener();
	}
}
