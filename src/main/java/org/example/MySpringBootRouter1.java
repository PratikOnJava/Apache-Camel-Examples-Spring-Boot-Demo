package org.example;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class MySpringBootRouter1 extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:sayHello")
                .routeId("hello")
                .transform()
                .method("myBean", "saySomething")
                .filter(simple("${body} contains 'foo'"))
                .to("log:foo")
                .end()
                .to("stream:out");
    }
}