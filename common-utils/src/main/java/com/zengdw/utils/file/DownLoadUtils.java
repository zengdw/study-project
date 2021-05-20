package com.zengdw.utils.file;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description: 下载文件
 * @author: zengd
 * @date: 2020/12/19 15:39
 */
public class DownLoadUtils {
    /**
     * 下载文件
     *
     * @param file 文件
     */
    public static void downLoadFile(File file, HttpServletResponse response) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件不存在");
        }
        // 配置文件下载
        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        String fileName = file.getName();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        try (FileInputStream fileInputStream = new FileInputStream(file);
             BufferedInputStream inputStream = new BufferedInputStream(fileInputStream);
             ServletOutputStream outputStream = response.getOutputStream();
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            byte[] bytes = new byte[1024];
            int l;
            while ((l = inputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, l);
            }
        }
    }
}
