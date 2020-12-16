package com.zengdw.classloader.hotdeploy;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 自定义类加载器
 * @author: zengd
 * @date: 2020/12/15 15:51
 */
public class MyClassLoader extends ClassLoader {
    private final String classPath;
    private final static Map<String, Class<?>> classMap;

    static {
        classMap = new ConcurrentHashMap<>();
    }

    public MyClassLoader(String classPath) {
        super(ClassLoader.getSystemClassLoader());
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) {
        final byte[] bytes = loadClassData(name);
        if(null == bytes) {
            return null;
        }
        final Class<?> aClass = defineClass(null, bytes, 0, bytes.length);
        classMap.put(name, aClass);
        return aClass;
    }

    public Class<?> findLoadClass(String name) throws ClassNotFoundException {
        if(!checkName(name)){
            return null;
        }
        return classMap.get(name) != null ? classMap.get(name) : Thread.currentThread().getContextClassLoader().loadClass(name);
    }

    private byte[] loadClassData(String className) {
        className = className.replaceAll("\\.", "/") + ".class";
        try (final FileInputStream inputStream = new FileInputStream(new File(classPath, className))) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean checkName(String name) {
        if ((name == null) || (name.length() == 0)) {
            return true;
        }
        return (name.indexOf('/') == -1) && (name.charAt(0) != '[');
    }
}
