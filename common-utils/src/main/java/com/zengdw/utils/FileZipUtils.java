package com.zengdw.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @description: zip文件的压缩与解压
 * @author: zengd
 * @date: 2020/12/19 17:29
 */
public class FileZipUtils {

    /**
     * 压缩整个文件夹中的所有文件，生成指定名称的zip压缩包
     *
     * @param filepath 文件所在目录
     * @param dirFlag  zip文件中第一层是否包含一级目录，true包含；false没有
     */
    public static void zipMultiFile(String filepath, OutputStream outputStream, boolean dirFlag) {
        // 要被压缩的文件夹
        File file = new File(filepath);
        try (ZipOutputStream zipOut = new ZipOutputStream(outputStream)) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if(null == files){
                    return;
                }
                for (File fileSec : files) {
                    if (dirFlag) {
                        recursionZip(zipOut, fileSec, file.getName() + File.separator);
                    } else {
                        recursionZip(zipOut, fileSec, "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void recursionZip(ZipOutputStream zipOut, File file, String baseDir) throws Exception {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if(null == files){
                return;
            }
            for (File fileSec : files) {
                recursionZip(zipOut, fileSec, baseDir + file.getName() + File.separator);
            }
        } else {
            try(InputStream input = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(input, 1024 * 10)) {
                byte[] buf = new byte[1024];
                zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
                int len;
                while ((len = bis.read(buf)) != -1) {
                    zipOut.write(buf, 0, len);
                }
            }
        }
    }

    /**
     * zip解压
     * @param inputFile 待解压文件名
     * @param destDirPath  解压路径
     */
    public static void zipUncompress(String inputFile, String destDirPath) throws Exception {
        //获取当前压缩文件
        File srcFile = new File(inputFile);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new Exception(srcFile.getPath() + "所指文件不存在");
        }
        //开始解压
        //构建解压输入流
        ZipInputStream zIn = new ZipInputStream(new FileInputStream(srcFile), StandardCharsets.UTF_8);
        ZipEntry entry;
        File file;
        while ((entry = zIn.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                file = new File(destDirPath, entry.getName());
                if (!file.exists()) {
                    //创建此文件的上级目录
                    new File(file.getParent()).mkdirs();
                }
                OutputStream out = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(out);
                int len = -1;
                byte[] buf = new byte[1024];
                while ((len = zIn.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                // 关流顺序，先打开的后关闭
                bos.close();
                out.close();
            }
        }
        zIn.close();
    }
}
