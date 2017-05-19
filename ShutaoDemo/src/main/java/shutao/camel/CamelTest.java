package shutao.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelTest {

	public static void main(String[] args) {
		MyRouteBuilder myRouteBuilder = new MyRouteBuilder();
		CamelContext ctx = new DefaultCamelContext();

		try {
			ctx.addRoutes(myRouteBuilder);
			ctx.start();
			Thread.sleep(5 * 60 * 1000);
			ctx.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
