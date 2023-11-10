package org.example;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Timer;

@RestController
public class MyRestController {
    @Autowired
    private ProducerTemplate template;

    @RequestMapping("/hello")
    public String sayHello(@RequestParam(defaultValue = "frank") String name) {
        return template.requestBody("direct:sayHello", name).toString();
    }

    @PostMapping("/time")
    public String getTime(@RequestBody Timer data) {
        return "HTTP POST Got " + data.toString();
    }

    /*@PostMapping("/time")
    public String getTime() {
        return "HTTP POST Request Received " ;
    }*/

    @GetMapping("/random/{id}")
    public String getTime(@PathVariable("id")  Integer id) {
        return "HTTP GET Got " + randomString(10);
    }

    private String randomString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));

        return sb.toString();
    }
}