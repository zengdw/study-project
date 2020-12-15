package com.zengdw.classloader.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: zengd
 * @date: 2020/12/14 10:48
 */
public class FileClassloader extends ClassLoader {
    private final String FILE_LOCATION;

    public FileClassloader(String fileLocation) {
        this.FILE_LOCATION = fileLocation;
    }

    @Override
    public synchronized Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes;
        try {
            classBytes = getClassBytes(name);
        } catch (IOException e) {
            throw new ClassNotFoundException("class not fount", e);
        }

        return defineClass("com.zengdw.classloader.Test", classBytes, 0, classBytes.length);
    }

    private byte[] getClassBytes(String name) throws IOException {
        File file = new File(FILE_LOCATION, name);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        }
    }

    public static void main(String[] args) throws Exception {
        final FileClassloader classloader = new FileClassloader("E:\\workspace\\study-project\\classloader-demo\\src\\com\\zengdw\\classloader\\Test");
        final Class<?> aClass = classloader.loadClass("Test.class");
        System.out.println(aClass.getClassLoader());
        final Method run = aClass.getMethod("run");
        final Object o = run.invoke(aClass.getDeclaredConstructor().newInstance());
    }
}
