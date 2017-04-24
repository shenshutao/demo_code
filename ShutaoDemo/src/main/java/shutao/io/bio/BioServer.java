package shutao.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {
	ServerSocket server;

	public BioServer(int port) throws IOException {
			server = new ServerSocket(port);
			System.out.println("BIO server start successful.");
	}

	public static void main(String[] args) throws IOException {
		new BioServer(8000).listener();
	}

	public void listener() throws IOException {
		while (true) {
			Socket client = server.accept();
			InputStream is = client.getInputStream();
			byte[] buff = new byte[1024];
			int len = is.read(buff);
			if (len > 0) {
				String msg = new String(buff, 0, len);
				System.out.println("Message from client: " + msg);
			}
		}
	}

}
