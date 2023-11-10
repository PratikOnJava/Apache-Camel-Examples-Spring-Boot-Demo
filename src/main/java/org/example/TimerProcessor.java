package org.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Timer;


public class TimerProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        String id = exchange.getIn().getBody(String.class);
        Timer t = new Timer(id);
        exchange.getIn().setBody(t);
    }
}