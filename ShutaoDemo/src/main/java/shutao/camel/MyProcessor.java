package shutao.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyProcessor implements Processor {

	public void process(Exchange ex) throws Exception {
		String content = ex.getIn().getBody(String.class);
		System.out.println	("Content is: " + content);
	}

}
