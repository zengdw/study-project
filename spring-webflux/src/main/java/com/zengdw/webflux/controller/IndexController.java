package com.zengdw.webflux.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: zengd
 * @date: 2020/11/20 17:04
 */
@RestController
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private final int count_down_sec=3*60*60;

    @GetMapping("/log")
    public void log() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("adfdfadfda");
            }
        }).start();
    }

    @GetMapping(value = "/index", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<String> index() throws FileNotFoundException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("E:\\workspace\\study-project\\log.log", "r");
        Map<String, Long> map = new HashMap<>();
        map.put("pointer", 0L);
        return Flux.interval(Duration.ofSeconds(1)).map(t -> readLogs(randomAccessFile, map));
    }

    private String readLogs(RandomAccessFile randomAccessFile, Map<String, Long> map) {
        try {
            StringBuilder builder = new StringBuilder();
            String l;
            randomAccessFile.seek(map.get("pointer"));
            do {
                l = randomAccessFile.readLine();
                if(!StringUtils.isEmpty(l)) {
                    builder.append(l).append("\r\n");
                    map.put("pointer", randomAccessFile.getFilePointer());
                }
            } while (!StringUtils.isEmpty(l));
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping(value="/countDown",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Object>> countDown() throws FileNotFoundException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("E:\\workspace\\study-project\\log.log", "r");
        Map<String, Long> map = new HashMap<>();
        map.put("pointer", 0L);
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, readLogs(randomAccessFile, map)))
                .map(data -> ServerSentEvent.builder()
                        .event("countDown")
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .retry(Duration.ofSeconds(3))
                        .build());
    }
}
