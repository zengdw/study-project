package com.zengdw.utils.file;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * @description: 文件操作工具
 * @author: zengd
 * @date: 2021/05/20 15:04
 */
public class FileUtils {
    /**
     * @param contents 二进制数据
     * @param filePath 文件存放目录，包括文件名及其后缀，如D:\file\bike.jpg
     * @description: 把二进制数据转成指定后缀名的文件，例如PDF，PNG等
     */
    public static void byteToFile(byte[] contents, String filePath) throws Exception {
        if(StringUtils.isBlank(filePath)){
            throw new Exception("文件存放目录为空");
        }
        File file = new File(filePath);
        // 获取文件的父路径字符串
        File path = file.getParentFile();
        if (!path.exists()) {
            path.mkdirs();
        }
        try (ByteArrayInputStream byteInputStream = new ByteArrayInputStream(contents);
             BufferedInputStream bis = new BufferedInputStream(byteInputStream);
             FileOutputStream fos = new FileOutputStream(file);
             // 实例化OutputString 对象
             BufferedOutputStream output = new BufferedOutputStream(fos)
        ) {
            byte[] buffer = new byte[1024];
            int l;
            while ((l = bis.read(buffer)) != -1) {
                output.write(buffer, 0, l);
            }
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

    /**
     * 把File String InputStream转换成InputStream
     */
    public static InputStream convertToInputStream(Object in) throws Exception {
        InputStream inputStream;
        if (in instanceof InputStream) {
            inputStream = (InputStream) in;
        } else if (in instanceof File) {
            // 判断源文件是否存在
            File srcFile = (File) in;
            fileExists(srcFile);
            inputStream = new FileInputStream(srcFile);
        } else if (in instanceof String) {
            // 判断源文件是否存在
            File srcFile = new File(in.toString());
            fileExists(srcFile);
            inputStream = new FileInputStream(srcFile);
        } else {
            throw new Exception("输入格式不对");
        }
        return inputStream;
    }

    /**
     * 判断文件是否存在
     */
    public static void fileExists(File srcFile) throws Exception {
        if (!srcFile.exists()) {
            throw new Exception(srcFile.getPath() + "所指文件不存在");
        }
    }
}
