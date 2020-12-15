package com.zengdw.classloader.hotdeploy;

/**
 * @description:
 * @author: zengd
 * @date: 2020/12/15 16:18
 */
public class HotDeployTest {
    public static void main(String[] args) {
        new Thread(() -> {
            final String path = HotDeployTest.class.getResource("/").getPath();
            final HotDeployManager deployManager = new HotDeployManager(path+"com");
            while (true) {
                try {
                    deployManager.loadClass(null);
                    Thread.sleep(1000);

                    new HotDeployClass().test();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
