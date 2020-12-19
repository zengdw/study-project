package com.zengdw.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
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
     * @param filePath 文件路劲
     * @param fileName 文件名
     */
    public static void downLoadFile(String filePath, String fileName, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(filePath)) {
            throw new Exception("文件路劲不能为空");
        }
        final File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception("文件不存在");
        }
        // 配置文件下载
        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        if (StringUtils.isBlank(fileName)) {
            fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }
        String suffix = "";
        if (!fileName.contains(".")) {
            suffix = filePath.substring(filePath.lastIndexOf("."));
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + suffix, StandardCharsets.UTF_8));
        try (FileInputStream fileInputStream = new FileInputStream(file);
             BufferedInputStream inputStream = new BufferedInputStream(fileInputStream);
             ServletOutputStream outputStream = response.getOutputStream()) {
            byte[] bytes = new byte[1024];
            int l;
            while ((l = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, l);
            }

        }
    }
}
