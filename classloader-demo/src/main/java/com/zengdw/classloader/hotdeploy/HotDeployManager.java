package com.zengdw.classloader.hotdeploy;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: zengd
 * @date: 2020/12/15 16:02
 */
public class HotDeployManager {
    private final MyClassLoader myClassLoader;
    private final Map<String, Long> hotDeplyClasses = new ConcurrentHashMap<>();
    private final String hotDeployClasspath;

    public HotDeployManager(String hotDeployClasspath) {
        this.myClassLoader = new MyClassLoader(hotDeployClasspath);
        this.hotDeployClasspath = hotDeployClasspath;
    }

    public void loadClass(String className) throws ClassNotFoundException {
        if (null == className || "".equals(className)) {
            listFile(hotDeployClasspath);
        } else {
            doLoadClass(className.replaceAll("\\.", "/") + ".class", className);
        }
    }

    private void listFile(String path) throws ClassNotFoundException {
        final File file = new File(path);
        final File[] listFiles = file.listFiles();
        if (null == listFiles || listFiles.length == 0) {
            return;
        }
        for (File f : listFiles) {
            if (f.isDirectory()) {
                listFile(f.getAbsolutePath());
            } else {
                final String packagePath = path.substring(hotDeployClasspath.length()).replaceAll("\\\\", ".");
                final String fileName = f.getName();
                String className = packagePath + "." + fileName.substring(0, fileName.indexOf("."));
                className = className.replace("^\\.", "");
                doLoadClass(fileName, className);
            }
        }
    }

    private void doLoadClass(String fileName, String className) throws ClassNotFoundException {
        final File loadFile = new File(hotDeployClasspath, fileName);
        //  文件最后修改时间
        final long lastModified = loadFile.lastModified();
        final Long modified = hotDeplyClasses.get(className);
        if (modified != null && lastModified == modified) {
            return;
        }
        myClassLoader.loadClass(className);
        // 加载完成把修改时间缓存
        hotDeplyClasses.put(className, lastModified);
    }

    public MyClassLoader getMyClassLoader() {
        return myClassLoader;
    }
}
