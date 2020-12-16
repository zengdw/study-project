package com.zengdw.classloader.hotdeploy;

/**
 * @description:
 * @author: zengd
 * @date: 2020/12/15 16:18
 */
public class HotDeployTest {
    public static void main(String[] args) {
        new Thread(() -> {
            final String path = "C:\\Users\\zengd\\Desktop\\hotTest\\";
            while (true) {
                try {
                    final HotDeployManager deployManager = new HotDeployManager(path);
                    deployManager.loadClass(null);
                    Thread.sleep(1000);

                    final Class<?> aClass = deployManager.getMyClassLoader().findLoadClass("P");
                    aClass.getMethod("test").invoke(aClass.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
