package com.zengdw.utils.file;

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
     * @param file    要压缩的文件
     * @param outFile 输出文件路径
     * @param dirFlag zip文件中第一层是否包含一级目录，true包含；false没有
     */
    public static void zipFile(File file, String outFile, boolean dirFlag) throws Exception {
        try (FileOutputStream outputStream = new FileOutputStream(outFile);
             ZipOutputStream zipOut = new ZipOutputStream(outputStream)) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (null == files) {
                    return;
                }
                for (File fileSec : files) {
                    if (dirFlag) {
                        doZip(zipOut, fileSec, file.getName() + File.separator);
                    } else {
                        doZip(zipOut, fileSec, "");
                    }
                }
            }
        }
    }

    /**
     * zip解压
     *
     * @param in          待解压文件或文件流或文件全路径
     * @param destDirPath 输出路径
     */
    public static void unZipFile(Object in, String destDirPath) throws Exception {
        InputStream inputStream = FileUtils.convertToInputStream(in);
        //开始解压
        //构建解压输入流
        ZipInputStream zIn = new ZipInputStream(inputStream, StandardCharsets.UTF_8);
        ZipEntry entry;
        while ((entry = zIn.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                File file = new File(destDirPath, entry.getName());
                if (!file.exists()) {
                    //创建此文件的上级目录
                    new File(file.getParent()).mkdirs();
                }
                try (OutputStream out = new FileOutputStream(file);
                     BufferedOutputStream bos = new BufferedOutputStream(out)) {
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = zIn.read(buf)) != -1) {
                        bos.write(buf, 0, len);
                    }
                }
            }
        }
        zIn.close();
    }

    private static void doZip(ZipOutputStream zipOut, File file, String baseDir) throws Exception {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null == files) {
                return;
            }
            for (File fileSec : files) {
                doZip(zipOut, fileSec, baseDir + file.getName() + File.separator);
            }
        } else {
            try (InputStream input = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(input, 1024 * 10)) {
                byte[] buf = new byte[1024];
                zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
                int len;
                while ((len = bis.read(buf)) != -1) {
                    zipOut.write(buf, 0, len);
                }
            }
        }
    }
}
