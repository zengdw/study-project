package com.zengdw.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @description:
 * @author: zengd
 * @date: 2020/12/19 17:05
 */
public class FileUtils {
    /**
     * 文件(夹)复制
     *
     * @param sourceFile 要复制的文件(夹)
     * @param targetFile 复制到的位置
     */
    public static void copyFile(File sourceFile, File targetFile) {
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        if (sourceFile.isDirectory()) {
            File[] files = sourceFile.listFiles();
            if (null != files) {
                for (File file : files) {
                    File directory = new File(targetFile.getPath() + File.separator + file.getName());
                    if (file.isDirectory()) {
                        copyFile(file, directory);
                    } else {
                        fileChannelCopy(file, directory);
                    }
                }
            }
        } else {
            fileChannelCopy(sourceFile, targetFile);
        }
    }

    /**
     * 利用文件通道(FileChannel)来实现文件(夹)复制
     *
     * @param s 要复制的文件(夹)
     * @param t
     */
    private static void fileChannelCopy(File s, File t) {
        try (FileInputStream fi = new FileInputStream(s);
             FileOutputStream fo = new FileOutputStream(t);
             //得到对应的文件通道
             FileChannel in = fi.getChannel();
             //得到对应的文件通道
             FileChannel out = fo.getChannel()) {
            //连接两个通道，并且从in通道读取，然后写入out通道
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 需要注意的是当删除某一目录时，必须保证该目录下没有其他文件才能正确删除，否则将删除失败。
     */
    public static void deleteFolder(File folder) {
        if (!folder.exists()) {
            return;
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    //递归直到目录下没有文件
                    deleteFolder(file);
                } else {
                    //删除
                    file.delete();
                }
            }
        }
        //删除
        folder.delete();
    }
}
