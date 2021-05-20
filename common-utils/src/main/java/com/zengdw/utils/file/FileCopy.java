package com.zengdw.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @description: 文件复制
 * @author: zengd
 * @date: 2021/05/20 15:06
 */
public class FileCopy {
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
                        fileCopyByChannel(file, directory);
                    }
                }
            }
        } else {
            fileCopyByChannel(sourceFile, targetFile);
        }
    }

    /**
     * 利用文件通道(FileChannel)来实现文件(夹)复制
     *
     * @param s 要复制的文件(夹)
     */
    private static void fileCopyByChannel(File s, File t) {
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
}
