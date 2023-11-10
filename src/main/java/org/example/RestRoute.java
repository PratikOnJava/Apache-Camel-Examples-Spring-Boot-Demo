package org.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

import java.util.Timer;

@Component
public class RestRoute extends RouteBuilder {
    JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Timer.class);

    @Override
    public void configure() throws Exception {
        restConfiguration().host("localhost").port(8080);

        /*from("timer:timer1?period={{timer.period}}").setHeader("id", simple("${random(6,9)}"))
                .to("rest:get:random/{id}").log("${body}"); */
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jsonDataFormat.setObjectMapper(objectMapper);
        from("timer://timer2?period={{timer.period}}").setBody().simple("Current time is ${header.firedTime}")
                .process(new TimerProcessor()).marshal(jsonDataFormat).to("rest:post:time").log("${body}");

        /*from("direct:sayHello")
                .routeId("hello1")
                .transform()
                .method("myBean", "saySomething")
                .to("rest:post:time").log("${body}");*/


    }

}