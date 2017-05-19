package shutao.camel;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:input?move=./done")
		.process(new MyProcessor())
		.bean(new MyTransformer(), "TransformContent")
		.to("file:output");
	}
}
