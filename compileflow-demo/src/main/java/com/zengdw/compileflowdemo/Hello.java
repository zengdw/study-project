package com.zengdw.compileflowdemo;

import java.util.StringJoiner;

/**
 * @description:
 * @author: zengd
 * @date: 2021/08/10 15:49
 */
public class Hello {
    public String hello(String name) {
        String s = new StringJoiner("", "Hello ", "").add(name).toString();
        System.out.println(s);
        return s;
    }
}
