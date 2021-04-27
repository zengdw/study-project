package com.zengdw.limit.counter;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zengd
 * @version 1.0
 * @date Created in 2021/4/26 16:57
 * @description 计数器限流
 */
public class Counter {
    private static final Map<String, CounterProperty> PROPERTY_MAP = new HashMap<>();

    public static boolean limit(String url, int max, int unitTime) {
        CounterProperty property = PROPERTY_MAP.get(url);
        long now = System.currentTimeMillis();
        if (null == property) {
            property = new CounterProperty(max, unitTime);
            PROPERTY_MAP.put(url, property);
            property.setStartTime(now);
            return true;
        } else {
            System.out.println(property.getQ());
            long startTime = property.getStartTime();
            if (startTime + unitTime > now) {
                return property.inc() <= max;
            } else {
                property.setQ(0);
                PROPERTY_MAP.remove(url);
                return true;
            }
        }
    }

    @Data
    static class CounterProperty {
        // 单位时间内最大访问量
        private int max;
        // 单位时间(s)
        private int unitTime;
        // 第一次访问时间
        private long startTime;
        // 访问量
        private int q = 0;

        public CounterProperty(int max, int unitTime) {
            this.max = max;
            this.unitTime = unitTime;
        }

        public int inc() {
            this.q += 1;
            return this.q;
        }
    }

}
