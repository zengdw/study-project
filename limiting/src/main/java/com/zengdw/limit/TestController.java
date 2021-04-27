package com.zengdw.limit;

import com.zengdw.limit.counter.Counter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengd
 * @version 1.0
 * @date Created in 2021/4/26 17:35
 * @description
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        boolean limit = Counter.limit("/test", 100, 1000 * 60);
        return limit ? "test" : "ACCESS_DENIED";
    }
}
