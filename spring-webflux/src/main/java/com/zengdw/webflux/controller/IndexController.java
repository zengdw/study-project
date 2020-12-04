package com.zengdw.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * @description:
 * @author: zengd
 * @date: 2020/11/20 17:04
 */
@RestController
public class IndexController {
    @GetMapping(value = "/index", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<String> index() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("C:\\Users\\zengd\\Desktop\\logs.txt");
        BufferedInputStream stream = new BufferedInputStream(inputStream);
        return Flux.interval(Duration.ofSeconds(3)).map(t -> {
            try {
                byte[] bytes = new byte[1024];
                int l;
                StringBuilder builder = new StringBuilder();
                do {
                    l = stream.read(bytes);
                    if (l != -1) {
                        builder.append(new String(bytes, 0, l, StandardCharsets.UTF_8));
                    }
                } while (l != -1);
                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        });
    }
}
