package shutao.io.bio;

import java.io.OutputStream;
import java.net.Socket;

public class BioClient {

	public static void main(String[] args) throws Exception {
		Socket client = new Socket("localhost", 8000);

		OutputStream os = client.getOutputStream();

		os.write("Client".getBytes());
		os.close();
		client.close();
	}
}
