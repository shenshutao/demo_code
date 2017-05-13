package shutao.cipersuites;

import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class CiperSuitesCheck {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		SSLContext context = SSLContext.getDefault();
		SSLSocketFactory sf = context.getSocketFactory();
		String[] cipherSuites = sf.getSupportedCipherSuites();

		for (String s : cipherSuites) {
			System.out.println(s);
		}
	}
}
