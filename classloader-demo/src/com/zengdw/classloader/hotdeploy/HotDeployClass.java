package com.zengdw.classloader.hotdeploy;

/**
 * @description: 需要修改 热加载的类
 * @author: zengd
 * @date: 2020/12/15 16:01
 */
public class HotDeployClass {
    public void test() {
        System.out.println(Thread.currentThread().getContextClassLoader().getName());
        System.out.println(2);
    }
}
