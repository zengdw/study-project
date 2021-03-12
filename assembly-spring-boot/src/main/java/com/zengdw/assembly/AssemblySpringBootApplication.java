package com.zengdw.assembly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class AssemblySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssemblySpringBootApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
